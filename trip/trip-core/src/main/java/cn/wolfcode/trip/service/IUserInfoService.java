package cn.wolfcode.trip.service;

import cn.wolfcode.trip.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 检查手机号唯一性
     * @param phone 手机号
     * @return true ：手机号存在；false：手机号不存在
     */
    boolean checkPhone(String phone);

    /**
     * 发送短信验证码
     * @param phone 接收验证码的手机号
     */
    void sendVerifyCode(String phone);

    /**
     * 用户注册
     * @param phone 手机号码
     * @param nickname 昵称
     * @param password 密码
     */
    void regist(String phone, String nickname, String password);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 返回用户信息，如果登录不成功，直接抛出异常
     */
    UserInfo login(String username, String password);
}
