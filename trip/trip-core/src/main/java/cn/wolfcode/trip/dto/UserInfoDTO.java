package cn.wolfcode.trip.dto;

import cn.wolfcode.trip.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("userinfo")
public class UserInfoDTO{



    private String nickname;  //昵称
    private String phone;  //手机
    private String password; //密码
    private String rpassword; //重复密码
    private String verifyCode; //验证码



}
