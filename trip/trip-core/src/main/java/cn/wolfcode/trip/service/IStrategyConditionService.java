package cn.wolfcode.trip.service;

import cn.wolfcode.trip.domain.StrategyCondition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IStrategyConditionService extends IService<StrategyCondition> {
    /**
     * 同步攻略查询数据
     * @param type 1 国外，2 国内，3 主题
     */
    void syncConditionData(int type);

    /**
     * 根据类型查询攻略条件
     * @param type
     * @return
     */
    List<StrategyCondition> queryCondition(Integer type);
}
