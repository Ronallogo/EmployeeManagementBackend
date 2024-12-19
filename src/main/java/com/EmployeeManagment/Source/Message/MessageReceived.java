package com.EmployeeManagment.Source.Message;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="message_received")
public class MessageReceived implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String content;
    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file;

    @Column(name = "sender", nullable = false)
    private  String sender;
    @ManyToOne
    @JoinColumn(name = "receiver", nullable = false  )
    private Employee receiver;
    private String type;
    private String nature ;
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
