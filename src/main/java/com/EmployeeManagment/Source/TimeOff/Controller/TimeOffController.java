package com.EmployeeManagment.Source.TimeOff.Controller;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOff;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffApply;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffApplyRequest;
import com.EmployeeManagment.Source.TimeOff.Service.TimeOffApplyService;
import com.EmployeeManagment.Source.TimeOff.Service.TimeOffService;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffRequest;
import com.EmployeeManagment.Source.report.reportConge.TimeOffPdfModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/auth/employee_manager/timeOff")
@CrossOrigin("*")
public class TimeOffController {
    @Autowired
    TimeOffService timeOffService ;
    @Autowired
    TimeOffApplyService timeOffApplyService ;

    @Autowired
     TimeOffPdfModel timeOffPdfModel;


    ////endpoint allowing to make an  TimeOff registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeOff> saveTimeOff(@RequestBody TimeOffRequest TimeOff ){
        TimeOff TimeOffAdded =  timeOffService.create(TimeOff);
        return  new ResponseEntity<TimeOff>( TimeOffAdded, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/apply/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeOffApply> saveTimeOff(@RequestBody TimeOffApplyRequest t){
        TimeOffApply TimeOffAdded =  timeOffApplyService.create(t);
        return  new ResponseEntity<TimeOffApply>( TimeOffAdded, HttpStatus.CREATED);
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

    @GetMapping(value = "/apply/all")
    public ResponseEntity<List<TimeOffApply>> allTimeOffApply(){
        List<TimeOffApply> listTimeOff =  timeOffApplyService.all() ;
        return new ResponseEntity<List<TimeOffApply>>(listTimeOff , HttpStatus.OK);
    }


    /////endpoint allowing to update  one  TimeOff by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<TimeOff> editTimeOff(@PathVariable Long id  , @RequestBody  TimeOffRequest  TimeOff){
        TimeOff  timeOffEdited =  timeOffService.edit(id ,  TimeOff);
        return  new ResponseEntity<TimeOff>( timeOffEdited , HttpStatus.OK);
    }

    @PutMapping(value = "/apply/edit/{id}")
    public ResponseEntity<TimeOffApply> editTimeOffApply(@PathVariable Long id  , @RequestBody  TimeOffApplyRequest  TimeOff){
        TimeOffApply  timeOffEdited =  timeOffApplyService.update(id ,  TimeOff);
        return  new ResponseEntity<TimeOffApply>( timeOffEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one  TimeOff
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deleteTimeOff(@PathVariable Long id){
        return  timeOffService.delete(id);
    }
    @DeleteMapping(value = "/apply/delete/{id}")
    public  boolean deleteTimeOffApply(@PathVariable Long id){
        return  timeOffApplyService.delete(id);
    }


    @GetMapping(value = "/search/{keyword}")
    public List<TimeOff> searchContent(@PathVariable String keyword){
        return  timeOffService.search(keyword);
    }


    @GetMapping(value = "/report/pdf")
    public void reportPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        // Define a DateFormat for the filename
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());

        // Set the Content-Disposition header to suggest a filename for download
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=liste_congé_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Call your method to write the PDF content to the response output stream
        this.timeOffPdfModel.export(response);
    }
    
}
