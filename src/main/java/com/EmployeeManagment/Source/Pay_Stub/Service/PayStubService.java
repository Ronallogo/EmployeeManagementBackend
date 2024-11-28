package com.EmployeeManagment.Source.Pay_Stub.Service;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Notification.Entity.Notification;
import com.EmployeeManagment.Source.Notification.Repository.NotificationRepository;
import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStubRequest;
import com.EmployeeManagment.Source.Pay_Stub.Exception.PayStubNotFoundException;
import com.EmployeeManagment.Source.Pay_Stub.Repository.PayStubRepository;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Position.Exception.PositionNotFoundException;
import com.EmployeeManagment.Source.Task_Inserted.Repository.TaskInsertedRepository;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.Task_Scheduled.Repository.TaskScheduledRepository;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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


    @Autowired
    private NotificationRepository notificationRepository ;

    ///  function to create  a PayStub
    public PayStub create(PayStubRequest payStubRequest){
        ////  fetch the  employee
        Employee e = employeeRepository.findById(payStubRequest.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee not exist !!!"));
        return payStubRepository.save(new PayStub(
                payStubRequest.getPaymentDate() ,
                 300 ,
                e,
                 3000,
                 0
                )
        );
    }

    public PayStub edit(Long id ,PayStubRequest payStubRequest){

        var payStub = this.payStubRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("payStub not found"));
        Employee e = employeeRepository.findById(payStubRequest.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee not exist !!!"));

        ////make updating
        payStub.setAmount(payStubRequest.getAmount());
        payStub.setNbrTasks(payStubRequest.getNbrTasks());
        payStub.setBonus( payStubRequest.getBonus());
        payStub.setPaymentDate(payStubRequest.getPaymentDate());
        return payStubRepository.save( payStub);


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

        int amount = 30000 ;
        /// process to find the total amount
        for ( int i = 0 ; i < longList.size() ; i++) {
            amount +=   taskInsertedRepository.getGainForOne(longList.get(i));
            System.out.print("id - > " + i+" ");
            System.out.print("gain - >" + longList.get(i)+" ");
            System.out.print("montant -> "+amount);

        }
        System.out.print("montant -> "+amount);
        return   amount ;

    }
    public Integer FindAmountForEdit(List<TaskScheduled> List   ){

        int amount = 30000 ;
        for (TaskScheduled s:List) {
                amount += s.getTaskInserted().getGain_task_post();
        }
        return   amount ;

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
    public Optional<PayStub> searchById(Long id){
        return this.payStubRepository.searchByEmployeeId(id);
    }
    public PayStub getPayStubForOneEmployee(String email){
        return this.payStubRepository.getForOne(email)
                .orElseThrow(()-> new PayStubNotFoundException("pay_stub not found"));
    }

    public PayStub refresh(Long id ,PayStubRequest payStubRequest){

        Employee e = employeeRepository.findById(payStubRequest.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee not exist !!!"));

        this.notificationRepository.save(
                new Notification(
                        " Votre payement est près a etre effectué ..... Veuillez imprimé votre bulletin!!!" ,
                        e ,
                        payStubRequest.getDateUpdate(),
                        "virement"
                )
        );
        return payStubRepository.save(new PayStub(
                    id ,
                    30000,
                    0,
                     5000,
                    payStubRequest.getPaymentDate() ,
                    e
                )

        );
    }


}



