package com.EmployeeManagment.Source.Absences.Repository;

import com.EmployeeManagment.Source.Absences.Entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence , Long> {

}
