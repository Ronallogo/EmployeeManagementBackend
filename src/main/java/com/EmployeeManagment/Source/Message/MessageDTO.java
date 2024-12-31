package com.EmployeeManagment.Source.Message;



import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageDTO {


    private Long id ;

    private String content;
    private String sender;
    private String chatId ;
    private String receiver;
    private String type ;
    private String nature ;
    private String file_name ;
    private String extension ;



}


