package com.EmployeeManagment.Source.Repartition.Entities;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Data
@Getter
@Setter
public class RepartitionRequest {


    private Long id ;
    private Long employee ;
    private Long taskScheduled ;
    private String function ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date ;




    public RepartitionRequest(Long e , Long taskScheduled , String function , Date date ){
        this.setEmployee(e) ;
        this.setFunction(function);
        this.setTaskScheduled(taskScheduled);
        this.setDate(date);
    }


}
