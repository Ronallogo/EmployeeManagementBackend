package com.EmployeeManagment.Source.TimeOff.Repository;


import com.EmployeeManagment.Source.TimeOff.Entity.TimeOff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeOffRepository extends JpaRepository<TimeOff, Long> {
}
