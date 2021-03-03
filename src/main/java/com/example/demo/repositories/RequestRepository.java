package com.example.demo.repositories;

import com.example.demo.models.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<UserRequest,Long> {
}
