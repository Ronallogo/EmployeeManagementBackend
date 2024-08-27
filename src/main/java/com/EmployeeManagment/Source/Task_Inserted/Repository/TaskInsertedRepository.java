package com.EmployeeManagment.Source.Task_Inserted.Repository;

import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskInsertedRepository extends JpaRepository<TaskInserted , Long> {
    /**
     *th
     * */
    @Query(value = "SELECT  t.gain_task_post FROM task_inserted AS t" +
            "WHERE id = :id_task", nativeQuery = true)
    public Integer getGainForOne(@Param("id_task")Long id);

    @Query(value = "SELECT  * FROM task_inserted  WHERE position_id = :id_position" , nativeQuery = true)
    List<TaskInserted> allTaskForOnePosition(@Param("id_position")Long id_position);
}
