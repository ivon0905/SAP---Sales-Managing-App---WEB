package com.example.demo.service;

import com.example.demo.models.UserRequest;
import com.example.demo.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RequestsService {
    @Autowired
    private RequestRepository repo;

    public UserRequest get(Long id) {
        return repo.findById(id).get();
    }
}
