package com.EmplyeeManagment.Source.Content.Repository;

import com.EmplyeeManagment.Source.Content.Entity.Content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository  extends JpaRepository<Content, Long> {
    @Query(value = "SELECT * FROM content WHERE title LIKE %:keyword%  OR theme LIKE %:keyword%", nativeQuery = true)
    List<Content> researchContent(@Param("keyword") String keyword);
}
