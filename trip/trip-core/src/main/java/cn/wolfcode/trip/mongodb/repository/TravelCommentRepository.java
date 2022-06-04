package cn.wolfcode.trip.mongodb.repository;

import cn.wolfcode.trip.mongodb.domain.TravelComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelCommentRepository extends MongoRepository<TravelComment, String> {
    List<TravelComment> findByTravelId(Long travelId);
}
