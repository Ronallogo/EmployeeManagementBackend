package com.EmployeeManagment.Source.Absences.Service;


import com.EmployeeManagment.Source.Absences.Entity.Absence;
import com.EmployeeManagment.Source.Absences.Exception.AbsenceNotFoundException;
import com.EmployeeManagment.Source.Absences.Repository.AbsenceRepository;
import com.EmployeeManagment.Source.Absences.Entity.AbsenceRequest;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Notification.Entity.Notification;
import com.EmployeeManagment.Source.Notification.Repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AbsenceService {

    @Autowired
    AbsenceRepository absenceRepository;
    @Autowired
    EmployeeRepository employeeRepository ;

    @Autowired
    private NotificationRepository notificationRepository ;

    ////// function to create an absences
    public Absence create(AbsenceRequest absence){
        /////make sure that this employee exist
        Employee e = employeeRepository.findByEmail(absence.getEmail())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee does not exist !!"));


        this.notificationRepository.save(
            new Notification(
            " Vous avez été enregistré comme étant absent(e) le "+absence.getAbsence_day(),
            e ,
            absence.getAbsence_day() ,
            "absence"
            )
        );
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
        System.out.print(absence.getEmail());
        /////make sure that this employee exist in case where it is updated
        Employee e = employeeRepository.findByEmail(absence.getEmail())
                .orElseThrow(()-> new EmployeeNotFoundException("this employee does not exist !!"));


        this.notificationRepository.save(
                new Notification(
                        " Vous avez été enregistré comme étant absent(e) le "+absence.getAbsence_day(),
                        e ,
                        absence.getAbsence_day() ,
                        "absence"
                )
        );
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


    /////function to search some absences
    public List<Absence> search(String keyword){
      return   this.absenceRepository.search(keyword);
    }
    public Optional<Absence> searchById(Long keyword){
      return   this.absenceRepository.searchByIdEmployee(keyword);
    }

    public Integer findNbrAbsenceForOne(Long employee){
       return this.absenceRepository.AllAbsencesForOne(employee);
    }
}
