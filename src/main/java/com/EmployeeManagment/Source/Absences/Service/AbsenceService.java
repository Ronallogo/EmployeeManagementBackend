package com.EmployeeManagment.Source.Absences.Service;


import com.EmployeeManagment.Source.Absences.Entity.Absence;
import com.EmployeeManagment.Source.Absences.Exception.AbsenceNotFoundException;
import com.EmployeeManagment.Source.Absences.Repository.AbsenceRepository;
import com.EmployeeManagment.Source.Absences.Entity.AbsenceRequest;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AbsenceService {

    @Autowired
    AbsenceRepository absenceRepository;
    @Autowired
    EmployeeRepository employeeRepository ;

    ////// function to create an absences
    public Absence create(AbsenceRequest absence){
        /////make sure that this employee exist
        Employee e = employeeRepository.findById(absence.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee does not exist !!"));

        /////make the registration in the database
        return absenceRepository.save(new Absence(
            absence.getAbsence_day() ,
            absence.getReason(),
            e
        ));

    }



    /////// function to get an absence
    public Absence get(Long id){
        return absenceRepository.findById(id)
                .orElseThrow(()-> new AbsenceNotFoundException("absences not found !!"));
    }


    ////function to update an absence

    public Absence edit(Long id , AbsenceRequest absence){
        /////make sure that this employee exist in case where it is updated
        Employee e = employeeRepository.findById(absence.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee does not exist !!"));

        /////make the registration in the database
        return absenceRepository.save(new Absence(
            id  ,
            absence.getAbsence_day() ,
            absence.getReason(),
            e
        ));
    }


    /////function to delete an absence

    public boolean delete(Long id){
        if(absenceRepository.existsById(id)) {
            absenceRepository.deleteById(id);
            return true ;
        }
        else{
            throw new AbsenceNotFoundException("this absence not exist or already deleted !!") ;
        }
    }


    ///function to get all absence
    public List<Absence> all() {
      return   absenceRepository.findAll();
    }
}