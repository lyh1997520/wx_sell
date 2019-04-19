package com.xmcc.repository;


import com.xmcc.entity.OrderMaster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

OrderMaster findOrderMasterByBuyerOpenidAndOrderIdIn(String openId,String orderId);
List<OrderMaster> findAllByBuyerOpenidIn(Pageable pageable,String openId);
}
