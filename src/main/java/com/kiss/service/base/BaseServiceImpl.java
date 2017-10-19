package com.kiss.service.base;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @author 11723
 */
public abstract class BaseServiceImpl<M extends Object,ID extends Serializable> implements BaseService<M,ID>{
    protected JpaRepository<M,ID> jpaRepository;

    /**
     * 初始化
     * */
    @PostConstruct
    protected abstract void init();

    @Override
    public void save(M o) {
        jpaRepository.save(o);
    }

    @Override
    public void delete(M o) {
        jpaRepository.delete(o);
    }

    @Override
    public void update(M o) {
        jpaRepository.saveAndFlush(o);
    }

    @Override
    public M findById(ID id) {
        return jpaRepository.findOne(id);
    }

    @Override
    public List findAll() {
        return null;
    }
}
