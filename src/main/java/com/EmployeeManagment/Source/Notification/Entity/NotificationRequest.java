package com.EmployeeManagment.Source.Notification.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {


    private Long id ;
    private String message ;
    private Long employee ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date ;
    private String type ;
}
