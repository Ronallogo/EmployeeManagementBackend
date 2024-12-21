package com.EmployeeManagment.Source.Message.UserMessage;

import com.EmployeeManagment.Source.Security.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserMessage {
    @Id
    private String nickName;
    private String fullName;
    private String status;
    @ManyToOne
    @JoinColumn(name = "userApp", nullable = false  )
    private User userApp ;




}
