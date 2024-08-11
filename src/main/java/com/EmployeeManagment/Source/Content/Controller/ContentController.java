package com.EmployeeManagment.Source.Content.Controller;

import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Content.Service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee_manager/content")
@CrossOrigin("*")
public class ContentController {

    @Autowired
    ContentService contentService ;


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

}
