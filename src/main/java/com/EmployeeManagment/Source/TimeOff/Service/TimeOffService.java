package com.EmployeeManagment.Source.TimeOff.Service;


import com.EmployeeManagment.Source.TimeOff.Entity.TimeOff;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffApply;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffApplyRequest;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffRequest;
import com.EmployeeManagment.Source.TimeOff.Exception.TimeOffApplyNotFoundException;
import com.EmployeeManagment.Source.TimeOff.Exception.TimeOffNotFoundException;
import com.EmployeeManagment.Source.TimeOff.Repository.TimeOffApplyRepository;
import com.EmployeeManagment.Source.TimeOff.Repository.TimeOffRepository;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TimeOffService {


    @Autowired
    TimeOffRepository TimeOffRepository;
    @Autowired
    TimeOffApplyRepository timeOffApplyRepository;
    @Autowired
    EmployeeRepository employeeRepository ;



    ////// function to create an TimeOffs
    public TimeOff create(TimeOffRequest TimeOff){
        TimeOffApply t = timeOffApplyRepository.findById(TimeOff.getTimeOffApply())
                .orElseThrow(()-> new RuntimeException("the time off have to be apply first!!"));


        if(!t.isValidate()){
            throw  new RuntimeException("this time off is not valiated");
        }



        System.out.print(TimeOff.getBeginning()  +" et " + TimeOff.getEnd());


        /////// check if beginning year < end year
        if( TimeOff.getBeginning().after(TimeOff.getEnd())) {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }


        /////make the registration in the database
        return TimeOffRepository.save(new TimeOff(
                TimeOff.getBeginning() ,
                TimeOff.getEnd(),
                TimeOff.getType(),
                TimeOff.isStatus(),
                t
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

        if( TimeOff.getBeginning().after(TimeOff.getEnd())) {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }

        TimeOffApply t = timeOffApplyRepository.findById(TimeOff.getTimeOffApply())
                .orElseThrow(()-> new TimeOffApplyNotFoundException("this timeOff have to be apply first!!!!"));

        /////make the registration in the database
        return TimeOffRepository.save(new TimeOff(
                id  ,
                TimeOff.getBeginning() ,
                TimeOff.getEnd(),
                TimeOff.getType(),
                TimeOff.isStatus(),

                t
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

    public List<TimeOff> search(String keyword){
        return TimeOffRepository.search(keyword);
    }
    public Optional<TimeOff> searchByIdEmployee(Long keyword){
        return TimeOffRepository.searchByIdEmployee(keyword);
    }



}
