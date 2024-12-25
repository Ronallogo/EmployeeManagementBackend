package com.EmployeeManagment.Source.Message;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;


@EnableJpaRepositories
public interface MessageRepository extends JpaRepository<Message, Long> {



   /* @Query(  value = "SELECT ms.id ,  ms.content , ms.file , ms.receiver , ms.type , ms.nature  , mr.sender FROM message_sended ms \n" +
            "JOIN message_received mr ON mr.content = ms.content\n" +
            "WHERE ms.sender = :id OR mr.receiver = :id ORDER BY ms.id DESC;"
             , nativeQuery = true)
    List<Tuple> findMessageReceivedForOne(@Param("id") Long id )  ;*/



    @Query(  value = " SELECT DISTINCT \n" +
            "        ms.id, \n" +
            "        ms.content, \n" +
            "        ms.file, \n" +
            "        ms.receiver, \n" +
            "        ms.type, \n" +
            "        ms.nature, \n" +
            "        mr.sender\n" +
            "    FROM \n" +
            "        message_sended ms\n" +
            "    JOIN \n" +
            "        message_received mr \n" +
            "    ON \n" +
            "        mr.content = ms.content\n" +
            "    WHERE \n" +
            "        ms.sender = :id OR mr.receiver = :id\n" +
            "    ORDER BY \n" +
            "        ms.timestamp ASC"
            , nativeQuery = true)
    List<Tuple> findByReceiverOrderByTimestampAsc(@Param("id") Long id );




}
