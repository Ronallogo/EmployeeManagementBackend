package com.EmployeeManagment.Source.Task_Scheduled.Controller;


import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduledRequest;
import com.EmployeeManagment.Source.Task_Scheduled.Service.TaskScheduledService;
import com.EmployeeManagment.Source.report.reportTaskScheduled.TaskScheduledPdfModel;
import com.EmployeeManagment.Source.report.reportValidation.ValidationPdfModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/auth/employee_manager/TaskScheduled")
@CrossOrigin("*")
public class TaskScheduleController {


    @Autowired
    private TaskScheduledPdfModel taskScheduledPdfModel ;
    @Autowired
    private ValidationPdfModel taskValidate ;


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

    @GetMapping(value = "/searchTaskScheduled/{keyword}")
    public ResponseEntity<List<TaskScheduled>> searchTaskScheduled(@PathVariable String keyword){
        List<TaskScheduled> listTaskScheduled = TaskScheduledService.searchTaskScheduled(keyword) ;
        return new ResponseEntity<>(listTaskScheduled, HttpStatus.OK);
    }


    @GetMapping(value = "/searchTaskByPositionId/{position_id}")
    public ResponseEntity<List<TaskScheduled>> searchTaskScheduledByPositionId(@PathVariable Long position_id){
        List<TaskScheduled> listTaskScheduled = TaskScheduledService.fetchTaskByPositionId(position_id) ;
        return new ResponseEntity<>(listTaskScheduled, HttpStatus.OK);
    }
    /////endpoint allowing to get all TaskScheduled for an employee taskForOne

    @GetMapping(value = "/taskForOne/{employee_id}")
    public ResponseEntity<List<TaskScheduled>> searchTaskScheduledByEmployeeId(@PathVariable Long employee_id){
        List<TaskScheduled> listTaskScheduled = TaskScheduledService.fetchTaskByEmployeeId(employee_id) ;
        return new ResponseEntity<>(listTaskScheduled, HttpStatus.OK);
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


    @GetMapping(value = "/report/pdf")
    public void reportPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        // Define a DateFormat for the filename
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());

        // Set the Content-Disposition header to suggest a filename for download
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=liste_tache_programmées_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Call your method to write the PDF content to the response output stream
        this.taskScheduledPdfModel.export(response);
    }





   /* @GetMapping(value = "/validation/report/pdf")
    public void reportTaskPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        // Define a DateFormat for the filename
       // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());

        // Set the Content-Disposition header to suggest a filename for download
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=liste_tache_validées_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        // Call your method to write the PDF content to the response output stream
        this.taskValidate.export(response);
    }*/
}
