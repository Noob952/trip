package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.domain.Region;
import cn.wolfcode.trip.mapper.RegionMapper;
import cn.wolfcode.trip.query.RegionQuery;
import cn.wolfcode.trip.service.IRegionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {
    @Override
    public Page<Region> page(RegionQuery qo) {
       QueryWrapper<Region> wrapper=  new QueryWrapper<>();
      Page<Region> page= new Page<>(qo.getCurrentPage(),qo.getPageSize());
        return super.page(page,wrapper);
    }

    @Override
    public void changeHotValue(Integer hot, Long id) {
        final UpdateWrapper<Region> wrapper=new UpdateWrapper<>();
        wrapper.set("ishot",hot).eq("id",id);
        super.update(wrapper);
    }

    @Override
    public List<Region> hotRegion() {
       QueryWrapper<Region> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq("ishot",1);
        return super.list(queryWrapper);
    }
}
