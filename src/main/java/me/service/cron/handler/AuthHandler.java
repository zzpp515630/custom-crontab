package me.service.cron.handler;

import org.apache.commons.lang3.tuple.Pair;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.util.ApacheHttpClient;
import me.service.cron.util.CommandProcess;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 描述：
 * 2021/12/23 17:59.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class AuthHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean auth = auth(request);
        if (auth) {
            return true;
        }
        response.sendRedirect("/cgi-bin");
        return false;
    }

    private boolean auth(HttpServletRequest request) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            return false;
        }
        Map<String, Cookie> collect = Arrays.stream(cookies).collect(Collectors.toMap(Cookie::getName, Function.identity()));
        Cookie cookie = collect.get("NAS_SID");
        if (null == cookie) {
            return false;
        }
        String nasSid = cookie.getValue();
        if (StringUtils.isBlank(nasSid)) {
            return false;
        }

        byte[] bytes = ApacheHttpClient.httpGetByByteBuffer("http://localhost:"+port("8080")+"/cgi-bin/authLogin.cgi?sid=" + nasSid);
        String result = new String(bytes, StandardCharsets.UTF_8);
        if (!result.contains("cuid")) {
            return false;
        }
        return true;
    }

    private String port(String defaultPort){
      try{
          CommandProcess commandProcess = new CommandProcess();
          Pair<Integer, List<String>> execute = commandProcess.executeArray("netstat -nlpt |grep apache_proxy |awk  '{print $4,$7}'");
          List<String> value = execute.getValue();
          for (String s : value) {
              if(s.contains("apache_proxys")){
                  continue;
              }
              if(!s.contains("apache_proxy")){
                  continue;
              }
              return s.split(" ")[0].replace(":::","").trim();
          }
      }catch (Exception e){
      }
        return defaultPort;
    }
}
