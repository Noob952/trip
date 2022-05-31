package cn.wolfcode.trip.redis;

import cn.wolfcode.trip.domain.UserInfo;

public interface IUserInfoRedisService {
    /**
     * 存储手机验证码到redis中
     * @param phone 手机号码
     * @param code  验证码
     */
    void setVerifyCode(String phone, String code);

    /**
     * 根据手机号码，获取对应的验证码信息
     * @param phone 手机号码
     * @return 验证码
     */
    String getVerifyCode(String phone);

    /**
     * 存储用户信息到redis中
     * @param userInfo 用户信息
     * @return 返回存储的token信息
     */
    String setUserInfo(UserInfo userInfo);

    UserInfo getUserByToken(String token);
}
