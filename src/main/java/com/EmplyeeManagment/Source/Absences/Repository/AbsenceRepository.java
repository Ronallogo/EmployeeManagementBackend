package com.EmplyeeManagment.Source.Absences.Repository;

import com.EmplyeeManagment.Source.Absences.Entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence , Long> {

}
