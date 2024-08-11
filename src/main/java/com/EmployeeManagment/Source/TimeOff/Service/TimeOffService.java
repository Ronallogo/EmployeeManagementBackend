package com.EmployeeManagment.Source.TimeOff.Service;


import com.EmployeeManagment.Source.TimeOff.Entity.TimeOff;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffRequest;
import com.EmployeeManagment.Source.TimeOff.Exception.TimeOffNotFoundException;
import com.EmployeeManagment.Source.TimeOff.Repository.TimeOffRepository;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TimeOffService {


    @Autowired
    TimeOffRepository TimeOffRepository;
    @Autowired
    EmployeeRepository employeeRepository ;

    ////// function to create an TimeOffs
    public TimeOff create(TimeOffRequest TimeOff){
        /////make sure that this employee exist
        Employee e = employeeRepository.findById(TimeOff.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee does not exist !!"));

        /////// check if beginning year < end year
        if(TimeOff.periodTimeOffCheck(
                TimeOff.getBeginning(),
                TimeOff.getEnd()
        ))
        {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }


        /////make the registration in the database
        return TimeOffRepository.save(new TimeOff(
                TimeOff.getBeginning() ,
                TimeOff.getEnd(),
                TimeOff.getType(),
                TimeOff.isStatus(),
                e
        ));

    }



    /////// function to get an TimeOff
    public TimeOff get(Long id){
        return TimeOffRepository.findById(id)
                .orElseThrow(()-> new TimeOffNotFoundException("TimeOffs not found !!"));
    }


    ////function to update an TimeOff

    public TimeOff edit(Long id , TimeOffRequest TimeOff){
        /////make sure that this employee exist in case where it is updated
        Employee e = employeeRepository.findById(TimeOff.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee does not exist !!"));


        ////check the deviation between the beginning and the end in case of update
        if(TimeOff.periodTimeOffCheck(
                TimeOff.getBeginning(),
                TimeOff.getEnd()
        ))
        {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }



        /////make the registration in the database
        return TimeOffRepository.save(new TimeOff(
                id  ,
                TimeOff.getBeginning() ,
                TimeOff.getEnd(),
                TimeOff.getType(),
                TimeOff.isStatus(),
                e
        ));
    }


    /////function to delete an TimeOff

    public boolean delete(Long id){
        if(TimeOffRepository.existsById(id)) {
            TimeOffRepository.deleteById(id);
            return true ;
        }
        else{
            throw new TimeOffNotFoundException("this TimeOff not exist or already deleted !!") ;
        }
    }


    ///function to get all TimeOff
    public List<TimeOff> all() {
        return   TimeOffRepository.findAll();
    }
}
