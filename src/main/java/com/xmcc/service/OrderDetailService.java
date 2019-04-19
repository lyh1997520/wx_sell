package com.xmcc.service;

import com.xmcc.common.ResultResponse;
import com.xmcc.entity.OrderDetail;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDetailService {

    //批量插入
   void batchInsert(List<OrderDetail> list);

ResultResponse detail(String openId,String orderId);
ResultResponse cancel(String openId,String orderId);
ResultResponse list(Pageable pageable,String openId);
}
