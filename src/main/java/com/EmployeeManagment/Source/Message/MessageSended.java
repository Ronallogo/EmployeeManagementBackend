package com.EmployeeManagment.Source.Message;

import com.EmployeeManagment.Source.Employee.Entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="message_sended")
public class MessageSended {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String chatId ;
    private String content;
    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file;
    @ManyToOne
    @JoinColumn(name = "sender", nullable = false)
    private Employee sender;
    @Column(name = "receiver", nullable = false)
    private String receiver;
    private String type;
    private String nature ;
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
