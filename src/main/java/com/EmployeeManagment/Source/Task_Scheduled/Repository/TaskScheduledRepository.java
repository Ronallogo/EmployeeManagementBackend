package com.EmployeeManagment.Source.Task_Scheduled.Repository;

import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.config.Task;

import java.util.List;

public interface TaskScheduledRepository extends JpaRepository<TaskScheduled, Long> {
    /**
     *  function that allow to take the sum of task did
     *  for an employee X
    *   this value will serve to the value nbrTask in the
    *   entity PayStub for setting the value payment


     */

    @Query(value = "SELECT COUNT(id) FROM task_scheduled WHERE employee_id = :employee ", nativeQuery = true)
    Integer sumTaskDid(@Param("employee") Long employee);


    @Query(value = "SELECT COUNT(id) FROM task_scheduled t WHERE t.employee_id = :employee AND t.status = true", nativeQuery = true)
    Integer sumTaskDidForPayStub(@Param("employee") Long employee);



    /**
     * this list returned will be used for the algorithm
     * to obtain the payment  value
    */
    @Query(value = "SELECT task_inserted_id FROM task_scheduled WHERE employee_id = :employee", nativeQuery = true)
    List<Long> listTaskDid(@Param("employee") Long employee);
    @Query(value = "SELECT id FROM task_scheduled WHERE employee_id = :employee AND status = true", nativeQuery = true)
    List<Long> listTaskDidForPayStub(@Param("employee") Long employee);
    @Query(value = "SELECT * FROM task_scheduled WHERE employee_id = :employee", nativeQuery = true)
    List<TaskScheduled> listTaskDidForOne(@Param("employee") Long employee);


    @Query(value = "SELECT * FROM task_scheduled WHERE status = 1" , nativeQuery = true)
    List<TaskScheduled> listTaskValidate();





}
