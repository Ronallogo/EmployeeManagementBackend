package com.EmplyeeManagment.Source.Task.Repository;

import com.EmplyeeManagment.Source.Position.Entity.Position;
import com.EmplyeeManagment.Source.Task.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository   extends JpaRepository<Task , Long> {
    @Query(value = "SELECT * FROM task WHERE task_name LIKE %:keyword%  ", nativeQuery = true)
    List<Task> researchByName(@Param("keyword") String keyword);

}
