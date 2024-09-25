package com.EmployeeManagment.Source.Absences.Controller;


import com.EmployeeManagment.Source.Absences.Entity.Absence;
import com.EmployeeManagment.Source.Absences.Entity.AbsenceRequest;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.report.reportAbsence.AbsencePdfModel;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/employee_manager/absence")
@CrossOrigin("*")
public class AbsenceController {
    @Autowired
    com.EmployeeManagment.Source.Absences.Service.AbsenceService AbsenceService ;

    @Autowired
    AbsencePdfModel absencePdfModel ;


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
        return  new ResponseEntity<Absence>( AbsenceGot , HttpStatus.OK);
    }

    @GetMapping(value = "/getForOne/{id}")
    public ResponseEntity<Integer> getNbrAbsenceForOne(@PathVariable Long id){
        return  new ResponseEntity<Integer>(this.AbsenceService.findNbrAbsenceForOne(id) , HttpStatus.OK);
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
 @GetMapping(value = "/searchById/{id}")
    public Optional<Absence> searchAbsenceByIdEmployee(@PathVariable Long id){
        return AbsenceService.searchById(id);
    }



    @GetMapping(value = "/report/pdf")
    public void reportPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        // Define a DateFormat for the filename
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());

        // Set the Content-Disposition header to suggest a filename for download
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=liste_absence_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Call your method to write the PDF content to the response output stream
        this.absencePdfModel.export(response);
    }

}
