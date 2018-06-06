package org.activiti.cloud.connectors.ranking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.activiti.cloud.connectors.ranking.model.RankedAuthor;
import org.activiti.cloud.connectors.ranking.services.RankingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.marker.Markers.append;

@RestController
public class RankingController {

    private Logger logger = LoggerFactory.getLogger(RankingController.class);

    private final RankingService rankingService;
    @Value("${spring.application.name}")
    private String appName;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String welcome() {
        return "Welcome to the Campaign Engagement Ranking Service";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rank")
    public List<String> getTopics() {
        return new ArrayList<>(rankingService.getRanking().keySet());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rank/{topic}/positive")
    public List<RankedAuthor> getPossitiveRanking(@PathVariable("topic") String topic) {
        logger.info(append("service-name",
                           appName),
                    ">>> Getting Possitive Ranked Authors for Campaign: " + topic);
        return rankingService.getRanking(topic + "-positive");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rank/{topic}/negative")
    public List<RankedAuthor> getNegativeRanking(@PathVariable("topic") String topic) {
        logger.info(append("service-name",
                           appName),
                    ">>> Getting Possitive Ranked Authors for Campaign: " + topic);
        return rankingService.getRanking(topic + "-negative");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rank/{topic}/neutral")
    public List<RankedAuthor> getNeutralRanking(@PathVariable("topic") String topic) {
        logger.info(append("service-name",
                           appName),
                    ">>> Getting Possitive Ranked Authors for Campaign: " + topic);
        return rankingService.getRanking(topic + "-neutral");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/rank/{topic}/{attitude}/{author}")
    public void rank(@PathVariable("topic") String topic,
                     @PathVariable("attitude") String attitude,
                     @PathVariable("author") String author) {
        logger.info(append("service-name",
                           appName),
                    ">>> Manual Ranking Author: " + author + " for Campaign: " + topic + "-" + attitude);
        rankingService.rank(topic + "-" + attitude,
                            author);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/rank/{topic}/{attitude}")
    public void cleanupRankingForTopic(@PathVariable("topic") String topic,
                                       @PathVariable("attitude") String attitude) {
        rankingService.cleanupRankingForTopic(topic + "-" + attitude);
    }
}

