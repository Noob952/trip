package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.domain.UserInfo;
import cn.wolfcode.trip.exception.LogicException;
import cn.wolfcode.trip.mapper.UserInfoMapper;
import cn.wolfcode.trip.redis.IUserInfoRedisService;
import cn.wolfcode.trip.service.IUserInfoService;
import cn.wolfcode.trip.util.Consts;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private IUserInfoRedisService iUserInfoRedisService;

    @Value("${sms.url}")
    private String msgUrl;

    @Value("${sms.appkey}")
    private String appkey;

    @Override
    public boolean checkPhone(String phone) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        UserInfo userInfo = super.getOne(queryWrapper);
        return userInfo != null;

    }

    @Override
    public void sendVerifyCode(String phone) {
        //1 生成一个验证码
        String code = UUID.randomUUID().toString().replaceAll("-", "")
                .substring(0, 4);
        //2 把验证码存储到redis中，key使用phone，value使用生成的验证码，设置有效时间五分钟
        iUserInfoRedisService.setVerifyCode(phone, code);

        //3 发送短信到指定手机
        StringBuilder sb = new StringBuilder(80);
        String content = sb.append("您本次的验证码是：").append(code).append(",请在"+ Consts.VERIFY_CODE_VAI_TIME+"分钟内使用").toString();
        System.out.println("content = " + content);
        // Url:https://way.jd.com/chuangxin/dxjk?
        // mobile=13568813957&content=【创信】你的验证码是：5873，3分钟内有效！&appkey=3a9dc6970aff5579692bb406207cd5e1
        RestTemplate template = new RestTemplate();
        String url = msgUrl + "?sign="+"【创瑞云】"+"&mobile=" + phone + "&content=" + content + "&appkey=" + appkey;
        String result=template.getForObject(url,String.class);
        System.out.println("result = " + result);
        if (!result.contains("SUCCESS")){
            throw new LogicException("验证码发送失败");
        }


    }

    @Override
    public void regist(String phone, String nickname, String password) {
        //1 创建一个UserInfo对象
        UserInfo userInfo = new UserInfo();
        //2 设置传递过来的值
        userInfo.setPhone(phone);
        userInfo.setNickname(nickname);
        userInfo.setPassword(password);
        //3 设置默认值
        userInfo.setGender(UserInfo.GENDER_SECRET);
        userInfo.setState(UserInfo.STATE_NORMAL);
        userInfo.setLevel(0);
        //4 保存操作
        super.saveOrUpdate(userInfo);
    }

    @Override
    public UserInfo login(String username, String password) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("phone",username).eq("password",password);
        UserInfo userInfo = super.getOne(wrapper);
        if (userInfo==null){
            throw new LogicException("用户名或密码错误");
        }
        return userInfo;
    }
}
