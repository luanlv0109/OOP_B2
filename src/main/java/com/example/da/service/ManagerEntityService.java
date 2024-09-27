package com.example.da.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManagerEntityService {
    public List<String> getEntityNames(String packageName);

}
