package com.EmployeeManagment.Source.Message;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMsgServer {
    private String message ;
    private String emailRecipient ;


}
