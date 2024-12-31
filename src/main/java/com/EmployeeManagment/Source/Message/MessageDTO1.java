package com.EmployeeManagment.Source.Message;

import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import lombok.*;

import java.sql.Blob;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageDTO1 {


    private Long id ;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Blob file ;
    private String content;
    private String sender;
    private String chatId ;
    private String receiver;
    private String name ;
    private String type ;
    private String date ;
    private String nature ;
    private String extension ;
    private boolean delete_for_sender ;
    private boolean delete_for_recipient ;


    public MessageDTO1(Long id, String sender, String receiver, String type, String nature , String c) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.nature = nature;
        this.content = c ;
    }
}
