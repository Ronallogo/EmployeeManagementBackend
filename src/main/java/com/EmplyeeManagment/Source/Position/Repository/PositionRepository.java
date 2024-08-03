package com.EmplyeeManagment.Source.Position.Repository;


import com.EmplyeeManagment.Source.Position.Entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query(value = "SELECT * FROM position WHERE position_name LIKE %:keyword%  ", nativeQuery = true)
    List<Position> researchByName(@Param("keyword") String keyword);
}
