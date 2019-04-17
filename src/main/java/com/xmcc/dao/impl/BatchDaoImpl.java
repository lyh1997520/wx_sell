package com.xmcc.dao.impl;

import com.xmcc.dao.BatchDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.BaseStream;

public class BatchDaoImpl<T> implements BatchDao<T> {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public void batchInsert(List<T> list) {
       int size = list.size();
        for (int i = 0; i <size; i++) {
            em.persist(list.get(i));
            if (i % 100 == 0||i==size-1) {//每100条执行一次写入数据库操作
                em.flush();
                em.clear();
            }
        }
    }
}
