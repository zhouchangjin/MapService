package com.iwhere.pathfinding.config;

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

	public String getCacheFolder() {
		return cacheFolder;
	}

	public void setCacheFolder(String cacheFolder) {
		this.cacheFolder = cacheFolder;
	}

	@Bean
	public GraphHopper getGraphHopperBean() {
		GraphHopper hopper=new GraphHopper();
		System.out.println(getCacheFolder());
		hopper.setGraphHopperLocation(getCacheFolder());
		hopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false));
		hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));
		hopper.importOrLoad();
        return hopper;
	}

}
