package com.EmployeeManagment.Source.TimeOff.Service;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
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


    public TimeOffApply create(TimeOffApplyRequest t){

        Employee e = employeeRepository.findById(t.getEmployee())
                .orElseThrow(()->   new EmployeeNotFoundException(" employee not found"));


        System.out.print(t.getBeginning() + " et ");
        System.out.print(t.getEnd());
        /////// check if beginning year < end year
        if( t.getBeginning().compareTo(t.getEnd()) > 0)
        {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }

        return  this.timeOffApplyRepository.save(
                new TimeOffApply(
                        e,
                        t.getApply(),
                        t.getType() ,
                        t.getEnd() ,
                        t.getBeginning(),
                        t.isValidate()
                )
        );
    }



    public TimeOffApply get(Long id){
        return timeOffApplyRepository.findById(id)
                .orElseThrow(()-> new TimeOffApplyNotFoundException("timOffApply  not  found !!!"));
    }



    public TimeOffApply update(Long id ,  TimeOffApplyRequest t){

        Employee e = employeeRepository.findById(t.getEmployee())
                .orElseThrow(()->   new EmployeeNotFoundException(" employee not found"));


        /////// check if beginning year < end year
        if(t.periodTimeOffCheck(
                t.getBeginning(),
                t.getEnd()
        ))
        {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }

        return  this.timeOffApplyRepository.save(
                new TimeOffApply(
                        id ,
                        t.getEnd() ,
                        t.getBeginning(),
                        e,
                        t.getType() ,
                        t.getApply(),
                        t.isValidate()
                )
        );
    }

    public List<TimeOffApply> all(){
        return  timeOffApplyRepository.findAll() ;
    }


    public boolean delete(Long id){
        if(!timeOffApplyRepository.existsById(id)){
            throw  new RuntimeException("this time Off Apply do not exist !!!");
        }else {
            return true ;
        }
    }

}
