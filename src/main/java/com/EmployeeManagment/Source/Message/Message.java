package com.EmployeeManagment.Source.Message;


import com.EmployeeManagment.Source.Document.Document;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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


    @Column(name = "recipient", nullable = false)
    private  String recipient;
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false  )
    private Employee owner;
    private String type;
    private String nature ;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    private boolean delete_for_sender ;
    private boolean delete_for_recipient ;


    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Document> documentList;
}
