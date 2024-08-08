package org.example.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class IpUtils {

  public static String getClientIp(HttpServletRequest request) {
    String clientIp = null;
    boolean isIpInHeader = false;

    List<String> headerList = new ArrayList<>();
    headerList.add("X-Forwarded-For");
    headerList.add("HTTP_CLIENT_IP");
    headerList.add("HTTP_X_FORWARDED_FOR");
    headerList.add("HTTP_X_FORWARDED");
    headerList.add("HTTP_FORWARDED_FOR");
    headerList.add("HTTP_FORWARDED");
    headerList.add("Proxy-Client-IP");
    headerList.add("WL-Proxy-Client-IP");
    headerList.add("HTTP_VIA");
    headerList.add("IPV6_ADR");

    for (String header : headerList) {
      clientIp = request.getHeader(header);
      if (StringUtils.hasText(clientIp) && !clientIp.equalsIgnoreCase("unknown")) {
        isIpInHeader = true;
        break;
      }
    }

    return (isIpInHeader) ? clientIp : request.getRemoteAddr();
  }
}
