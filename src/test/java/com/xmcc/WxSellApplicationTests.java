package com.xmcc;

import com.xmcc.common.ResultResponse;
import com.xmcc.service.OrderDetailService;
import com.xmcc.service.ProductInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxSellApplicationTests {

	@Resource
	private OrderDetailService orderDetailService;
	@Test
	public void contextLoads() {
		String openId="oXDaO1RMGiRJACn5Bsp0nkHEqQ_w";
		String orderId="cbde94f19b3f4f73b8a5c065400837b6";
	orderDetailService.cancel(openId,orderId);

	}

}
