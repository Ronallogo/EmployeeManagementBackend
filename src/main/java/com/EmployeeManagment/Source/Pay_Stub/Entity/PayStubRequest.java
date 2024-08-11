package com.EmployeeManagment.Source.Pay_Stub.Entity;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

/**
 * this kind of class allow to receive some request where the entity is the child table
 * it will be used for creation updating
 *  */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PayStubRequest {

    private Long id;
    private Integer amount = 0;
    private Integer nbrTasks = 0;
    private Integer bonus = 0;
    private LocalDate paymentDate;
    private Long employee;


    public PayStubRequest(
            Integer amount,
            Integer nbrTasks,
            Integer bonus,
            LocalDate paymentDate,
            Long employee
    ) {
        this.setPaymentDate(paymentDate);
        this.setBonus(bonus);
        this.setEmployee(employee);
        this.setNbrTasks(nbrTasks);
        this.setAmount(amount);

    }
    public PayStubRequest(Long employee , LocalDate paymentDate){
        this.setEmployee(employee);
        this.setPaymentDate(paymentDate);
    }

}