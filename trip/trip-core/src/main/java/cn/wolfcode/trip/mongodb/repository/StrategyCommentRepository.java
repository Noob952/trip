package cn.wolfcode.trip.mongodb.repository;

import cn.wolfcode.trip.mongodb.domain.StrategyComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyCommentRepository extends MongoRepository<StrategyComment, String> {
 Page<StrategyComment> findByStrategyId(Long strategyId, Pageable pageable);
}
