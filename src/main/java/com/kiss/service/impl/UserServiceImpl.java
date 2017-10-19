package com.kiss.service.impl;

import com.kiss.jpa.UserJpa;
import com.kiss.model.UserModel;
import com.kiss.service.UserService;
import com.kiss.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 11723
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserModel,Integer> implements UserService{

    @Autowired
    private UserJpa userJpa;
    @Override
    protected void init() {
        jpaRepository = userJpa;
    }
}
