package com.EmployeeManagment.Source.Document;


import com.EmployeeManagment.Source.Message.Message;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="document")
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_doc ;
    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] content ;
    private String extension ;
    @ManyToOne
    private Message message ;
    private String file_name ;

}
