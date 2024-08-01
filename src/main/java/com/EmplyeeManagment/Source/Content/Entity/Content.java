package com.EmplyeeManagment.Source.Content.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Content")
public class Content implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String title ;
    private String theme ;
    private String nature ;
    private String language ;
    private Date creation_date ;
    private String status ;

    Content(String title , String theme , String nature , String language , Date creation_date){
        this.setLanguage(language);
        this.setTheme(theme);
        this.setTitle(title);
        this.setCreation_date(creation_date);
        this.setNature(nature);

    }

}
