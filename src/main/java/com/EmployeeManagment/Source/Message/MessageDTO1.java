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
public class MessageDTO1 {


    private Long id ;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] file ;
    private String content;
    private String sender;
    private String chatId ;
    private String receiver;
    private String type ;
    private String nature ;


    public MessageDTO1(Long id, String sender, String receiver, String type, String nature , String c) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.nature = nature;
        this.content = c ;
    }
}
