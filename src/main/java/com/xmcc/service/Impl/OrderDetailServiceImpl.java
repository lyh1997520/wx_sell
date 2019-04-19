package com.xmcc.service.Impl;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.xmcc.common.OrderEnums;
import com.xmcc.common.ResultEnums;
import com.xmcc.common.ResultResponse;
import com.xmcc.dao.impl.BatchDaoImpl;
import com.xmcc.dto.OrderXiangQingDto;
import com.xmcc.entity.OrderDetail;
import com.xmcc.entity.OrderMaster;
import com.xmcc.entity.ProductInfo;
import com.xmcc.exception.CustomException;
import com.xmcc.repository.OrderDetailRepository;
import com.xmcc.repository.OrderMasterRepository;
import com.xmcc.repository.ProductInfoRepository;
import com.xmcc.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl extends BatchDaoImpl<OrderDetail> implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    @Transactional//加入事务管理
    public void batchInsert(List<OrderDetail> list) {
             super.batchInsert(list);
    }

    @Override
    public ResultResponse detail(String openId, String orderId) {
        OrderMaster master = orderMasterRepository.findOrderMasterByBuyerOpenidAndOrderIdIn(openId, orderId);
        System.out.println("msatee="+master);
if (master==null){
    throw new CustomException("订单不存在");
}else{
    OrderDetail detail = orderDetailRepository.findOrderDetailByOrderIdIn(orderId);
    System.out.println("detail="+detail);
    OrderXiangQingDto dto = new OrderXiangQingDto();
    List<OrderDetail> list = new ArrayList<OrderDetail>();
    list.add(detail);
    dto.setOrderDetailList(list);
    dto.setOrderId(master.getOrderId());
    dto.setBuyerName(master.getBuyerName());
    dto.setBuyerPhone(master.getBuyerPhone());
    dto.setBuyerAddress(master.getBuyerAddress());
    dto.setBuyerOpenid(master.getBuyerOpenid());
    dto.setOrderAmount(master.getOrderAmount());
    dto.setOrderStatus(master.getOrderStatus());
    dto.setPayStatus(master.getPayStatus());
    dto.setCreateTime(master.getCreateTime());
    dto.setUpdateTime(master.getUpdateTime());

    return ResultResponse.success(dto);
        }

    }

    @Override
    public ResultResponse cancel(String openId, String orderId) {
        OrderMaster master = orderMasterRepository.findOrderMasterByBuyerOpenidAndOrderIdIn(openId, orderId);

        if (master.getOrderStatus()==OrderEnums.CANCEL.getCode()){
            throw  new CustomException("订单已经取消");
        }else{
            master.setOrderStatus(OrderEnums.CANCEL.getCode());
            OrderDetail detail = orderDetailRepository.findOrderDetailByOrderIdIn(master.getOrderId());
            ProductInfo info = productInfoRepository.findProductInfoByProductNameIn(detail.getProductName());
           info.setProductStock(info.getProductStock()+detail.getProductQuantity()) ;
           productInfoRepository.save(info);
            orderMasterRepository.save(master);
        }
        return ResultResponse.success();

    }

    @Override
    public ResultResponse list(Pageable pageable,String openId) {
        List<OrderMaster> masters = orderMasterRepository.findAllByBuyerOpenidIn(pageable, openId);
        ArrayList<OrderXiangQingDto> list = new ArrayList<>();
        for (int i = 0; i < masters.size(); i++) {
            OrderXiangQingDto dto = new OrderXiangQingDto();
           OrderMaster master= masters.get(i);
            dto.setOrderId(master.getOrderId());
            dto.setBuyerName(master.getBuyerName());
            dto.setBuyerPhone(master.getBuyerPhone());
            dto.setBuyerAddress(master.getBuyerAddress());
            dto.setBuyerOpenid(master.getBuyerOpenid());
            dto.setOrderAmount(master.getOrderAmount());
            dto.setOrderStatus(master.getOrderStatus());
            dto.setPayStatus(master.getPayStatus());
            dto.setCreateTime(master.getCreateTime());
            dto.setUpdateTime(master.getUpdateTime());
            dto.setOrderDetailList(null);
            list.add(dto);
        }


        return ResultResponse.success(list);
    }

}
