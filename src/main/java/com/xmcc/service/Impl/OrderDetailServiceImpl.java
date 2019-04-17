package com.xmcc.service.Impl;

import com.xmcc.dao.impl.BatchDaoImpl;
import com.xmcc.entity.OrderDetail;
import com.xmcc.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderDetailServiceImpl extends BatchDaoImpl<OrderDetail> implements OrderDetailService {
    @Override
    @Transactional//加入事务管理
    public void batchInsert(List<OrderDetail> list) {
             super.batchInsert(list);
    }
}
