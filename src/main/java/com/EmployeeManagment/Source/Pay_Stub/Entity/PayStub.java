package com.EmployeeManagment.Source.Pay_Stub.Entity;

import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
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
    private Integer bonus  ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date paymentDate ;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;
//private boolean bonusIsGive ;

    public PayStub(Date date, Integer bonus, Employee employee, Integer amount , Integer nbrTasks){
        this.setPaymentDate(date);
        this.setBonus(bonus);
        this.setEmployee(employee);
        this.setAmount(amount);
        this.setNbrTasks(nbrTasks);

    }


}
