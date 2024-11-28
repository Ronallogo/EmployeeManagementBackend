package com.EmployeeManagment.Source.Repartition.Entities;

import com.EmployeeManagment.Source.Employee.Entity.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepartitionDTO3 {

    private List<EmployeeDTO> listEmployee ;
    private Long taskScheduled_id ;
    private  String taskScheduled_name ;
    private boolean status ;

}
