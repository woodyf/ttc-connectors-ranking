package org.activiti.cloud.connectors.ranking;

import org.activiti.cloud.connectors.ranking.model.RankedAuthor;
import org.activiti.cloud.connectors.ranking.services.RankingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@ComponentScan({"org.activiti.cloud.connectors.starter", "org.activiti.cloud.connectors.ranking"})
@EnableScheduling
public class RankingCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(RankingCloudApplication.class,
                              args);
    }



}
