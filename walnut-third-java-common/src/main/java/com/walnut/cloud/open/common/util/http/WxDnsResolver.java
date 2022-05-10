package com.walnut.cloud.open.common.util.http;

import org.apache.http.conn.DnsResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class WxDnsResolver implements DnsResolver {

  private static final String WECHAT_API_URL = "api.weixin.qq.com";
  private static Map<String, InetAddress[]> MAPPINGS = new HashMap<>();
  protected final Logger log = LoggerFactory.getLogger(WxDnsResolver.class);
  private String wxApiIp;

  public WxDnsResolver(String ip) {

    this.wxApiIp = ip;
    this.init();
  }

  private void init() {
    if (log.isDebugEnabled()) {
      log.debug("init wechat dns config with ip {}", wxApiIp);
    }
    try {
      MAPPINGS.put(WECHAT_API_URL, new InetAddress[]{InetAddress.getByName(wxApiIp)});
    } catch (UnknownHostException e) {
      //如果初始化DNS配置失败则使用默认配置,不影响服务的启动
      log.error("init WxDnsResolver error", e);
      MAPPINGS = new HashMap<>();
    }

  }

  @Override
  public InetAddress[] resolve(String host) {


    return MAPPINGS.containsKey(host) ? MAPPINGS.get(host) : new InetAddress[0];
  }

  public String getWxApiIp() {
    return wxApiIp;
  }

  public void setWxApiIp(String wxApiIp) {
    this.wxApiIp = wxApiIp;
    this.init();
  }
}
