package com.iwhere.pathfinding.util;


import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.iwhere.pathfinding.elevation.MyElevationProvider;

public class GraphHopperBuilder{
    String osmFile;
    String demFile;

    String cacheFolder;

    String[] profileNames;

    boolean initialize=false;

    boolean enableElevation=false;

    public GraphHopperBuilder(String cacheFolder){
        this.cacheFolder=cacheFolder;
    }

    public GraphHopperBuilder initialize(String osmFile){
        this.osmFile=osmFile;
        initialize=true;
        return this;
    }

    public GraphHopperBuilder useElevation(String demFile){
        this.demFile=demFile;
        this.enableElevation=true;
        return this;
    }

    public GraphHopperBuilder withProfile(String ... profileNames){
        this.profileNames=profileNames;
        return this;
    }

    public GraphHopper build(){
        GraphHopper hopper=new GraphHopper();
        hopper.setGraphHopperLocation(cacheFolder);
        if(initialize){
            hopper.setOSMFile(osmFile);
        }
        if(enableElevation){
            MyElevationProvider provider=new MyElevationProvider(demFile);
            provider.initialize();
            hopper.setElevationProvider(provider);
        }
        ProfileFactory fac=new ProfileFactory();
        Profile[] profiles=fac.createProfilesWithName(profileNames);
        hopper.setProfiles(profiles);
        CHProfile[] chProfiles=fac.createCHProfilesWithName(profileNames);
        hopper.getCHPreparationHandler().setCHProfiles(chProfiles);
        if(initialize){
            hopper.importAndClose();
            return null;
        }else{
            hopper.importOrLoad();
            return hopper;
        }
    }
}
