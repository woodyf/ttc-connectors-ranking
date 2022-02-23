package org.activiti.cloud.connectors.ranking;

import org.activiti.cloud.connectors.starter.configuration.EnableActivitiCloudConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableActivitiCloudConnector
@EnableScheduling
public class RankingCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(RankingCloudApplication.class, args);
	}
}
