package com.EmployeeManagment.Source.Pay_Stub.Service;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStubRequest;
import com.EmployeeManagment.Source.Pay_Stub.Repository.PayStubRepository;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Position.Exception.PositionNotFoundException;
import com.EmployeeManagment.Source.Task_Inserted.Repository.TaskInsertedRepository;
import com.EmployeeManagment.Source.Task_Scheduled.Repository.TaskScheduledRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        if(nbrTask % 5 == 0) payStubRequest.setBonus(payStubRequest.getBonus() +  5000);

        ////fetch list of all  id_task did for the employee
        List<Long> listTaskId = taskScheduledRepository.listTaskDid(payStubRequest.getEmployee());

        /// set the total amount
        Integer totalAmount = this.FindAmount(listTaskId);

        ////  fetch the  employee
        Employee e = employeeRepository.findById(payStubRequest.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee not exist !!!"));

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
        ////check the employee
        Employee e = employeeRepository.findById(payStubRequest.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee not exist !!!"));

       ////make updating
        return payStubRepository.save(new PayStub(
                id ,
                payStubRequest.getAmount(),
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
    public Integer FindAmount(List<Long> longList){
        Integer amount = 0 ;

        /// process to find the total amount
        for (Long i: longList ) {
            amount += taskInsertedRepository.getGainForOne(i);
        }
        return amount ;
    }
    public boolean delete(Long id){
        if(payStubRepository.existsById(id)){

                payStubRepository.deleteById(id);
                return true ;
        }
        else throw  new PositionNotFoundException(" this pay_stub  does not exist is maybe already  deleted !!");
    }




}



