package com.EmployeeManagment.Source.Position.Repository;


import com.EmployeeManagment.Source.Position.Entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query(value = "SELECT * FROM position WHERE position_name LIKE CONCAT('%', :keyword, '%') ", nativeQuery = true)
    List<Position> researchByName(@Param("keyword") String keyword);
}
