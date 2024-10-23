package com.EmployeeManagment.Source.Task_Scheduled.Service;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Content.Exception.ContentNotFoundException;
import com.EmployeeManagment.Source.Content.Repository.ContentRepository;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Notification.Entity.Notification;
import com.EmployeeManagment.Source.Notification.Repository.NotificationRepository;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.EmployeeManagment.Source.Task_Inserted.Exception.TaskInsertedNotFoundException;
import com.EmployeeManagment.Source.Task_Inserted.Repository.TaskInsertedRepository;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduledRequest;
import com.EmployeeManagment.Source.Task_Scheduled.Exception.TaskScheduledNotFoundException;
import com.EmployeeManagment.Source.Task_Scheduled.Repository.TaskScheduledRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskScheduledService {

    @Autowired
    private EmployeeRepository employeeRepository ;

    @Autowired
    private TaskInsertedRepository taskInsertedRepository ;

    @Autowired
    private ContentRepository contentRepository ;

    @Autowired
    private   TaskScheduledRepository taskScheduledRepository ;

    @Autowired
    private NotificationRepository notificationRepository ;


    ///////function to create a  task_schedule

    public TaskScheduled create(TaskScheduledRequest taskScheduledRequest){
        /////fetch the employee
        Employee e = employeeRepository.findById(taskScheduledRequest.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("employee not found !!"));
        ///fetch taskInserted
        TaskInserted  t = taskInsertedRepository.findById(taskScheduledRequest.getTaskInserted())
                .orElseThrow(()-> new TaskInsertedNotFoundException("task not insert !!!"));
        //// fetch content

        Content c = contentRepository.findById(taskScheduledRequest.getContent())
                .orElseThrow(()-> new ContentNotFoundException("content not found !!"));

        if(c.getStatus() == "publié"){
            throw new RuntimeException("this content is already published !!!");
        }


        if( taskScheduledRequest.getBeginning().after(taskScheduledRequest.getEnd())) {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }


        this.sendNotification(taskScheduledRequest , e , null);

        /////make the task_schedule registration
        return  taskScheduledRepository.save( new TaskScheduled(
                    t , ///taskInserted
                    e  ,////employee
                    taskScheduledRequest.getBeginning() , //// beginning date
                    taskScheduledRequest.getEnd() ,////end date
                    c ////content

                )
        );
    }


    /////function for update task_schedule

    public  TaskScheduled edit( Long id , TaskScheduledRequest t){

        TaskScheduled taskScheduled = this.taskScheduledRepository.findById(id)
                .orElseThrow(()-> new TaskScheduledNotFoundException("taskScheduled not found"));
        /////check the employee
        Employee e = employeeRepository.findById(t.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("employee not found !!"));
        ///check taskInserted
        TaskInserted  task = taskInsertedRepository.findById(t.getTaskInserted())
                .orElseThrow(()-> new TaskInsertedNotFoundException("task not insert !!!"));
        //// check content

        Content c = contentRepository.findById(t.getContent())
                .orElseThrow(()-> new ContentNotFoundException("content not found !!"));

        this.sendNotification(t , e , id);

        if(t.getBeginning().after(t.getEnd()))
        {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }  /////make the task_schedule updating
        taskScheduled.setTaskInserted(task);
        taskScheduled.setEmployee(e);
        taskScheduled.setContent(c);
        taskScheduled.setStatus(t.isStatus());
        taskScheduled.setBeginning(t.getBeginning());
        taskScheduled.setEnd(t.getEnd());


        return  taskScheduledRepository.save(taskScheduled );




    }
    ////find all task_schedule
    public List<TaskScheduled> all(){return taskScheduledRepository.findAll() ;}


    //// get one task_scheduled
    public TaskScheduled get(Long id){
        return taskScheduledRepository.findById(id)
                .orElseThrow(()-> new TaskScheduledNotFoundException("task not scheduled")) ;
    }

    ///// delete a taskSchedule

    public boolean delete(Long id){
        ////check if this task schedule exist or throw an entityNotFound Error
        if( taskScheduledRepository.existsById(id)){
            ////try the deletion or throw an error
            try{
                taskScheduledRepository.deleteById(id);
                return  true ;
            }
            catch (Exception e){
                throw  new RuntimeException("make sure that this task is not schedule");
            }
        }
        else{
            throw new EntityNotFoundException("this task is not scheduled !!!") ;
        }
    }

    public List<TaskScheduled> taskDidByOne(Long employee){
        return  taskScheduledRepository.listTaskDidForOne(employee);

    }
    public Integer nbrTaskForOne(Long employee){
        return this.taskScheduledRepository.sumTaskDid(employee);
    }







    public void sendNotification(TaskScheduledRequest t , Employee e , Long id_task){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(!t.isStatus() && id_task == null){
            this.notificationRepository.save(
                    new Notification(
                            "Vous avez programmé une tache qui doit etre achevée au plus tard le "+t.getEnd() ,
                            e ,
                            t.getBeginning() ,
                            "programmation de tache"
                    )
            );
        } else  if (!t.isStatus()) {
            this.notificationRepository.save(
                    new Notification(
                            "Vous avez modifié la programmation d' une tache qui doit etre achevée au plus tard le  "+t.getEnd() ,
                            e ,
                             null,
                            "programmation de tache"
                    )
            );
        }else{

            Optional<TaskInserted> ti = this.taskInsertedRepository.findById(t.getTaskInserted());

            ti.ifPresent(taskInserted -> this.notificationRepository.save(
                    new Notification(
                            " Votre  " + taskInserted.getTask().getTask_name() + " débuté(e) le " +
                                    t.getBeginning() + " à été validé !!.... veuillez mettre a jour votre bulletin de paie !! ",
                            e,
                            null
                            ,
                            "virement"
                    )
            ));


        }

    }
}


