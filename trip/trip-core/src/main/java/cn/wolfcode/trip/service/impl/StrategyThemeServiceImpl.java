package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.domain.StrategyTheme;
import cn.wolfcode.trip.mapper.StrategyThemeMapper;
import cn.wolfcode.trip.query.StrategyThemeQuery;
import cn.wolfcode.trip.service.IStrategyThemeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StrategyThemeServiceImpl extends ServiceImpl<StrategyThemeMapper, StrategyTheme> implements IStrategyThemeService {
    @Override
    public IPage<StrategyTheme> queryPage(StrategyThemeQuery qo) {
        QueryWrapper<StrategyTheme> queryWrapper=new QueryWrapper<>();
        Page<StrategyTheme> page=new Page<>(qo.getCurrentPage(),qo.getPageSize());
        return super.page(page,queryWrapper);
    }
}
