package com.EmployeeManagment.Source.TimeOff.Repository;

import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@EnableJpaRepositories
public interface TimeOffApplyRepository extends JpaRepository<TimeOffApply , Long> {
}
