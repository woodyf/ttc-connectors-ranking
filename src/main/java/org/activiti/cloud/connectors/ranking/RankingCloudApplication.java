package org.activiti.cloud.connectors.ranking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"org.activiti.cloud.connectors.starter", "org.activiti.cloud.connectors.ranking"})
@EnableScheduling
public class RankingCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(RankingCloudApplication.class,
                              args);
    }
}
