package com.EmployeeManagment.Source.Pay_Stub.Repository;

import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayStubRepository  extends JpaRepository<PayStub, Long> {
}
