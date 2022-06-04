package cn.wolfcode.trip.web.resolve;

import cn.wolfcode.trip.domain.UserInfo;
import cn.wolfcode.trip.redis.IUserInfoRedisService;
import cn.wolfcode.trip.web.annotation.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

//@Component
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //1 参数类型必须是userInfo类型
        //2 参数上必须要有@UserParam注解
        if (UserInfo.class == parameter.getParameterType() && parameter.hasParameterAnnotation(UserParam.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("token");
        UserInfo userInfo = userInfoRedisService.getUserByToken(token);
        return userInfo;
    }
}
