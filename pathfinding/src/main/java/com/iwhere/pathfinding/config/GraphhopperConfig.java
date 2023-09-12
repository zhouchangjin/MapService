package com.iwhere.pathfinding.config;

import com.graphhopper.reader.dem.ElevationProvider;
import com.iwhere.pathfinding.elevation.MyElevationProvider;
import com.iwhere.pathfinding.util.GraphHopperInitUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;

@Configuration
@ConfigurationProperties(prefix = "graphhopper")
public class GraphhopperConfig {
	
	String cacheFolder;

	boolean enableElevator;

	String demFile;

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
	public GraphHopper getGraphHopperBean() {
		/**
		GraphHopper hopper=new GraphHopper();
		//System.out.println(getCacheFolder());
		if(isEnableElevator()){
			MyElevationProvider provider=new MyElevationProvider(getDemFile());
			provider.initialize();
			hopper.setElevationProvider(provider);
		}

		hopper.setGraphHopperLocation(getCacheFolder());
		hopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false));
		hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));
		hopper.importOrLoad();
        return hopper;
		 **/
		if(isEnableElevator()){
			return GraphHopperInitUtil.GetInstance3d(getCacheFolder(),getDemFile(),"car");
		}else{
			return GraphHopperInitUtil.GetInstance(getCacheFolder(),"car");
		}
	}

}
