package com.EmployeeManagment.Source.Task_Scheduled.Entity;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskScheduledRequest {


    private Long id ;

    private Long taskInserted ;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date beginning ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date end ;
    private boolean status ;
    private int nbrPerson ;
    private String type;
    private Long content ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date dateUpdate ;



    public TaskScheduledRequest(
         Long taskInserted ,
         Date beginning ,
         Date end ,
         boolean status ,
         Long content
    ){
        this.setTaskInserted(taskInserted);
        this.setBeginning(beginning);
        this.setEnd(end);
        this.setStatus(status);
        this.setContent(content);


    }
}
