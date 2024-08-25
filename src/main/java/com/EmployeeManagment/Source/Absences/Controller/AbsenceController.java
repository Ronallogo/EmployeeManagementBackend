package com.EmployeeManagment.Source.Absences.Controller;


import com.EmployeeManagment.Source.Absences.Entity.Absence;
import com.EmployeeManagment.Source.Absences.Entity.AbsenceRequest;
import com.EmployeeManagment.Source.Position.Entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/employee_manager/absence")
@CrossOrigin("*")
public class AbsenceController {
    @Autowired
    com.EmployeeManagment.Source.Absences.Service.AbsenceService AbsenceService ;


    ////endpoint allowing to make an  Absence registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Absence> saveAbsence(@RequestBody AbsenceRequest Absence){
        Absence AbsenceAdded =  AbsenceService.create(Absence);
        return  new ResponseEntity<Absence>( AbsenceAdded, HttpStatus.CREATED);
    }

    ///endpoint allowing to get one  Absence
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Absence> getAbsence(@PathVariable Long id){
        Absence  AbsenceGot =  AbsenceService.get(id);
        return  new ResponseEntity<Absence>( AbsenceGot , HttpStatus.FOUND);
    }


    /////endpoint allowing to get all  Absences
    @GetMapping(value = "/all")
    public ResponseEntity<List<Absence>> allAbsence(){
        List<Absence> listAbsence =  AbsenceService.all() ;
        return new ResponseEntity<List<Absence>>(listAbsence , HttpStatus.OK);
    }


    /////endpoint allowing to update  one  Absence by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<Absence> editAbsence(@PathVariable Long id  , @RequestBody  AbsenceRequest  Absence){
        Absence  AbsenceEdited =  AbsenceService.edit(id ,  Absence);
        return  new ResponseEntity<Absence>( AbsenceEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one  Absence
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deleteAbsence(@PathVariable Long id){
        return  AbsenceService.delete(id);
    }


    @GetMapping(value = "/search/{keyword}")
    public List<Absence> searchAbsence(@PathVariable String keyword){
        return AbsenceService.search(keyword);
    }


}
