package com.EmployeeManagment.Source.Task.Controller;


import com.EmployeeManagment.Source.Task.Entity.Task;
import com.EmployeeManagment.Source.Task.Service.TaskService;
import com.EmployeeManagment.Source.report.reportTask.TaskPdfModel;
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

//////here is all endpoints of task module
@RestController
@RequestMapping("/api/auth/employee_manager/task")
@CrossOrigin("*")
public class TaskController {
    /////variable that represent the service in this endpoint class
    @Autowired
    private TaskService taskService ;

    @Autowired
    private TaskPdfModel  taskPdfModel;

    ////endpoint allowing to make a Task registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> saveTask(@RequestBody Task task){
        Task taskAdded = taskService.create(task);
        return  new ResponseEntity<Task>(taskAdded, HttpStatus.CREATED);
    }

    ///endpoint allowing to get one Task
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        Task TaskGot = taskService.get(id);
        return  new ResponseEntity<Task>(TaskGot , HttpStatus.FOUND);
    }


    /////endpoint allowing to get all Tasks
    @GetMapping(value = "/all")
    public ResponseEntity<List<Task>> allTask(){
        List<Task> listTask = taskService.all() ;
        return new ResponseEntity<List<Task>>(listTask , HttpStatus.OK);
    }


    /////endpoint allowing to update  one Task by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<Task> editTask(@PathVariable Long id  , @RequestBody Task Task){
        Task TaskEdited = taskService.edit(id , Task);
        return  new ResponseEntity<Task>(TaskEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one Task
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deleteTask(@PathVariable Long id){
        return taskService.delete(id);
    }


    /////endpoint allowing to do a research
    @GetMapping(value = "/search/{keyword}")
    public List<Task> searchTask(@PathVariable String keyword){
        return taskService.search(keyword);
    }


    @GetMapping(value = "/report/pdf")
    public void reportPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        // Define a DateFormat for the filename
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());

        // Set the Content-Disposition header to suggest a filename for download
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=liste_tache_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Call your method to write the PDF content to the response output stream
        this.taskPdfModel.export(response);
    }

        
}
