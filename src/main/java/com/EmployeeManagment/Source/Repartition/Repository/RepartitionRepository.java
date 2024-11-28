package com.EmployeeManagment.Source.Repartition.Repository;

import com.EmployeeManagment.Source.Repartition.Entities.Repartition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface RepartitionRepository  extends JpaRepository<Repartition , Long> {



    @Query(value = "SELECT * FROM repartition WHERE task_scheduled_id = :task_id" ,nativeQuery = true)
    List<Repartition> SearchByTaskScheduled_id(@Param("task_id") Long task_id);

    @Query(value = "SELECT r.employee_id  FROM repartition r WHERE task_scheduled_id = :task_id" ,nativeQuery = true)
    List<Long> SearchByEmployee_taskId(@Param("task_id") Long task_id);

    @Query(value = "SELECT t.task_name FROM repartition r " +
            "INNER JOIN task_scheduled ts ON ts.id = r.task_scheduled_id " +
            "INNER JOIN task_inserted ti ON ti.id = ts.task_inserted_id " +
            "INNER JOIN task t ON t.id = ti.task_id WHERE r.employee_id = :employee_id AND ts.status = false AND ts.check_for_payement = false;" ,nativeQuery = true)
    List<String> SearchTaskNameByEmployeeId(@Param("employee_id") Long employee_id);




    @Modifying
    @Query(value = "DELETE  FROM repartition WHERE task_scheduled_id = :task_id" ,nativeQuery = true)
     void DeleteByTaskScheduled_id(@Param("task_id") Long task_id);

}
