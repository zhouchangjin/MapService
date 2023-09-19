package com.iwhere.pathfinding.config;

import com.iwhere.pathfinding.util.GraphHopperInitUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.graphhopper.GraphHopper;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties(prefix = "graphhopper")
public class GraphhopperConfig {
	
	String cacheFolder;

	String customCacheFolder;

	boolean enableElevator;

	String demFile;

	public String getCustomCacheFolder() {
		return customCacheFolder;
	}

	public void setCustomCacheFolder(String customCacheFolder) {
		this.customCacheFolder = customCacheFolder;
	}

	public boolean isEnableElevator() {
		return enableElevator;
	}

	public void setEnableElevator(boolean enableElevator) {
		this.enableElevator = enableElevator;
	}

	public String getDemFile() {
		return demFile;
	}

	public void setDemFile(String demFile) {
		this.demFile = demFile;
	}

	public String getCacheFolder() {
		return cacheFolder;
	}

	public void setCacheFolder(String cacheFolder) {
		this.cacheFolder = cacheFolder;
	}

	@Bean
	@Primary
	public GraphHopper getGraphHopperBean() {

		if(isEnableElevator()){
			return GraphHopperInitUtil.GetInstance3d(getCacheFolder(),getDemFile(),"car","bike","foot");
		}else{
			return GraphHopperInitUtil.GetInstance2d(getCacheFolder(),"car","bike","foot");
		}
	}

	@Bean
	@Qualifier("customGraphHopper")
	public GraphHopper getGraphHopperCustomBean(){
		if(isEnableElevator()){
			return GraphHopperInitUtil.GetCustomInstance3d(getCustomCacheFolder(),getDemFile(),"car_custom");
		}else{
			return GraphHopperInitUtil.GetCusomInstance2d(getCustomCacheFolder(),"car_custom");
		}

	}



}
