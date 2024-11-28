package com.EmployeeManagment.Source.Repartition.Service;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Entity.EmployeeDTO;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Notification.Entity.Notification;
import com.EmployeeManagment.Source.Notification.Repository.NotificationRepository;
import com.EmployeeManagment.Source.Repartition.Entities.*;
import com.EmployeeManagment.Source.Repartition.Repository.RepartitionRepository;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduledDTO;
import com.EmployeeManagment.Source.Task_Scheduled.Exception.TaskScheduledNotFoundException;
import com.EmployeeManagment.Source.Task_Scheduled.Repository.TaskScheduledRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class RepartitionService {

    @Autowired
    private RepartitionRepository repartitionRepository ;
    @Autowired
    private EmployeeRepository employeeRepository ;
    @Autowired
    private TaskScheduledRepository taskScheduledRepository ;
    @Autowired
    private NotificationRepository notify ;



    public RepartitionDTO create(RepartitionRequest request){

        checkUnicity(request);
        Employee e  =    this.employeeRepository.findById(request.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("Employee not found"));


        TaskScheduled t =  this.taskScheduledRepository.findById(request.getTaskScheduled())
                .orElseThrow(()-> new TaskScheduledNotFoundException("taskScheduled not found"));

        if(!this.checkEmployeeActivitySector(e , t)) throw new RuntimeException("Wrong activity sector");

        Repartition r = this.repartitionRepository.save(new Repartition(e , t   , request.getFunction()));
        if(Objects.equals(t.getType(), "Individuelle") && t.getNbrPerson() < 1 )  throw new RuntimeException("task is full");
        if(t.getNbrPerson() < 0 ) throw new RuntimeException("task is full");
        t.setNbrPerson(t.getNbrPerson() - 1);
        this.taskScheduledRepository.save(t);
        this.notify.save(Notification.builder()
                        .date(request.getDate())
                        .type("asssignement")
                        .message("User @"+e.getName()+",vous avez été assigné à  cette tache : '"+
                                t.getTaskInserted().getTask().getTask_name()+
                                "' dont la dateLine est le "+t.getEnd()
                                )
                        .employee(e)
                .build());






        return RepartitionDTO.builder()
                .id(r.getId())
                .employee(
                        EmployeeDTO.builder()
                                .id(e.getId())
                                .name(e.getName())
                                .position(e.getPosition().getPosition_name())
                                .surname(e.getSurname())
                                .build()
                )
                .taskScheduled(TaskScheduledDTO.builder()
                        .id(t.getId())
                        .end(t.getEnd())
                        .beginning(t.getBeginning())
                        .contentTitle(t.getContent().getTitle())
                        .taskInserted(t.getTaskInserted().getTask().getTask_name())
                        .build())
                .function(request.getFunction())
                .build();


    }

    public RepartitionDTO edit( RepartitionRequest request){

        checkUnicity(request);
        Repartition r = this.repartitionRepository.findById(request.getId())
                .orElseThrow(()->new RuntimeException("repartition not found"));

        Employee e  =    this.employeeRepository.findById(request.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("Employee not found"));



        TaskScheduled t =  this.taskScheduledRepository.findById(request.getTaskScheduled())
                .orElseThrow(()-> new TaskScheduledNotFoundException("taskScheduled not found"));

        if(this.checkEmployeeActivitySector(e , t)) throw new RuntimeException("Wrong activity sector");

        r.setEmployee(e) ;
        r.setFunction(request.getFunction());
        r.setTaskScheduled(t);
        this.notify.save(Notification.builder()
                .date(request.getDate())
                .type("asssignement")
                .message("User @"+e.getName()+",vous avez été assigné à cette tache suivant :'"+
                        t.getTaskInserted().getTask().getTask_name()+
                        "' dont la dateLine est le "+t.getEnd()
                )
                .employee(e)
                .build());


        r = this.repartitionRepository.save(r);


        return RepartitionDTO.builder()
                .id(r.getId())
                .employee(
                        EmployeeDTO.builder()
                                .id(e.getId())
                                .name(e.getName())
                                .position(e.getPosition().getPosition_name())
                                .surname(e.getSurname())
                                .build()
                )
                .taskScheduled(TaskScheduledDTO.builder()
                        .id(t.getId())
                        .end(t.getEnd())
                        .beginning(t.getBeginning())
                        .contentTitle(t.getContent().getTitle())
                        .taskInserted(t.getTaskInserted().getTask().getTask_name())
                        .build())
                .function(request.getFunction())
                .build();

    }


    /*
             this feature return  a list of RepartitionDTO2 which  is the
             employees information list  about the current task_scheduled
    */
    public List<RepartitionDTO2> searchForOneTask(Long taskScheduled_id){
         if(this.taskScheduledRepository.existsById(taskScheduled_id)){
             List<Repartition> list = this.repartitionRepository.SearchByTaskScheduled_id(taskScheduled_id);
             if(list.isEmpty()) return new ArrayList<>();
             return list.stream()
                     .map(repartition -> RepartitionDTO2.builder()
                             .id(repartition.getId())
                             .employee_id(repartition.getEmployee().getId())
                             .name(repartition.getEmployee().getName())
                             .email(repartition.getEmployee().getEmail())
                             .surname(repartition.getEmployee().getSurname())
                             .position(repartition.getEmployee().getPosition().getPosition_name())
                             .photo(repartition.getEmployee().getPhoto())
                             .taskScheduled_id(repartition.getTaskScheduled().getId())
                             .check_for_payement(repartition.getTaskScheduled().isCheckForPayement())
                             .taskScheduled_name(repartition.getTaskScheduled().getTaskInserted().getTask().getTask_name())
                             .function(repartition.getFunction())
                             .build())
                     .toList();

         }else{
            throw new TaskScheduledNotFoundException("TaskScheduled not found");
         }

    }


    /*
        this feature prepare the final data researched  for one task

    */
    public RepartitionDTO3 globalResearchCleaning(Long taskScheduled_id){
        List<RepartitionDTO2> dto2List = this.searchForOneTask(taskScheduled_id)  ;

        var task = this.taskScheduledRepository.findById(taskScheduled_id)
                .orElseThrow(()-> new RuntimeException("taskScheduled not found"));


        List<EmployeeDTO> employeeDTO1List  = new ArrayList<>() ;
        if(dto2List.isEmpty()) return new RepartitionDTO3() ;

        for (RepartitionDTO2 repartitionDTO2 : dto2List) {
            employeeDTO1List.add(EmployeeDTO.builder()
                    .id(repartitionDTO2.getId())
                    .photo(repartitionDTO2.getPhoto())
                    .email(repartitionDTO2.getEmail())
                    .name(repartitionDTO2.getName())
                    .surname(repartitionDTO2.getSurname())
                    .position(repartitionDTO2.getPosition())
                    .function(repartitionDTO2.getFunction())
                    .build()
            );

        }
        return RepartitionDTO3.builder()
                .listEmployee(employeeDTO1List)
                .taskScheduled_id(dto2List.get(0).getTaskScheduled_id())
                .taskScheduled_name(dto2List.get(0).getTaskScheduled_name())
                .status(task.isStatus())
                .build();
    }
    /*this feature make the list of final data researched with id_task_scheduled list taking keyword*/
    public List<RepartitionDTO3> globalResearch(String keyword){
        List<Long> longIdTaskScheduled = this.taskScheduledRepository.fetchTaskScheduled(keyword);

        List<RepartitionDTO3> dto3List = new ArrayList<>();
        for(Long id : longIdTaskScheduled){

            if(
                this.globalResearchCleaning(id).getListEmployee() != null &&
                this.globalResearchCleaning(id).getTaskScheduled_id() != null &&
                this.globalResearchCleaning(id).getTaskScheduled_name() != null
            ){
                 dto3List.add(  this.globalResearchCleaning(id)) ;
            }
        }
        return dto3List ;

    }

    /*this. feature make  a list of  final data researched for one employee*/
    public  List<RepartitionDTO3>researchForOneEmployee(Long id){
        List<String> taskNameList = this.repartitionRepository.SearchTaskNameByEmployeeId(id);
        List<RepartitionDTO3> finalList = new ArrayList<>() ;
        if(!taskNameList.isEmpty()){
            for (String nameTask: taskNameList ) {
               finalList.addAll(this.globalResearch(nameTask));
            }
        }
        return finalList ;

    }




    public List<RepartitionDTO3> allRepartition(){
        List<Long> longIdTaskScheduled = this.taskScheduledRepository.fetchTaskScheduled_3() ;
        if(longIdTaskScheduled.isEmpty())return new ArrayList<>();

        List<RepartitionDTO3> dto3List = new ArrayList<>();
        for(Long id : longIdTaskScheduled){

            if(
                    this.globalResearchCleaning(id).getListEmployee() != null &&
                            this.globalResearchCleaning(id).getTaskScheduled_id() != null &&
                            this.globalResearchCleaning(id).getTaskScheduled_name() != null
            ){
                dto3List.add(  this.globalResearchCleaning(id)) ;
            }
        }
        return dto3List ;

    }


    public boolean deleteRepartitionForTaskScheduled(Long taskScheduled_id){

           if(this.taskScheduledRepository.existsById(taskScheduled_id)){
               Optional<TaskScheduled>t =  this.taskScheduledRepository.findById(taskScheduled_id);
               t.get().setNbrPerson(0);
               this.taskScheduledRepository.save(t.get());
               this.repartitionRepository.DeleteByTaskScheduled_id(taskScheduled_id);
                return  true ;
           }
           else{
            throw new RuntimeException("TaskScheduled not found");
           }
         //   return true ;
    }

    public boolean   dropEmployeeForRepartition(Long  repartition_id ){

        Repartition r = this.repartitionRepository.findById(repartition_id)
                .orElseThrow(()-> new RuntimeException("Employee not found in this repartition"));

        Employee e = this.employeeRepository.findById(r.getEmployee().getId())
                .orElseThrow(()-> new RuntimeException("Employee not found"));

        String task = r.getTaskScheduled().getTaskInserted().getTask().getTask_name();

         var t =   this.taskScheduledRepository.findById(r.getTaskScheduled().getId())
                         .orElseThrow(()->new RuntimeException("taskScheduled not found"));

         t.setNbrPerson(t.getNbrPerson() + 1 );
         this.taskScheduledRepository.save(t);

        this.notify.save(Notification.builder()

                .type("désassignation")
                .message("User @"+e.getName()+",vous avez été désassigné de cette tache :'"+
                       task+"' "+
                        " dont la dateLine est le "+r.getTaskScheduled().getEnd()
                )
                .employee(e)
                .build());

       this.repartitionRepository.deleteById(repartition_id);
        return  true ;
    }

   public boolean checkEmployeeActivitySector(Employee e , TaskScheduled t){
            return Objects.equals(e.getPosition().getId(), t.getTaskInserted().getPosition().getId());

   }


   public void checkUnicity(RepartitionRequest r){
        var repartitionList = this.repartitionRepository.findAll();

        for (Repartition rep: repartitionList) {
            if(Objects.equals(rep.getTaskScheduled().getId(), r.getTaskScheduled()) && Objects.equals(rep.getEmployee().getId(), r.getEmployee())){
                throw new RuntimeException("employee already in  assignment");
            }
        }
   }


}
