package com.iwhere.pathfinding.util;


import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.LMProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.weighting.custom.CustomProfile;
import com.iwhere.pathfinding.elevation.MyElevationProvider;

public class GraphHopperBuilder{
    String osmFile;
    String demFile;

    String cacheFolder;

    String[] profileNames;

    boolean initialize=false;

    boolean enableElevation=false;

    boolean customModel=false;

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

    public GraphHopperBuilder customModel(){
        this.customModel=true;
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
        /**
        EncodingManager.Builder builder=hopper.getEncodingManagerBuilder();
        builder.addIfAbsent(hopper.getTagParserFactory(),"toll");
        **/
        ProfileFactory fac=new ProfileFactory();
        Profile[] profiles=fac.createProfilesWithName(profileNames);
        hopper.setProfiles(profiles);
        if(customModel){
            //使用landmark算法
            LMProfile[] lmProfiles=fac.createLMProfilesWithName(profileNames);
            hopper.getLMPreparationHandler().setLMProfiles(lmProfiles);
        }else{
            //使用ch算法
            CHProfile[] chProfiles=fac.createCHProfilesWithName(profileNames);
            hopper.getCHPreparationHandler().setCHProfiles(chProfiles);
        }
        if(initialize){
            hopper.importAndClose();
            return null;
        }else{
            hopper.importOrLoad();
            return hopper;
        }
    }
}
