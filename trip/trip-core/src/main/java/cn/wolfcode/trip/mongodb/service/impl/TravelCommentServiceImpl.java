package cn.wolfcode.trip.mongodb.service.impl;

import cn.wolfcode.trip.mongodb.domain.TravelComment;
import cn.wolfcode.trip.mongodb.repository.TravelCommentRepository;
import cn.wolfcode.trip.mongodb.service.ITravelCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelCommentServiceImpl implements ITravelCommentService {

    @Autowired
    private TravelCommentRepository travelCommentRepository;

    @Override
    public void save(TravelComment comment) {
        travelCommentRepository.save(comment);
    }

    @Override
    public TravelComment get(String id) {
        Optional<TravelComment> op = travelCommentRepository.findById(id);
        return op.get();
    }

    @Override
    public List<TravelComment> findByTravelId(Long travelId) {
        return travelCommentRepository.findByTravelId(travelId);

    }
}
