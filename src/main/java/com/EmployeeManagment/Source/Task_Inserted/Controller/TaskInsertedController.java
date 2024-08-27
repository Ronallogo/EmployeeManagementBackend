package com.EmployeeManagment.Source.Task_Inserted.Controller;


import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.EmployeeManagment.Source.Task_Inserted.Service.TaskInsertedService;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInsertedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

///////// all TaskInsertedInserted endpoint
@RestController
@RequestMapping("/api/auth/employee_manager/taskInserted")
@CrossOrigin("*")
public class TaskInsertedController {

    /////variable that represent the service in this endpoint class
    @Autowired
    private TaskInsertedService taskInsertedService ;

    ////endpoint allowing to make a TaskInserted registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskInserted> saveTaskInserted(@RequestBody TaskInsertedRequest taskInsertedRequest){
        System.out.print(taskInsertedRequest.getDate_insertion());
        TaskInserted TaskInsertedAdded = taskInsertedService.create(taskInsertedRequest);
        return  new ResponseEntity<TaskInserted>(TaskInsertedAdded, HttpStatus.CREATED);
    }

    ///endpoint allowing to get one TaskInsertedInserted
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<TaskInserted> getTaskInserted(@PathVariable Long id){
        TaskInserted TaskInsertedGot = taskInsertedService.get(id);
        return  new ResponseEntity<TaskInserted>(TaskInsertedGot , HttpStatus.FOUND);
    }


    /////endpoint allowing to get all TaskInserted
    @GetMapping(value = "/all")
    public ResponseEntity<List<TaskInserted>> allTaskInserted(){
        List<TaskInserted> listTaskInserted = taskInsertedService.all() ;
        return new ResponseEntity<List<TaskInserted>>(listTaskInserted , HttpStatus.OK);
    }   /////endpoint allowing to get all TaskInserted for one position
    @GetMapping(value = "/allForOnePosition/{id}")
    public ResponseEntity<List<TaskInserted>> allTaskInserted(@PathVariable Long id){
        List<TaskInserted> listTaskInserted = taskInsertedService.allTaskForOnePosition(id) ;
        return new ResponseEntity<List<TaskInserted>>(listTaskInserted , HttpStatus.OK);
    }


    /////endpoint allowing to update  one TaskInserted by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<TaskInserted> editTaskInserted(@PathVariable Long id  , @RequestBody TaskInsertedRequest TaskInsertedRequest){
        TaskInserted TaskInsertedEdited = taskInsertedService.edit(id , TaskInsertedRequest);
        return  new ResponseEntity<TaskInserted>(TaskInsertedEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one TaskInserted
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deleteTaskInserted(@PathVariable Long id){
        return taskInsertedService.delete(id);
    }

}
