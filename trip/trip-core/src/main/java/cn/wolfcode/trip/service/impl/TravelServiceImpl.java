package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.domain.Travel;
import cn.wolfcode.trip.domain.TravelContent;
import cn.wolfcode.trip.exception.LogicException;
import cn.wolfcode.trip.mapper.TravelContentMapper;
import cn.wolfcode.trip.mapper.TravelMapper;
import cn.wolfcode.trip.query.TravelQuery;
import cn.wolfcode.trip.service.ITravelService;
import cn.wolfcode.trip.service.IUserInfoService;
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
public class TravelServiceImpl extends ServiceImpl<TravelMapper, Travel> implements ITravelService {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private TravelContentMapper travelContentMapper;
    @Override
    public Page<Travel> queryPage(TravelQuery qo) {
        QueryWrapper<Travel> queryWrapper = new QueryWrapper<>();
        Page<Travel> page = new Page(qo.getCurrentPage(), qo.getPageSize());
        page = super.page(page, queryWrapper);
        List<Travel> records=page.getRecords();
        for (Travel record : records) {
            if (record.getAuthorId()!=null){
                record.setAuthor(userInfoService.getById(record.getAuthorId()));
            }
        }
        return page;
    }

    @Override
    public TravelContent getContentById(Long id) {
       return travelContentMapper.selectById(id);
    }

    @Override
    public void audit(Long id, Integer state) {
        Travel travel=super.getById(id);
        //1 判断能否被审核
        if (travel.getState()==null||travel.getState()!=Travel.STATE_WAITING){
            throw new LogicException("当前单据状态不合法");
        }
        //2 记录相关信息：审核人 最后修改时间 状态修改 发布时间
        travel.setAuthorId(1L);
        travel.setLastUpdateTime(new Date());
        travel.setReleaseTime(new Date());
        travel.setState(state);
        //3 生成流水记录
        //4 继承消息通知
        super.saveOrUpdate(travel);
    }
}
