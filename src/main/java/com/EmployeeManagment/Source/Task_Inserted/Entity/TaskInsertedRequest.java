package com.EmployeeManagment.Source.Task_Inserted.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TaskInsertedRequest {

    private Long id ;
    private Long position ;
    private Long task ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date_insertion ;
    private Integer gain_task_post ;
    private String task_type ;
    private  int nbrPerson ;

    TaskInsertedRequest(Long position , Long task , Date date , Integer gain_task_post){
            this.setTask(task);
            this.setPosition(position);
            this.setGain_task_post(gain_task_post);
            this.setDate_insertion(date);
    }



}
