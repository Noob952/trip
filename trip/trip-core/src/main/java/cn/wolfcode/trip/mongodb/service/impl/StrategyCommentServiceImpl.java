package cn.wolfcode.trip.mongodb.service.impl;

import cn.wolfcode.trip.mongodb.domain.StrategyComment;
import cn.wolfcode.trip.mongodb.repository.StrategyCommentRepository;
import cn.wolfcode.trip.mongodb.service.IStrategyCommentService;
import cn.wolfcode.trip.query.StrategyQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StrategyCommentServiceImpl implements IStrategyCommentService {


    @Autowired
    private StrategyCommentRepository strategyCommentRepository;

    @Override
    public void save(StrategyComment strategyComment) {
        strategyCommentRepository.save(strategyComment);
    }

    @Override
    public Page<StrategyComment> queryPage(StrategyQuery strategyQuery) {
        PageRequest pageRequest = PageRequest.of(strategyQuery.getCurrentPage() - 1, strategyQuery.getPageSize());
        return strategyCommentRepository.findByStrategyId(strategyQuery.getStrategyId(), pageRequest);

    }
}
