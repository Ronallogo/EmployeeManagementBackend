package com.EmployeeManagment.Source.Pay_Stub.Entity;

import com.EmployeeManagment.Source.Employee.Entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

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
    private LocalDate paymentDate ;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;

    public PayStub(LocalDate date, Integer bonus, Employee employee, Integer amount , Integer nbrTasks){
        this.setPaymentDate(date);
        this.setBonus(bonus);
        this.setEmployee(employee);
        this.setAmount(amount);
        this.setNbrTasks(nbrTasks);

    }


}
