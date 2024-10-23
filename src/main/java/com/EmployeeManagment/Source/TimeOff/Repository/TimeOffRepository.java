package com.EmployeeManagment.Source.TimeOff.Repository;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface TimeOffRepository extends JpaRepository<TimeOff, Long> {

   @Query("""
    SELECT t
    FROM TimeOff t
    JOIN FETCH t.timeOffApply a
    JOIN FETCH a.employee e
    WHERE e.name LIKE CONCAT('%', :keyword, '%')
       OR e.surname LIKE CONCAT('%', :keyword, '%')
    """)
List<TimeOff> search(@Param("keyword") String keyword);
   @Query("""
    SELECT t
    FROM TimeOff t
    JOIN FETCH t.timeOffApply a
    JOIN FETCH a.employee e
    WHERE  e.id = :keyword
    """)
   List<TimeOff> searchByIdEmployee(@Param("keyword") Long keyword);



}
