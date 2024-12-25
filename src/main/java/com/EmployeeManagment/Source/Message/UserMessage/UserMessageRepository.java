package com.EmployeeManagment.Source.Message.UserMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
@EnableJpaRepositories
public interface UserMessageRepository  extends JpaRepository<UserMessage , String> {

    List<UserMessage> findAllByStatus(String status);
}
