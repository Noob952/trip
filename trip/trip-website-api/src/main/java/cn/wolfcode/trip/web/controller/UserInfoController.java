package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.domain.UserInfo;
import cn.wolfcode.trip.exception.LogicException;
import cn.wolfcode.trip.redis.IUserInfoRedisService;
import cn.wolfcode.trip.service.IUserInfoService;
import cn.wolfcode.trip.util.AssertUtil;
import cn.wolfcode.trip.util.JsonResult;
import cn.wolfcode.trip.web.annotation.RequireLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @GetMapping("list")
    public List<UserInfo> list() {
        final List<UserInfo> list = userInfoService.list();
        return list;
    }

    @GetMapping("checkPhone")
    public boolean checkPhone(String phone) {
        boolean flag = userInfoService.checkPhone(phone);
        return flag;
    }

    @GetMapping("sendVerifyCode")
    public Object sendVerifyCode(String phone) {
        userInfoService.sendVerifyCode(phone);
        return JsonResult.success();
    }

    @PostMapping("regist")
    public Object regist(String phone, String nickname, String password,
                         String rpassword, String verifyCode) {
        //1 完成基本的非空判断
        AssertUtil.hasLength(phone, "手机号不能为空");
        AssertUtil.hasLength(nickname, "昵称不能为空");
        AssertUtil.hasLength(password, "密码不能为空");
        AssertUtil.hasLength(rpassword, "重复密码不能为空");
        AssertUtil.hasLength(verifyCode, "验证码不能为空");
        //2 判断密码是否一致
        AssertUtil.isEquals(password, rpassword, "密码不一致");
        //3 判断验证码是否正确
        //3.1 先获取到验证码
        String code = userInfoRedisService.getVerifyCode(phone);
        AssertUtil.isEquals(code, verifyCode, "验证码信息不正确");
        //4 判断手机号是否唯一
        boolean flag = userInfoService.checkPhone(phone);
        if (flag) {
            throw new LogicException("手机号已注册");
        }
        //5 保存用户信息
        userInfoService.regist(phone, nickname, password);

        return JsonResult.success();
    }

    @PostMapping("login")
    public JsonResult login(String username, String password) {
        //调用该service方法完成登录
        UserInfo userInfo = userInfoService.login(username, password);
        //调用redis的方法，缓存用户信息，返回对应的token
        String token = userInfoRedisService.setUserInfo(userInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", userInfo);
        return JsonResult.success(map);
    }

    @RequireLogin //在需要控制的方法贴上注解
    @GetMapping("getCurrentUser")
    public JsonResult getCurrentUser(HttpServletRequest request) {
        System.out.println("UserInfoController.getCurentUser");
        String token = request.getHeader("token");
        UserInfo userInfo = userInfoRedisService.getUserByToken(token);
        return JsonResult.success(userInfo);
    }
}
