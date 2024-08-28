package com.EmployeeManagment.Source.Pay_Stub.Service;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStubRequest;
import com.EmployeeManagment.Source.Pay_Stub.Exception.PayStubNotFoundException;
import com.EmployeeManagment.Source.Pay_Stub.Repository.PayStubRepository;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Position.Exception.PositionNotFoundException;
import com.EmployeeManagment.Source.Task_Inserted.Repository.TaskInsertedRepository;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.Task_Scheduled.Repository.TaskScheduledRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class PayStubService {

    @Autowired
    private PayStubRepository payStubRepository ;
    @Autowired
    private TaskScheduledRepository taskScheduledRepository ;
    @Autowired
    private EmployeeRepository  employeeRepository ;

    @Autowired
    private TaskInsertedRepository taskInsertedRepository ;

    ///  function to create  a PayStub
    public PayStub create(PayStubRequest payStubRequest){
         ////set the value nbrTask
        Integer nbrTask = taskScheduledRepository.sumTaskDid(payStubRequest.getEmployee()) ;
        //// check for bonus ... here we take the previous + 5000 in the case where
        // nbrTask would be a multiple of 5

        if(nbrTask % 5 == 0 && nbrTask > 0) payStubRequest.setBonus(payStubRequest.getBonus() +  5000);

        ////fetch list of all  id_task did for the employee
        List<Long> listTaskId = taskScheduledRepository.listTaskDid(payStubRequest.getEmployee());

        /// set the total amount
        Integer totalAmount = this.FindAmount(listTaskId) + payStubRequest.getAmount();

        ////  fetch the  employee
        Employee e = employeeRepository.findById(payStubRequest.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee not exist !!!"));


        PayStub p = new PayStub(
               payStubRequest.getPaymentDate() ,
               payStubRequest.getBonus() ,
               e,
               totalAmount ,
               nbrTask );


        return payStubRepository.save(new PayStub(
                payStubRequest.getPaymentDate() ,
                payStubRequest.getBonus() ,
                e,
                totalAmount ,
                nbrTask
                )
        );
    }

    public PayStub edit(Long id ,PayStubRequest payStubRequest){

            PayStub oldRegister = this.payStubRepository.findById(id).orElseThrow();
        ////check the employee
        Employee e = employeeRepository.findById(payStubRequest.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee not exist !!!"));


        ////set the value nbrTask
        Integer nbrTask = taskScheduledRepository.sumTaskDid(payStubRequest.getEmployee()) ;

        System.out.print("nombre de tache ++" + nbrTask );
        //// check for bonus ... here we take the previous + 5000 in the case where
        // nbrTask would be a multiple of 5

        if(nbrTask % 5 == 0 && nbrTask > 0) payStubRequest.setBonus(payStubRequest.getBonus() +  5000);

        ////fetch list of all  id_task did for the employee
        List<Long> listTaskId = taskScheduledRepository.listTaskDid(payStubRequest.getEmployee());




        /// set the total amount
        Integer totalAmount =    this.FindAmount(listTaskId) ;


            System.out.print("une fois encore "+totalAmount);
        ////make updating
        return payStubRepository.save(new PayStub(
                id ,
                 totalAmount,
                payStubRequest.getNbrTasks(),
                payStubRequest.getBonus(),
                payStubRequest.getPaymentDate() ,
                e
            )

        );
    }

    /////find all payStub
    public List<PayStub> all(){
        return  payStubRepository.findAll() ;
    }


    ////get one payStub

    public PayStub get(Long id){
         return payStubRepository.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException("this pay_stub not exist !!!"));
    }





    ///// function to make a total amount
    public Integer FindAmount(List<Long> longList   ){

        Integer amount = 30000 ;
        /// process to find the total amount
        for ( int i = 0 ; i < longList.size() ; i++) {
            amount += taskInsertedRepository.getGainForOne(longList.get(i));
            System.out.print("id - >" + i);
        }
        System.out.print("montant -> "+amount);
        return amount ;

    }
    public boolean delete(Long id){
        if(payStubRepository.existsById(id)){

                payStubRepository.deleteById(id);
                return true ;
        }
        else throw  new PositionNotFoundException(" this pay_stub  does not exist is maybe already  deleted !!");
    }


    public List<PayStub> search(String keyword){
        return this.payStubRepository.searchByEmployee(keyword);
    }


    public PayStub getPayStubForOneEmployee(String email){
        return this.payStubRepository.getForOne(email)
                .orElseThrow(()-> new PayStubNotFoundException("pay_stub not found"));
    }

    public Integer FindExtraLength(List<Long> firtList , List<Long> secondList){
            return firtList.size() - secondList.size() ;
    }


}



