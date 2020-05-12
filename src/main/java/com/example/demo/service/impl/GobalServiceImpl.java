package com.example.demo.service.impl;

import com.example.demo.mapper.GobalMapper;
import com.example.demo.service.GobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GobalServiceImpl implements GobalService {
    @Autowired
    private GobalMapper mapper;
    @Override
    public int getUserIdByToken(String token) {
        return mapper.getUserIdByToken(token);
    }
}
