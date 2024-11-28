package com.EmployeeManagment.Source.Repartition.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;


@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepartitionDTO2 {

    private Long id ;
    private  Long  employee_id ;
    private String name ;
    private String surname ;
    private String email ;
    private String position;
    @Lob
    @Column(name = "photo", columnDefinition = "MEDIUMBLOB")
    private byte[] photo ;
    private Long taskScheduled_id ;
    private  String taskScheduled_name ;
    private boolean check_for_payement ;
    private String function ;



}
