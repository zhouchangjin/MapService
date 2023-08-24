package com.iwhere.pathfinding.util;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;

public class GraphHopperInitUtil {

    public static void InitGraphHopper(String osmFile,String cachefolder,String ...profileNames){
        GraphHopper hopper=new GraphHopper();
        hopper.setOSMFile(osmFile);
        hopper.setGraphHopperLocation(cachefolder);
        ProfileFactory fac=new ProfileFactory();
        Profile[] profiles=fac.createProfilesWithName(profileNames);
        CHProfile[] chProfiles=fac.createCHProfilesWithName(profileNames);
        hopper.setProfiles(profiles);
        hopper.getCHPreparationHandler().setCHProfiles(chProfiles);
        hopper.importAndClose();
    }

    public static void main(String[] args) {
        InitGraphHopper("d:/Fujian_Road_Network.osm","d:/cache","car","bike","foot");
    }
}
