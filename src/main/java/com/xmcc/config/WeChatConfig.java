package com.xmcc.config;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class WeChatConfig {
    @Autowired
    private WeiXinProperits weiXinProperits;
    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService =new WxMpServiceImpl();
        //设置微信配置的存储
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        //设置微信配置
        log.info("appid:{}",weiXinProperits.getAppid());
        wxMpInMemoryConfigStorage.setAppId("wxcec0b9e65c084712");
        wxMpInMemoryConfigStorage.setSecret("05a7e861c1985ced86af77fb8f7163bc");
        return wxMpConfigStorage();
    }



}
