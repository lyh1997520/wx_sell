package com.xmcc.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeiChatController {
    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl) throws UnsupportedEncodingException {
        String url = "http://xmccjyqs.natapp1.cc/sell/wechat/getUserInfo";
    //构造微信授权的url
        //oauth2buildAuthorizationUrl()传递的三个参数：
        //参数一：授权码
        //参数二：授权策略 scope
        //参数三：自己携带的数据
       String redirectUrl= wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl,"UTF-8"));
        log.info("redirectUrl:{}",redirectUrl);
       return "redirect:"+ redirectUrl;
    }
@RequestMapping("/getUserInfo")
  public String getUserInfo(@RequestParam("code") String code,@RequestParam("state") String returnUrl) throws UnsupportedEncodingException {
       //根据SDK文档获取令牌
      WxMpOAuth2AccessToken wxMpOAuth2AccessToken=null;
      WxMpUser wxMpUser =null;
      try {
           wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
      } catch (WxErrorException e) {
          e.printStackTrace();
      }
       //获取用户信息
      try {
           wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
          log.info("微信昵称：{}",wxMpUser.getNickname());
      } catch (WxErrorException e) {
          e.printStackTrace();
      }
      String openId = wxMpUser.getOpenId();
      log.info("微信openID:{}",openId);

      return "redirect:"+ URLDecoder.decode(returnUrl,"UTF-8")+"?openid="+openId;
  }

  @RequestMapping("/testOpenid")
    public void testOpenid(@RequestParam("openid")String openid){
         log.info("用户获得的openid:{}",openid);
  }
}
