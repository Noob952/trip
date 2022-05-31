package cn.wolfcode.trip.web.interceptor;

import cn.wolfcode.trip.domain.UserInfo;
import cn.wolfcode.trip.redis.IUserInfoRedisService;
import cn.wolfcode.trip.util.JsonResult;
import cn.wolfcode.trip.web.annotation.RequireLogin;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return  true;
        }
        HandlerMethod hm= (HandlerMethod) handler;


        final   String token = request.getHeader("token");
   /*     if (!StringUtils.hasLength(token)) {
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(JsonResult.error(JsonResult.CODE_NOLOGIN, JsonResult.MSG_NOLOGIN, null)));
            return false;
        }*/

        UserInfo userInfo = userInfoRedisService.getUserByToken(token);
        if (!hm.hasMethodAnnotation(RequireLogin.class)){
            return true;
        }
        if (userInfo == null) {
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(JsonResult.error(JsonResult.CODE_NOLOGIN, JsonResult.MSG_NOLOGIN, null)));
            return false;
        }
        return true;
    }
}
