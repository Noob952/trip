package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.domain.*;
import cn.wolfcode.trip.mapper.StrategyContentMapper;
import cn.wolfcode.trip.mapper.StrategyMapper;
import cn.wolfcode.trip.query.StrategyQuery;
import cn.wolfcode.trip.service.IDestinationService;
import cn.wolfcode.trip.service.IStrategyCatalogService;
import cn.wolfcode.trip.service.IStrategyService;
import cn.wolfcode.trip.service.IStrategyThemeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StrategyServiceImpl extends ServiceImpl<StrategyMapper, Strategy> implements IStrategyService {

    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @Autowired
    private StrategyContentMapper strategyContentMapper;

    @Autowired
    private IDestinationService destinationService;

    @Autowired
    private IStrategyService strategyService;

    @Override
    public Page<Strategy> page(StrategyQuery qo) {
        QueryWrapper<Strategy> queryWrapper = new QueryWrapper<>();
        Page<Strategy> page = new Page<Strategy>(qo.getCurrentPage(), qo.getPageSize());
        return super.page(page, queryWrapper);
    }

    public boolean saveOrUpdate(Strategy strategy) {
        boolean flag = false;
        StrategyCatalog catalog = strategyCatalogService.getById(strategy.getCatalogId());
        strategy.setCatalogName(catalog.getName());
        strategy.setDestId(catalog.getDestId());
        strategy.setDestName(catalog.getDestName());
        StrategyTheme theme = strategyThemeService.getById(strategy.getThemeId());
        strategy.setThemeName(theme.getName());
        // 判断是国内否
        List<Destination> toasts = destinationService.queryToasts(catalog.getDestId());
        if (toasts != null && toasts.size() > 0) {
            if ("中国".equals(toasts.get(0).getName())) {
                //国内
                strategy.setIsabroad(Strategy.ABROAD_NO);
            } else {
                strategy.setIsabroad(Strategy.ABROAD_YES);
            }
        }

        if (strategy.getId() == null) {
            strategy.setViewnum(0);
            strategy.setReplynum(0);
            strategy.setFavornum(0);
            strategy.setSharenum(0);
            strategy.setThumbsupnum(0);
            strategy.setCreateTime(new Date());
            flag = super.save(strategy);
            //先保存攻略-->攻略内容
            StrategyContent content = strategy.getContent();
            content.setId(strategy.getId());
            strategyContentMapper.insert(content);
        } else {//修改操作
            StrategyContent content = strategy.getContent();
            content.setId(strategy.getId());
            strategyContentMapper.updateById(content);
        }
        return false;

    }

    @Override
    public StrategyContent getContent(Long id) {
        return strategyContentMapper.selectById(id);
    }

    @Override
    public List<Strategy> queryViewnumTop3(Long destId) {
        QueryWrapper<Strategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dest_id", destId)
                .orderByDesc("viewnum")
                .last("limit 3"); //sql语句后面加上的sql
        return strategyService.list(queryWrapper);

    }

    @Override
    public Page<Strategy> queryPage(StrategyQuery qo) {
        QueryWrapper<Strategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(qo.getDestId() != null, "dest_id", qo.getDestId());
        queryWrapper.eq(qo.getThemeId() != null, "theme_id", qo.getThemeId());
        if (qo.getType()!=null&&(qo.getType()==1||qo.getType()==2)){
            queryWrapper.eq("dest_id",qo.getRefid());
        }else if(qo.getType()!=null&&qo.getType()==3){
            queryWrapper.eq("theme_id",qo.getRefid());
        }
        queryWrapper.orderByDesc(qo.getOderBy());

        Page<Strategy> page=new Page(qo.getCurrentPage(),qo.getPageSize());
        return super.page(page,queryWrapper);

    }
}
