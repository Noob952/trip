package cn.wolfcode.trip.mongodb.repository;

import cn.wolfcode.trip.mongodb.domain.StrategyComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyCommentRepository extends MongoRepository<StrategyComment, String> {
}
