package com.EmployeeManagment.Source.Task_Scheduled.Entity;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Content.Entity.ContentDTO;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Builder
@Data
@Getter
@Setter
public class TaskScheduledDTO {

    private Long id ;
    private String taskInserted ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date beginning ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date end ;
    private boolean status ;
    private String contentTitle ;
    private int nbrPerson ;
    private String type;



}
