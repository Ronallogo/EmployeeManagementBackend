package com.EmployeeManagment.Source.TimeOff.Service;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Notification.Entity.Notification;
import com.EmployeeManagment.Source.Notification.Repository.NotificationRepository;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffApply;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffApplyRequest;
import com.EmployeeManagment.Source.TimeOff.Exception.TimeOffApplyNotFoundException;
import com.EmployeeManagment.Source.TimeOff.Repository.TimeOffApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeOffApplyService {


    @Autowired
    private TimeOffApplyRepository timeOffApplyRepository;
    @Autowired
    private EmployeeRepository employeeRepository ;

    @Autowired
    private NotificationRepository notificationRepository ;


    public TimeOffApply create(TimeOffApplyRequest t){

        Employee e = employeeRepository.findById(t.getEmployee())
                .orElseThrow(()->   new EmployeeNotFoundException(" employee not found"));


        System.out.print(t.getBeginning() + " et ");
        System.out.print(t.getEnd());
        /////// check if beginning year < end year
        if( t.getBeginning().after(t.getEnd())) {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }

        this.notificationRepository.save(
                new Notification(
                        " Votre demande de congé a été reçu !!! ... La réponse vous sera communiqué très bientot" ,
                        e ,
                        t.getBeginning() ,
                        "demande de congé"
                )
        );

        return  this.timeOffApplyRepository.save(
                new TimeOffApply(
                        e,
                        t.getApply(),
                        t.getType() ,
                        t.getBeginning(),
                        t.getEnd() ,
                        t.isValidate()
                )
        );
    }



    public TimeOffApply get(Long id){
        return timeOffApplyRepository.findById(id)
                .orElseThrow(()-> new TimeOffApplyNotFoundException("timOffApply  not  found !!!"));
    }



    public TimeOffApply update(Long id ,  TimeOffApplyRequest t){

        TimeOffApply ta = this.timeOffApplyRepository.findById(id).orElseThrow(()-> new RuntimeException("demande not found!!"));

        Employee e = employeeRepository.findById(t.getEmployee())
                .orElseThrow(()->   new EmployeeNotFoundException(" employee not found"));


        /////// check if beginning year < end year
        if( t.getBeginning().after(t.getEnd())) {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }

        ta.setEnd(t.getEnd());
        ta.setBeginning(t.getBeginning());
        ta.setEmployee(e);
        ta.setValidate(t.isValidate());
        ta.setType(t.getType());

        this.notificationRepository.save(
                new Notification(

                        (t.isValidate())?" Votre demande de congé a été etudié et a été validé !!! ...." +
                                " Je vous souhaite un excellent congé  User @"+e.getName() :
                                "Votre demande de congé a été etudié et n'a été validé malheureusement " ,
                        e ,
                        t.getBeginning() ,
                        "demande de congé"
                )
        );
        return  this.timeOffApplyRepository.save(ta);


    }

    public List<TimeOffApply> all(){
        return  timeOffApplyRepository.findAll() ;
    }


    public boolean delete(Long id){
        if(!timeOffApplyRepository.existsById(id)){
            throw  new RuntimeException("this time Off Apply do not exist !!!");
        }else {
            timeOffApplyRepository.deleteById(id);
            return true ;
        }
    }

}
