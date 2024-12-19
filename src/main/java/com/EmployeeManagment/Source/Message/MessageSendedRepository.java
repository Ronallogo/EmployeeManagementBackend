package com.EmployeeManagment.Source.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;


@EnableJpaRepositories
public interface MessageSendedRepository extends JpaRepository<MessageSended , Long> {
    @Query(  value = "SELECT m.id FROM `message_sended` m WHERE m.content LIKE  ':content' "
            , nativeQuery = true)
     Long findMessageSentForOne(@Param("content") String content)  ;

  //  List<MessageSended> findBySenderOrderByTimestampAsc(String sender);

}


