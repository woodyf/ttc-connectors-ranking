/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.connectors.ranking.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.activiti.cloud.connectors.ranking.model.RankedAuthor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static net.logstash.logback.marker.Markers.append;

@Service
public class RankingService {

    @Value("${spring.application.name}")
    private String appName;

    private Logger logger = LoggerFactory.getLogger(RankingService.class);

    private Map<String, List<RankedAuthor>> ranking = new ConcurrentHashMap<>();

    public List<RankedAuthor> rank(String topic,
                                   String username) {
        List<RankedAuthor> rankedUsers = getCurrentRankedUsers(topic);
        Optional<RankedAuthor> researchedUser = rankedUsers.stream()
                .filter(user -> user.getUserName().equals(username))
                .findFirst();

        if (researchedUser.isPresent()) {
            researchedUser.get().incrementTweets();
            rankedUsers.sort(
                    (o1, o2) ->
                            o2.getNroOfTweets() - o1.getNroOfTweets());
        } else {
            rankedUsers.add(new RankedAuthor(username));
        }
        return ranking.get(topic);
    }

    private List<RankedAuthor> getCurrentRankedUsers(String topic) {
        if (!ranking.containsKey(topic)) {
            ranking.put(topic,
                        new CopyOnWriteArrayList<>());
        }
        return ranking.get(topic);
    }

    public void cleanupRankingForTopic(String topic) {
        ranking.remove(topic);
    }

    public List<RankedAuthor> getRanking(String topic, int top) {
        return Collections.unmodifiableList(getCurrentRankedUsers(topic).subList(0, top));
    }

    public Map<String, List<RankedAuthor>> getRanking() {
        return Collections.unmodifiableMap(ranking);
    }

    public List<RankedAuthor> getTop(String topic,
                                     int topSize) {
        List<RankedAuthor> top = getCurrentRankedUsers(topic)
                .stream()
                .limit(topSize)
                .collect(Collectors.toList());
        return Collections.unmodifiableList(top);
    }

    @Scheduled(fixedRate = 60000)
    public void logCurrentRankingsForAllCampaigns() {
        logger.info(append("service-name",
                           appName),
                    ">>> Scheduled Printing (local) Ranking: ");
        if (getRanking().keySet().isEmpty()) {
            logger.info("No ranking set");
        }
        for (String key : getRanking().keySet()) {
            logger.info("Campaign being ranked is (hardcoded top 3) " + key);
            for (RankedAuthor ru : getRanking(key, 3)) {
                logger.info("Ranked User: " + ru);
            }
        }
    }
}
