package com.example.urs.repository;

import com.example.urs.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findByOwnerOrderByValidUntilDesc(String owner);
}
