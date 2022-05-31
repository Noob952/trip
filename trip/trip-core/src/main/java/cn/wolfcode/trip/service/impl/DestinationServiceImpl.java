package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.domain.Destination;
import cn.wolfcode.trip.domain.Region;
import cn.wolfcode.trip.domain.Strategy;
import cn.wolfcode.trip.domain.StrategyCatalog;
import cn.wolfcode.trip.mapper.DestinationMapper;
import cn.wolfcode.trip.query.DestinationQuery;
import cn.wolfcode.trip.service.IDestinationService;
import cn.wolfcode.trip.service.IRegionService;
import cn.wolfcode.trip.service.IStrategyCatalogService;
import cn.wolfcode.trip.service.IStrategyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class DestinationServiceImpl extends ServiceImpl<DestinationMapper, Destination> implements IDestinationService {
   @Autowired
   private IRegionService regionService;
@Autowired
private IStrategyCatalogService catalogService;
@Autowired
private IStrategyService strategyService;
    @Override
    public List<Destination> getDestByRegionId(Long rid) {
        Region region=regionService.getById(rid);
        List<Long> ids=region.parseRefIds();
        return super.listByIds(ids);
    }
    @Override
    public Page<Destination> page(DestinationQuery qo) {
        QueryWrapper<Destination> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(qo.getKeyword()),"name",qo.getKeyword());
       if (qo.getParentId()!=null&&qo.getParentId()>0){
           queryWrapper.eq("parent_id",qo.getParentId());
       }
        Page<Destination> page=new Page(qo.getCurrentPage(),qo.getPageSize());
        return super.page(page,queryWrapper);
    }

    @Override
    public List<Destination> queryToasts(Long parentId) {
        List<Destination> toasts=new ArrayList<>();
        while (parentId!=null){
            Destination dest=super.getById(parentId);
            toasts.add(dest);
            parentId=dest.getParentId();
        }
        //翻转对应的顺序
        Collections.reverse(toasts);
        return toasts;
    }

    @Override
    public List<Destination> search(Long regionId) {
        List<Destination> dests = null;
        if (regionId==-1){
            QueryWrapper<Destination> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id",1);
            dests=super.list(queryWrapper);
        }else {
            dests=getDestByRegionId(regionId);
        }
        for (Destination dest : dests) {
            QueryWrapper<Destination> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id",dest.getId());
            List<Destination> children = super.list(queryWrapper);
            dest.setChildren(children);
        }
        return dests;
    }

    @Override
    public List<StrategyCatalog> queryCatalogs(Long destId) {
        QueryWrapper<StrategyCatalog> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("dest_id",destId);
        List<StrategyCatalog> catalogs=catalogService.list(queryWrapper);
        for (StrategyCatalog catalog:catalogs){
            QueryWrapper<Strategy> wrapper=new QueryWrapper<>();
            wrapper.eq("catalog_id",catalog.getId());
            List<Strategy> strategies=strategyService.list(wrapper);
            catalog.setStrategies(strategies);
        }
        return catalogs;
    }
}
