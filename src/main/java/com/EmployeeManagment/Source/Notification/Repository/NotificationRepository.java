package com.EmployeeManagment.Source.Notification.Repository;

import com.EmployeeManagment.Source.Notification.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface NotificationRepository extends JpaRepository <Notification , Long>{
    @Query(value = "SELECT * FROM notification WHERE employee_id = :id_employee", nativeQuery = true)
    List<Notification> findByEmployee(@Param("id_employee")Long id_employee);

    @Query(value = """
            SELECT *\s
            FROM notification\s
            WHERE type LIKE %:keyword%\s
            AND employee_id = :id_employee\s
            ORDER BY notification.id DESC
            """, nativeQuery = true)
    List<Notification> researchNotification(@Param("keyword") String keyword ,@Param("id_employee")Long id_employee);


}
