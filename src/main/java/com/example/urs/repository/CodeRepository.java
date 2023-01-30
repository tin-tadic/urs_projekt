package com.example.urs.repository;

import com.example.urs.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findByOwnerOrderByValidUntilDesc(String owner);
    Optional<Code> findByCode(String code);
}
