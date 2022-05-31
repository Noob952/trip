package cn.wolfcode.trip.redis.impl;

import cn.wolfcode.trip.domain.UserInfo;
import cn.wolfcode.trip.redis.IUserInfoRedisService;
import cn.wolfcode.trip.util.RedisKeys;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoRedisServiceImpl implements IUserInfoRedisService {

    @Autowired
    private StringRedisTemplate template;

    @Override
    public void setVerifyCode(String phone, String code) {
        String key = RedisKeys.VERIFY_CODE.join(phone);//唯一性，可读性
        template.opsForValue().set(key, code, 300, TimeUnit.SECONDS);
    }

    @Override
    public String getVerifyCode(String phone) {
        String key = RedisKeys.VERIFY_CODE.join(phone);
        return template.opsForValue().get(key);

    }

    @Override
    public String setUserInfo(UserInfo userInfo) {
        //1 通过UUID创建token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //2 创建对应的key
        String key = RedisKeys.LOGIN_TOKEN.join(token);
        //3 设置对应的value
        String userInfoStr = JSON.toJSONString(userInfo);
        //4 存储到redis中
        template.opsForValue().set(key, userInfoStr, RedisKeys.LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
        return token;
    }

    @Override
    public UserInfo getUserByToken(String token) {
        String key = RedisKeys.LOGIN_TOKEN.join(token);
        if (template.hasKey(key)){
            //延长时间
            template.expire(key,RedisKeys.LOGIN_TOKEN.getTime(),TimeUnit.SECONDS);
            String userInfoStr=template.opsForValue().get(token);
            if (StringUtils.hasLength(userInfoStr)){
                UserInfo userInfo=JSON.parseObject(userInfoStr,UserInfo.class);
            }
        }
        return null;
    }
}
