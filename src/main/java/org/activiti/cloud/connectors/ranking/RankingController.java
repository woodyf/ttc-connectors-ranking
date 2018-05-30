package org.activiti.cloud.connectors.ranking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankingController {

    private Logger logger = LoggerFactory.getLogger(RankingController.class);

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rank/{topic}/possitive")
    public List<RankedAuthor> getPossitiveRanking(@PathVariable("topic") String topic) {
        logger.info(">>> Getting Possitive Ranked Authors for Campaign: " + topic);
        return rankingService.getRanking(topic + "-possitive");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rank/{topic}/negative")
    public List<RankedAuthor> getNegativeRanking(@PathVariable("topic") String topic) {
        logger.info(">>> Getting Possitive Ranked Authors for Campaign: " + topic);
        return rankingService.getRanking(topic + "-negative");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rank/{topic}/neutral")
    public List<RankedAuthor> getNeutralRanking(@PathVariable("topic") String topic) {
        logger.info(">>> Getting Possitive Ranked Authors for Campaign: " + topic);
        return rankingService.getRanking(topic + "-neutral");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/rank/{topic}/{attitude}/{author}")
    public void rank(@PathVariable("topic") String topic,
                       @PathVariable("attitude") String attitude,
                       @PathVariable("author") String author) {
        logger.info(">>> Manual Ranking Author: " + author + " for Campaign: " + topic + "-" + attitude);
        rankingService.rank(topic + "-" + attitude,
                            author);
    }
}

