package com.EmplyeeManagment.Source.Pay_Stub.Entity;

import com.EmplyeeManagment.Source.Employee.Entity.Employee;
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
@Table(name = "Pay_Stub")
public class PayStub implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Integer amount ;
    private Integer nbrTasks ;
    private Integer bonus ;
    private Date paymentDate ;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;

    PayStub(Date date , Integer bonus , Employee employee){
        this.setPaymentDate(date);
        this.setBonus(bonus);
        this.setEmployee(employee);

    }

}
