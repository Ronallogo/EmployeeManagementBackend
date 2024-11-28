package com.EmployeeManagment.Source.Task_Scheduled.Repository;

import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


    @Query(value = "SELECT ts.id FROM task_scheduled ts " +
            "INNER JOIN task_inserted ti ON ts.task_inserted_id = ti.id " +
            "INNER JOIN task t ON ti.task_id = t.id WHERE t.task_name LIKE %:keyword%", nativeQuery = true)
    List<Long> fetchTaskScheduled(@Param("keyword") String keyword);

    @Query(value = "SELECT  ts.id , ts.beginning , ts.end , ts.status , ts.content_id , ts.task_inserted_id , ts.nbr_person , ts.type , ts.check_for_payement FROM task_scheduled ts " +
            "INNER JOIN task_inserted ti ON ts.task_inserted_id = ti.id " +
            "INNER JOIN task t ON ti.task_id = t.id WHERE t.task_name LIKE %:keyword%", nativeQuery = true)
    List<TaskScheduled> searchTaskScheduled(@Param("keyword") String keyword);
    @Query(value = "SELECT  ti.gain_task_post FROM task_scheduled ts " +
            "INNER JOIN task_inserted ti ON ts.task_inserted_id = ti.id " +
            " WHERE ts.id  = :id_task", nativeQuery = true)
     Integer getAmountTaskScheduled(@Param("id_task") Long id_task);


    @Query(value = "SELECT ts.id , ts.beginning , ts.end , ts.status , ts.content_id , ts.task_inserted_id ," +
            " ts.nbr_person , ts.type , ts.check_for_payement FROM task_scheduled ts " +
            "INNER JOIN task_inserted ti ON ts.task_inserted_id = ti.id " +
            "INNER JOIN position p ON ti.position_id = p.id WHERE p.id = :position_id ;", nativeQuery = true)
    List<TaskScheduled> fetchTaskScheduled_2(@Param("position_id") Long position_id);



    @Query(value = "SELECT ts.id , ts.beginning , ts.end , ts.status , ts.content_id , ts.task_inserted_id ," +
            " ts.nbr_person , ts.type , ts.check_for_payement FROM task_scheduled ts " +
            "INNER JOIN repartition r ON ts.id = r.task_scheduled_id " +
            "  WHERE r.employee_id = :employee_id AND ts.status = true AND ts.check_for_payement = false ;", nativeQuery = true)
    List<TaskScheduled> fetchTaskScheduled_3(@Param("employee_id") Long employee_id);

    @Query(value = "SELECT ts.id FROM task_scheduled ts " +
            "INNER JOIN task_inserted ti ON ts.task_inserted_id = ti.id " +
            "INNER JOIN position p ON ti.position_id = p.id;", nativeQuery = true)
    List<Long> fetchTaskScheduled_3();








}
