package com.example.urs.repository;

import com.example.urs.model.Something;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeRepository extends JpaRepository<Something, Integer> {

}
