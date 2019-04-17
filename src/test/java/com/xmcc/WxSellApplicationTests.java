package com.xmcc;

import com.xmcc.common.ResultResponse;
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
	private ProductInfoService productInfoService;
	@Test
	public void contextLoads() {
		System.out.println(productInfoService.queryList());

	}

}
