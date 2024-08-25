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


        return  this.timeOffApplyRepository.save(
                new TimeOffApply(
                        e,
                        t.getType() ,
                        t.getApply(),
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
