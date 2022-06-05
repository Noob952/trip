package cn.wolfcode.trip.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StrategyQuery extends QueryObject {
    private Long themeId;
    private Long destId;
    private Long strategyId;
    private String oderBy = "viewnum";
    private Integer type = null;
    private Long refid = null;
}
