package com.kiss.service.impl;

import com.kiss.jpa.UserJpa;
import com.kiss.model.UserModel;
import com.kiss.service.UserService;
import com.kiss.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{

    @Autowired
    private UserJpa userJpa;
    @Override
    protected void init() {
        jpaRepository = userJpa;
    }
}
