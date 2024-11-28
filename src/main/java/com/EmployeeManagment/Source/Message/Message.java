package com.EmployeeManagment.Source.Message;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String content;
    private String sender;
    private MessageType type;
}
