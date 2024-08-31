package com.EmployeeManagment.Source.Position.Controller;


import com.EmployeeManagment.Source.Position.Service.PositionService;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.report.reportPosition.PositionPdfModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//////here is all endpoints of position module
@RestController
@RequestMapping("/api/auth/employee_manager/position")
@CrossOrigin("*")
public class PositionController {
    /////variable that represent the service in this endpoint class
    @Autowired
    private PositionService positionService ;
    @Autowired
    private PositionPdfModel positionPdfModel ;

    ////endpoint allowing to make a position registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Position> savePosition(@RequestBody Position position){
        Position positionAdded = positionService.create(position);
        return  new ResponseEntity<Position>(positionAdded, HttpStatus.CREATED);
    }

    ///endpoint allowing to get one position
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Position> getPosition(@PathVariable Long id){
        Position positionGot = positionService.get(id);
        return  new ResponseEntity<Position>(positionGot , HttpStatus.FOUND);
    }


    /////endpoint allowing to get all positions
    @GetMapping(value = "/all")
    public ResponseEntity<List<Position>> allPosition(){
        List<Position> listPosition = positionService.all() ;
        return new ResponseEntity<List<Position>>(listPosition , HttpStatus.OK);
    }


    /////endpoint allowing to update  one position by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<Position> editPosition(@PathVariable Long id  , @RequestBody Position position){
        Position positionEdited = positionService.edit(id , position);
        return  new ResponseEntity<Position>(positionEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one position
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deletePosition(@PathVariable Long id){
        return positionService.delete(id);
    }


    /////endpoint allowing to do a research
    @GetMapping(value = "/search/{keyword}")
    public List<Position> searchPosition(@PathVariable String keyword){
        return positionService.search(keyword);
    }
    @GetMapping(value = "/report/pdf")
    public void reportPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        // Define a DateFormat for the filename
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());

        // Set the Content-Disposition header to suggest a filename for download
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=liste_poste_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Call your method to write the PDF content to the response output stream
        this.positionPdfModel.export(response);
    }





}
