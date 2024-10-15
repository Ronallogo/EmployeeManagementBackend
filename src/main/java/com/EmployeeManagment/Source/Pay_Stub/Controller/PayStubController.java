package com.EmployeeManagment.Source.Pay_Stub.Controller;



import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStubRequest;
import com.EmployeeManagment.Source.Pay_Stub.Repository.PayStubRepository;
import com.EmployeeManagment.Source.Pay_Stub.Service.PayStubService;
import com.EmployeeManagment.Source.Pay_Stub.Service.ReportServicePayStub;
import com.EmployeeManagment.Source.report.reportPayStub.PayStubPdfModel;
import jakarta.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/employee_manager/payStub")
@CrossOrigin("*")
public class PayStubController {

    /////variable that represent the service in this endpoint class
    @Autowired
    private PayStubService PayStubService ;
    @Autowired
    private ReportServicePayStub report ;
    @Autowired
    private PayStubRepository payStubRepository ;
    @Autowired
    private PayStubPdfModel payStubPdfModel ;

    ////endpoint allowing to make a PayStub registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PayStub> savePayStub(@RequestBody PayStubRequest PayStubRequest){
        PayStub PayStubAdded = PayStubService.create(PayStubRequest);
        return  new ResponseEntity<PayStub>(PayStubAdded, HttpStatus.CREATED);
    }

    ///endpoint allowing to get one PayStubInserted
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<PayStub> getPayStub(@PathVariable Long id){
        PayStub PayStubGot = PayStubService.get(id);
        return  new ResponseEntity<PayStub>(PayStubGot , HttpStatus.FOUND);
    }


    /////endpoint allowing to get all PayStub
    @GetMapping(value = "/all")
    public ResponseEntity<List<PayStub>> allPayStub(){
        List<PayStub> listPayStub = PayStubService.all() ;
        return new ResponseEntity<List<PayStub>>(listPayStub , HttpStatus.OK);
    }


    @GetMapping(value = "/search/{keyword}")
    public ResponseEntity<List<PayStub>> SearchPayStub(@PathVariable String keyword){
        List<PayStub> listPayStub = PayStubService.search(keyword) ;
        return new ResponseEntity<List<PayStub>>(listPayStub , HttpStatus.OK);
    }
    @GetMapping(value = "/searchById/{id}")
    public ResponseEntity<Optional<PayStub>> SearchPayStubById(@PathVariable Long id){
        Optional<PayStub> PayStub = PayStubService.searchById(id) ;
        return new ResponseEntity<Optional<PayStub>>(PayStub , HttpStatus.OK);
    }
    @GetMapping(value = "/getPayStubForOne/{email}")
    public ResponseEntity<PayStub> getPayStub(@PathVariable String email){
        PayStub PayStub = PayStubService.getPayStubForOneEmployee(email) ;
        return new ResponseEntity<PayStub>(PayStub , HttpStatus.OK);
    }


    /////endpoint allowing to update  one PayStub by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<PayStub> editPayStub(@PathVariable Long id  , @RequestBody PayStubRequest PayStubRequest){
        PayStub PayStubEdited = PayStubService.edit(id , PayStubRequest);
        return  new ResponseEntity<PayStub>(PayStubEdited , HttpStatus.OK);
    }
    @PutMapping(value = "/refresh/{id}")
    public ResponseEntity<PayStub> refreshPayStub(@PathVariable Long id  , @RequestBody PayStubRequest PayStubRequest){
        PayStub PayStubEdited = PayStubService.refresh(id , PayStubRequest);
        return  new ResponseEntity<PayStub>(PayStubEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one PayStub
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deletePayStub(@PathVariable Long id){
        return PayStubService.delete(id);
    }

    @GetMapping(value = "/report/pdf/{id}")
    public void reportPdf(HttpServletResponse response , @PathVariable Long id) throws IOException {
        response.setContentType("application/pdf");

        // Define a DateFormat for the filename
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());

        // Set the Content-Disposition header to suggest a filename for download
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=bulletin_de_paie_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Call your method to write the PDF content to the response output stream
        this.payStubPdfModel.generatedPdf2( id, response);
    }

    @GetMapping("/report/{format}/{id}")
    public String generatedReport(@PathVariable String format , @PathVariable Long id) throws JRException, FileNotFoundException {
            return report.generatePayStubReport(id, format);
    }
    @GetMapping("/generate/{Id}")
    public void generatePdf(@PathVariable Long Id, HttpServletResponse response) throws Exception {
        byte[] pdfBytes = report.generatePdf(Id);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bulletin_de_paie.pdf");
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }



}
