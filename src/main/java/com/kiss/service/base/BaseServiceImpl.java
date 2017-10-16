package com.kiss.service.base;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class BaseServiceImpl implements BaseService{
    protected JpaRepository jpaRepository;

    @PostConstruct
    protected abstract void init();

    @Override
    public void save(Object o) {
        jpaRepository.save(o);
    }

    @Override
    public List findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Object findById(Integer id) {
        return jpaRepository.findOne(id);
    }

    @Override
    public void delete(Object o) {
        jpaRepository.delete(o);
    }

    @Override
    public void update(Object o) {
        jpaRepository.saveAndFlush(o);
    }
}
