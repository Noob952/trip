package cn.wolfcode.trip.service;


import cn.wolfcode.trip.domain.StrategyTheme;
import cn.wolfcode.trip.query.StrategyThemeQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 攻略主题服务接口
 */
public interface IStrategyThemeService extends IService<StrategyTheme>{
    /**
     * 分页
     * @param qo
     * @return
     */
    IPage<StrategyTheme> queryPage(StrategyThemeQuery qo);
}
