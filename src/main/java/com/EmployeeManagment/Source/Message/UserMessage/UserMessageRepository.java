package com.EmployeeManagment.Source.Message.UserMessage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMessageRepository  extends JpaRepository<UserMessage , String> {

    List<UserMessage> findAllByStatus(String status);
}
