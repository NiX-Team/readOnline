package com.kiss.service.base;

import java.util.List;

public interface BaseService<M extends Object> {
    void save(M m);
    List<M> findAll();
}
