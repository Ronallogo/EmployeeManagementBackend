package com.EmployeeManagment.Source.Task_Scheduled.Service;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Content.Exception.ContentNotFoundException;
import com.EmployeeManagment.Source.Content.Repository.ContentRepository;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Notification.Entity.Notification;
import com.EmployeeManagment.Source.Notification.Repository.NotificationRepository;
import com.EmployeeManagment.Source.Pay_Stub.Exception.PayStubNotFoundException;
import com.EmployeeManagment.Source.Pay_Stub.Repository.PayStubRepository;
import com.EmployeeManagment.Source.Repartition.Repository.RepartitionRepository;
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

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TaskScheduledService {

    @Autowired
    private EmployeeRepository employeeRepository ;

    @Autowired
    private PayStubRepository payStubRepository ;

    @Autowired
    private TaskInsertedRepository taskInsertedRepository ;

    @Autowired
    private ContentRepository contentRepository ;

    @Autowired
    private   TaskScheduledRepository taskScheduledRepository ;

    @Autowired
    private RepartitionRepository repartitionRepository ;

    @Autowired
    private NotificationRepository notificationRepository ;


    ///////function to create a  task_schedule
    public TaskScheduled create(TaskScheduledRequest taskScheduledRequest){

        ///fetch taskInserted
        TaskInserted  t = taskInsertedRepository.findById(taskScheduledRequest.getTaskInserted())
                .orElseThrow(()-> new TaskInsertedNotFoundException("task not insert !!!"));
        //// fetch content

        Content c = contentRepository.findById(taskScheduledRequest.getContent())
                .orElseThrow(()-> new ContentNotFoundException("content not found !!"));

        if(Objects.equals(c.getStatus(), "publié")){
            throw new RuntimeException("this content is already published !!!");
        }


        if( taskScheduledRequest.getBeginning().after(taskScheduledRequest.getEnd())) {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }


        /////make the task_schedule registration
        return  taskScheduledRepository.save(
                TaskScheduled.builder()
                        .taskInserted(t)
                        .beginning(taskScheduledRequest.getBeginning())
                        .end(taskScheduledRequest.getEnd())
                        .content(c)
                        .type(taskScheduledRequest.getType())
                        .nbrPerson(taskScheduledRequest.getNbrPerson())
                        .status(false)
                .build());
    }
    /////function for update task_schedule
    public  TaskScheduled edit( Long id , TaskScheduledRequest t){

        TaskScheduled taskScheduled = this.taskScheduledRepository.findById(id)
                .orElseThrow(()-> new TaskScheduledNotFoundException("taskScheduled not found"));

        TaskInserted  task = taskInsertedRepository.findById(t.getTaskInserted())
                .orElseThrow(()-> new TaskInsertedNotFoundException("task not insert !!!"));
        //// check content

        Content c = contentRepository.findById(t.getContent())
                .orElseThrow(()-> new ContentNotFoundException("content not found !!"));

        if(t.isStatus() && !taskScheduled.isCheckForPayement()){
            this.payProgressInValidation(id , t);
        }

        if(t.getBeginning().after(t.getEnd()))
        {
            throw new RuntimeException("check the deviation between the beginning date and the end date !!");
        }
        /////make the task_schedule updating
        taskScheduled.setTaskInserted(task);
        taskScheduled.setContent(c);
        taskScheduled.setStatus(t.isStatus());
        taskScheduled.setBeginning(t.getBeginning());
        taskScheduled.setEnd(t.getEnd());
        taskScheduled.setType(t.getType());
        taskScheduled.setNbrPerson(t.getNbrPerson());
        return  taskScheduledRepository.save(taskScheduled);




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

    public List<TaskScheduled> searchTaskScheduled(String keyword){
        return this.taskScheduledRepository.searchTaskScheduled(keyword);
    }

    public List<TaskScheduled> fetchTaskByPositionId(Long position_id){
        return this.taskScheduledRepository.fetchTaskScheduled_2(position_id);
    }

    public List<TaskScheduled> fetchTaskByEmployeeId(Long employee_id){
        return this.taskScheduledRepository.fetchTaskScheduled_3(employee_id);
    }


    public void payProgressInValidation(Long id_task , TaskScheduledRequest request){
        var task = this.taskScheduledRepository.findById(id_task)
                .orElseThrow(()-> new RuntimeException("taskScheduled not found"));


        List<Long> listEmployee = this.repartitionRepository.SearchByEmployee_taskId(id_task);

        for(Long id : listEmployee){
            this.sendNotificationAndPay(id , task , request.getDateUpdate());
        }
        task.setCheckForPayement(true);
        this.taskScheduledRepository.save(task);

    }

    public void sendNotificationAndPay(Long employee_id , TaskScheduled task , Date date){
        var e = this.employeeRepository.findById(employee_id)
                .orElseThrow( ()-> new EmployeeNotFoundException("employee not found"));

       var pay = this.payStubRepository.getForOne(e.getEmail())
               .orElseThrow(()-> new PayStubNotFoundException("payStub not found"));

       pay.setNbrTasks(pay.getNbrTasks() + 1 );
       pay.setAmount(pay.getAmount() + this.taskScheduledRepository.getAmountTaskScheduled(task.getId()));
       var bonus = pay.getAmount() /10 ;
       pay.setAmount(pay.getAmount() + pay.getBonus());
       pay.setBonus(bonus);
       this.payStubRepository.save(pay);



       this.notificationRepository.save(Notification.builder()
               .views(false)
               .employee(e)
               .date(date)
               .type("virement").message("User @"+e.getName()+" la tache :'"+task.getTaskInserted().getTask().getTask_name()
               +"' a été validé  et un virement a été effectué....Veuillez mettre à jour votre bulletin de paie ."
               )
               .build());

    }




}


