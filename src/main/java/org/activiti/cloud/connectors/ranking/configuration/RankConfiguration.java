package org.activiti.cloud.connectors.ranking.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rank")
public class RankConfiguration {

	private int top = 3;

	public RankConfiguration() {
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}
}
