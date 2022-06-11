package cn.wolfcode.trip.redis.impl;

import cn.wolfcode.trip.domain.Strategy;
import cn.wolfcode.trip.redis.IStrategyStatisticService;
import cn.wolfcode.trip.service.IStrategyService;
import cn.wolfcode.trip.util.RedisKeys;
import cn.wolfcode.trip.vo.StrategyStaticsVO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StrategyStatisticServiceImpl implements IStrategyStatisticService {

    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private IStrategyService strategyService;

    public void increViewnum(Long strategyId) {

        //判断key是否存在
        StrategyStaticsVO staticsVO = getStatisVO(strategyId);
        staticsVO.setViewnum(staticsVO.getViewnum() + 1);
        setStatisVO(staticsVO);
    }

    @Override
    public StrategyStaticsVO statisVO(Long sid) {
            return getStatisVO(sid);
    }

    @Override
    public void incrReplynum(Long  id) {
        StrategyStaticsVO staticsVO=getStatisVO(id);
        //添加回复数量
        staticsVO.setReplynum(staticsVO.getReplynum()+1);
        setStatisVO(staticsVO);
    }

    @Override
    public StrategyStaticsVO getStatisVO(Long sid) {
        //1 判断id是否为空
        String key = RedisKeys.STRATEGY_STATIS_VO.join(sid.toString());
        //判断key是否存在
        StrategyStaticsVO staticsVO = null;
        if (!template.hasKey(key)) {
            //如果不存在，查询mysql的攻略数据，并且封装为vo对象
            Strategy strategy = strategyService.getById(sid);
            staticsVO = new StrategyStaticsVO();
            BeanUtils.copyProperties(strategy, staticsVO);
            staticsVO.setStrategyId(sid);
            //缓存对应的vo对象到redis中
            template.opsForValue().set(key, JSON.toJSONString(staticsVO));
        } else {
            String jsonString = template.opsForValue().get(key);
            staticsVO = JSON.parseObject(jsonString, StrategyStaticsVO.class);
        }
        //2 判断redis中是否有对应的key
        //3 没有则去mysql
        return staticsVO;
    }

    @Override
    public void setStatisVO(StrategyStaticsVO statisVO) {
        String key=RedisKeys.STRATEGY_STATIS_VO.join(statisVO.getStrategyId().toString());
        template.opsForValue().set(key,JSON.toJSONString(statisVO));
    }
}
