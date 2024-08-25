package com.EmployeeManagment.Source.Task_Scheduled.Controller;


import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduledRequest;
import com.EmployeeManagment.Source.Task_Scheduled.Service.TaskScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/employee_manager/TaskScheduled")
@CrossOrigin("*")
public class TaskScheduleController {

    /////variable that represent the service in this endpoint class
    @Autowired
    private  TaskScheduledService TaskScheduledService ;

    ////endpoint allowing to make a TaskScheduled registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskScheduled> saveTaskScheduled(@RequestBody TaskScheduledRequest TaskScheduledRequest){
        TaskScheduled TaskScheduledAdded = TaskScheduledService.create(TaskScheduledRequest);
        return  new ResponseEntity<TaskScheduled>(TaskScheduledAdded, HttpStatus.CREATED);
    }

    ///endpoint allowing to get one TaskScheduledInserted
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<TaskScheduled> getTaskScheduled(@PathVariable Long id){
        TaskScheduled TaskScheduledGot = TaskScheduledService.get(id);
        return  new ResponseEntity<TaskScheduled>(TaskScheduledGot , HttpStatus.FOUND);
    }


    /////endpoint allowing to get all TaskScheduled
    @GetMapping(value = "/all")
    public ResponseEntity<List<TaskScheduled>> allTaskScheduled(){
        List<TaskScheduled> listTaskScheduled = TaskScheduledService.all() ;
        return new ResponseEntity<List<TaskScheduled>>(listTaskScheduled , HttpStatus.OK);
    }


    /////endpoint allowing to update  one TaskScheduled by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<TaskScheduled> editTaskScheduled(@PathVariable Long id  , @RequestBody TaskScheduledRequest TaskScheduledRequest){
        TaskScheduled TaskScheduledEdited = TaskScheduledService.edit(id , TaskScheduledRequest);
        return  new ResponseEntity<TaskScheduled>(TaskScheduledEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one TaskScheduled
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deleteTaskScheduled(@PathVariable Long id){
        return TaskScheduledService.delete(id);
    }

}
