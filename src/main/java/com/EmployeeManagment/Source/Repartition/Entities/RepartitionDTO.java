package com.EmployeeManagment.Source.Repartition.Entities;


import com.EmployeeManagment.Source.Employee.Entity.EmployeeDTO;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduledDTO;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class RepartitionDTO {

    private Long id ;
    private EmployeeDTO employee ;
    private TaskScheduledDTO taskScheduled ;
    private byte[] photo;
    private String function ;
}
