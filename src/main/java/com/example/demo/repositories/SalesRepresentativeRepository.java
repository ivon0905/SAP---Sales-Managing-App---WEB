package com.example.demo.repositories;

import com.example.demo.models.SalesRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepresentativeRepository extends JpaRepository<SalesRepresentative, Long> {
}
