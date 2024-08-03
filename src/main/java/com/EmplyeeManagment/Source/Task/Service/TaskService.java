package com.EmplyeeManagment.Source.Task.Service;


import com.EmplyeeManagment.Source.Task.Entity.Task;
import com.EmplyeeManagment.Source.Task.Exception.TaskNotFoundException;
import com.EmplyeeManagment.Source.Task.Repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


//////class service for all logics
@Service
@Transactional
public class TaskService {


    @Autowired
    private TaskRepository taskRepository ;

    /////function for task creation
    public Task create (Task task){return   taskRepository.save(task);}


    ////function for getting all task
    public List<Task> all(){return taskRepository.findAll() ; }

    ////function for getting one task
    public Task get(Long id){
        return taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException("task with id" + id + "do not  exist!!!"));
    }


    ////function to update a task

    public Task edit(Long id  ,  Task  task){
        task.setId(id);
        return  taskRepository.save(task);
    }

    ///function to delete task
    public boolean delete(Long id){
        if(taskRepository.existsById(id)){
           try{
               taskRepository.deleteById(id);
               return true ;
           }catch (Exception e){
            throw  new RuntimeException("make sure that this task is not inserted");
           }
        }
        else{
            throw new TaskNotFoundException("this task do not exist or maybe already deleted")  ;
        }

    }


    ///function to make a research
    public List<Task> search(String keyword){
        return  taskRepository.researchByName(keyword);
    }
}
