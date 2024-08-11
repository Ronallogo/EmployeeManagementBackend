package com.EmployeeManagment.Source.Content.Service;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Content.Exception.ContentNotFoundException;
import com.EmployeeManagment.Source.Content.Repository.ContentRepository;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/////class service for content
@Service
@Transactional
public class ContentService {

    /////Variable representing the repository here
    @Autowired
    private ContentRepository contentRepository ;

    ///function to create a content
    public Content create(Content content){
       return  contentRepository.save(content)  ;
    }

    ///function to get all contents
    public List<Content> all(){
       return  contentRepository.findAll();
    }

    ///function to get a content
    public Content get(Long id){
        return contentRepository.findById(id)
                .orElseThrow(()-> new ContentNotFoundException("this content do not exist !!!"));
    }

    /////function to update a content
    public Content edit(Long id , Content content){
        content.setId(id);
        return  contentRepository.save(content);
    }


    ////function to search a content

    public List<Content> search(String keyword){
        return contentRepository.researchContent(keyword);
    }


    /////function to delete a content

    public boolean delete(Long id){
        //// check if this employee exist
        if(contentRepository.existsById(id)){
            ///try to delete otherwise throw the error
            try{
                 contentRepository.deleteById(id);
                return  true ;
            }
            catch (Exception e ){
                throw  new RuntimeException("make sure that the content is not in relationship with any task schedule !!");
            }
        }
        else{
            throw new EmployeeNotFoundException("this content does not exist or maybe already deleted !!!");
        }
    }


}
