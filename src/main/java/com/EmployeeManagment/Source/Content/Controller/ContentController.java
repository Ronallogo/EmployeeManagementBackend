package com.EmployeeManagment.Source.Content.Controller;

import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Content.Service.ContentService;
import com.EmployeeManagment.Source.report.reportContent.ContentPdfModel;
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
@RequestMapping("/api/auth/employee_manager/content")
@CrossOrigin("*")
public class ContentController {

    @Autowired
    ContentService contentService ;
    @Autowired
    private ContentPdfModel contentPdfModel ;


    ////endpoint allowing to make a  content registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Content> saveContent(@RequestBody Content content){
        Content contentAdded =  contentService.create(content);
        return  new ResponseEntity<Content>( contentAdded, HttpStatus.CREATED);
    }

    ///endpoint allowing to get one  content
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Content> getContent(@PathVariable Long id){
        Content  contentGot =  contentService.get(id);
        return  new ResponseEntity<Content>( contentGot , HttpStatus.FOUND);
    }


    /////endpoint allowing to get all  contents
    @GetMapping(value = "/all")
    public ResponseEntity<List<Content>> allContent(){
        List<Content> listContent =  contentService.all() ;
        return new ResponseEntity<List<Content>>(listContent , HttpStatus.OK);
    }


    /////endpoint allowing to update  one  content by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<Content> editContent(@PathVariable Long id  , @RequestBody  Content  content){
        Content  contentEdited =  contentService.edit(id ,  content);
        return  new ResponseEntity<Content>( contentEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one  content
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deleteContent(@PathVariable Long id){
        return  contentService.delete(id);
    }


    /////endpoint allowing to do a research
    @GetMapping(value = "/search/{keyword}")
    public List<Content> searchContent(@PathVariable String keyword){
        return  contentService.search(keyword);
    }
  @GetMapping(value = "/searchById/{keyword}")
    public Optional<Content> searchContentById(@PathVariable Long keyword){
        return  contentService.searchById(keyword);
    }



    @GetMapping(value = "/report/pdf")
    public void reportPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        // Define a DateFormat for the filename
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());

        // Set the Content-Disposition header to suggest a filename for download
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=liste_contenu_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Call your method to write the PDF content to the response output stream
        this.contentPdfModel.export(response);
    }
}
