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
@Table(name="message")
public class Message implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String chatId ;
    private String content;
    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file;

    @Column(name = "recipient", nullable = false)
    private  String recipient;
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false  )
    private Employee owner;
    private String type;
    private String nature ;
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
