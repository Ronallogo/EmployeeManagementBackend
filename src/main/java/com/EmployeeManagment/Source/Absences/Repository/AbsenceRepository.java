package com.EmployeeManagment.Source.Absences.Repository;

import com.EmployeeManagment.Source.Absences.Entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AbsenceRepository extends JpaRepository<Absence , Long> {


    @Query(value = """
       SELECT * FROM absence WHERE employee_id IN\s
       (SELECT id FROM employee WHERE name LIKE CONCAT('%', :keyword, '%')\s
       OR surname LIKE CONCAT('%', :keyword, '%'))
       """, nativeQuery = true)
    List<Absence> search(@Param("keyword") String keyword);
  @Query(value = "SELECT * FROM absence WHERE employee_id = :keyword", nativeQuery = true)
  Optional<Absence> searchByIdEmployee(@Param("keyword") Long keyword);


    @Query(value = "SELECT COUNT(id) FROM absence a WHERE  a.employee_id = :id_e;  " , nativeQuery = true)
    Integer AllAbsencesForOne(@Param("id_e") Long employee);

}
