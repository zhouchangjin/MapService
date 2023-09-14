package com.iwhere.pathfinding.config;

import com.graphhopper.reader.dem.ElevationProvider;
import com.iwhere.pathfinding.elevation.MyElevationProvider;
import com.iwhere.pathfinding.util.GraphHopperInitUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
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
			return GraphHopperInitUtil.GetInstance3d(getCacheFolder(),getDemFile(),"car");
		}else{
			return GraphHopperInitUtil.GetInstance(getCacheFolder(),"car");
		}
	}

	@Bean
	@Qualifier("customGraphHopper")
	public GraphHopper getGraphHopperCustomBean(){
		return GraphHopperInitUtil.GetCustomInstance3d(getCustomCacheFolder(),getDemFile(),"car_custom");
	}



}
