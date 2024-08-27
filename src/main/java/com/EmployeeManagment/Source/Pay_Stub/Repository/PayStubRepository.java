package com.EmployeeManagment.Source.Pay_Stub.Repository;

import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface PayStubRepository  extends JpaRepository<PayStub, Long> {

    String valueForOne = "p.id , p.amount , p.nbr_task , p.bonus , p.payment_date  , p.employee_id ";

    @Query(value = """
            SELECT p.id , p.amount , p.bonus , p.nbr_tasks , p.payment_date , p.employee_id
            FROM pay_stub p
            INNER JOIN employee e ON e.id = p.employee_id
            WHERE e.name LIKE CONCAT('%', :keyword, '%')
               OR e.surname LIKE CONCAT('%', :keyword, '%');""", nativeQuery = true)
    List<PayStub> searchByEmployee(@Param("keyword") String keyword);

    @Query(value = "SELECT "+valueForOne+"FROM pay_stub p  INNER JOIN employee e  ON e.id = p.employee_id" +
            "WHERE e.email = :email ;" , nativeQuery = true)
    Optional<PayStub> getForOne(@Param("email") String email);

}
