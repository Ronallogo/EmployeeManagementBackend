package com.EmployeeManagment.Source.Task_Scheduled.Entity;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.Repartition.Entities.Repartition;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TaskScheduled")
public class TaskScheduled implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne
    @JoinColumn(name = "taskInserted_id", nullable = false)
    private TaskInserted taskInserted ;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date  beginning ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date end ;
    private int nbrPerson ;
    private String type;
    private boolean status ;
    private  boolean checkForPayement ;
    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content ;



    @JsonIgnore
    @OneToMany(mappedBy = "taskScheduled", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Repartition> repartition;

    //////////constructor for creation
     public   TaskScheduled(
             TaskInserted taskInserted  ,
             Date  beginning,
             Date end ,
             Content content

     ){
            this.setTaskInserted(taskInserted);

            this.setBeginning(beginning);
            this.setEnd(end);
            this.setContent(content);
            this.setCheckForPayement(false);

     }

    public TaskScheduled(Long id, TaskInserted t , Date beginning, Date end, Content c , boolean status) {
        this.setId(id);
        this.setTaskInserted(t);

        this.setBeginning(beginning);
        this.setEnd(end);
        this.setContent(c);
        this.setStatus(status);
        this.setCheckForPayement(false);
    }


    public TaskScheduled(Long id, TaskInserted t  , Date beginning, Date end, Content c , boolean status , String type , int nbrPerson) {
        this.setId(id);
        this.setTaskInserted(t);
        this.setNbrPerson(nbrPerson);
        this.setType(type);
        this.setBeginning(beginning);
        this.setEnd(end);
        this.setContent(c);
        this.setStatus(status);
        this.setCheckForPayement(false);
    }



    public TaskScheduled(  TaskInserted t  , Date beginning, Date end, Content c , boolean status , String type , int nbrPerson) {
        this.setTaskInserted(t);

        this.setBeginning(beginning);
        this.setEnd(end);
        this.setContent(c);
        this.setStatus(status);
        this.setNbrPerson(nbrPerson);
        this.setType(type);
        this.setCheckForPayement(false);

    }
}
