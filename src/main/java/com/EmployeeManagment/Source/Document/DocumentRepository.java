package com.EmployeeManagment.Source.Document;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface DocumentRepository extends JpaRepository<Document , Long> {



    @Query(value="SELECT * FROM document WHERE message_id = :message" , nativeQuery = true)
    Optional<Document> findByMessage(Long message);
}
