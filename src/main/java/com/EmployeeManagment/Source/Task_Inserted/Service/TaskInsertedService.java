package com.EmployeeManagment.Source.Task_Inserted.Service;


import com.EmployeeManagment.Source.Task.Entity.Task;
import com.EmployeeManagment.Source.Task.Repository.TaskRepository;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.EmployeeManagment.Source.Task_Inserted.Exception.DuplicateTaskInsertedException;
import com.EmployeeManagment.Source.Task_Inserted.Exception.TaskInsertedNotFoundException;
import com.EmployeeManagment.Source.Task_Inserted.Repository.TaskInsertedRepository;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.Position.Exception.PositionNotFoundException;
import com.EmployeeManagment.Source.Position.Repository.PositionRepository;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInsertedRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


//////taskInserted service
@Service
@Transactional
public class TaskInsertedService {

    //////variable representing the repository here
    @Autowired
    TaskInsertedRepository taskInsertedRepository ;
    @Autowired
    PositionRepository positionRepository ;
    @Autowired
    TaskRepository taskRepository ;

    ///function to create a taskInserted
    public TaskInserted create(TaskInsertedRequest taskInsertedRequest){
        ///// fetching of task and position otherwise throw an error
        Task t = taskRepository.findById(taskInsertedRequest.getTask())
                .orElseThrow(()-> new TaskInsertedNotFoundException("task not found to make insertion")) ;
        Position p = positionRepository.findById(taskInsertedRequest.getPosition())
                .orElseThrow(()-> new PositionNotFoundException("position not found to make insertion"));

        ////make sure that this task inserted is unique
        if(!checkUnity(p.getId() , t.getId() , taskInsertedRequest.getGain_task_post())){
            throw  new DuplicateTaskInsertedException("this task is already inserted");
        }
        ////  make the TaskInserted registration
        return taskInsertedRepository.save(new TaskInserted(
                p ,
                t ,
                taskInsertedRequest.getDate_insertion()
                ,  taskInsertedRequest.getGain_task_post()
        )) ;


    }

    public boolean checkUnity(Long id_position , Long id_task ,Integer gain){
        List<TaskInserted> taskList = taskInsertedRepository.findAll() ;

        for (TaskInserted t: taskList) {
                if(
                        Objects.equals(t.getTask().getId(), id_task) &&
                                Objects.equals(t.getPosition().getId(), id_position) &&
                                Objects.equals(t.getGain_task_post(), gain)
                ){
                    return false ;
                }
        }
        return true ;

    }



    /////function  to find one TaskInserted
    public  TaskInserted get(Long id){
        return taskInsertedRepository.findById(id)
                .orElseThrow(()-> new TaskInsertedNotFoundException("this task is not inserted ... !!"));
    }

    /////function to update one taskInserted
    public TaskInserted edit(Long id , TaskInsertedRequest taskInsertedRequest){
        /*
            * fetch the matching position and  task for update if its are updated
            * or make the update for the remain
        */

        Task t = taskRepository.findById(taskInsertedRequest.getTask())
                .orElseThrow(()-> new TaskInsertedNotFoundException("task not found to make  the update working !!")) ;
        Position p = positionRepository.findById(taskInsertedRequest.getPosition())
                .orElseThrow(()-> new PositionNotFoundException("position not found to make the update working !!"));

        if(!checkUnity(p.getId() , t.getId() ,taskInsertedRequest.getGain_task_post() )){
            throw  new DuplicateTaskInsertedException("this task is already inserted");
        }
        return taskInsertedRepository.save(new TaskInserted(
                id ,
                p ,
                t ,
                taskInsertedRequest.getDate_insertion(),
                taskInsertedRequest.getGain_task_post())) ;

    }
    ///function to find all taskInserted
    public List<TaskInserted> all(){ return  taskInsertedRepository.findAll();}

    ///function for delete a task Inserted
    public boolean delete(Long id){
        ////check if this task inserted exist or throw an entityNotFound Error
        if( taskInsertedRepository.existsById(id)){
           ////try the deletion or throw an error
           try{
               taskInsertedRepository.deleteById(id);
               return  true ;
           }
           catch (Exception e){
                throw  new RuntimeException("make sure that this task is not schedule");
           }
        }
        else{
            throw new EntityNotFoundException("this task is not inserted !!!") ;
        }
    }

    public List<TaskInserted> allTaskForOnePosition(Long position){
            return  this.taskInsertedRepository.allTaskForOnePosition(position);
    }






}
