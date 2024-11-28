package com.EmployeeManagment.Source.Content.Entity;


import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ContentDTO {
    private Long id ;
    private String title ;
    private String theme ;
    private String nature ;
    private String language ;
    private Date creation_date ;
    private String status ;

}
