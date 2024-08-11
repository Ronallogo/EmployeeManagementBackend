package com.EmployeeManagment.Source.TimeOff.Controller;


import com.EmployeeManagment.Source.TimeOff.Entity.TimeOff;
import com.EmployeeManagment.Source.TimeOff.Service.TimeOffService;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee_manager/timeOff")
@CrossOrigin("*")
public class TimeOffController {
    @Autowired
    TimeOffService timeOffService ;


    ////endpoint allowing to make an  TimeOff registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeOff> saveTimeOff(@RequestBody TimeOffRequest TimeOff){
        TimeOff TimeOffAdded =  timeOffService.create(TimeOff);
        return  new ResponseEntity<TimeOff>( TimeOffAdded, HttpStatus.CREATED);
    }

    ///endpoint allowing to get one  TimeOff
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<TimeOff> getTimeOff(@PathVariable Long id){
        TimeOff  timeOffGot =  timeOffService.get(id);
        return  new ResponseEntity<TimeOff>( timeOffGot , HttpStatus.FOUND);
    }


    /////endpoint allowing to get all  TimeOffs
    @GetMapping(value = "/all")
    public ResponseEntity<List<TimeOff>> allTimeOff(){
        List<TimeOff> listTimeOff =  timeOffService.all() ;
        return new ResponseEntity<List<TimeOff>>(listTimeOff , HttpStatus.OK);
    }


    /////endpoint allowing to update  one  TimeOff by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<TimeOff> editTimeOff(@PathVariable Long id  , @RequestBody  TimeOffRequest  TimeOff){
        TimeOff  timeOffEdited =  timeOffService.edit(id ,  TimeOff);
        return  new ResponseEntity<TimeOff>( timeOffEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one  TimeOff
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deleteTimeOff(@PathVariable Long id){
        return  timeOffService.delete(id);
    }
    
}
