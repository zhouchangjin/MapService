package com.iwhere.pathfinding.util;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.iwhere.pathfinding.elevation.MyElevationProvider;

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

    public static void InitGraphHopper3d(String osmFile,String tiffFile,String cachefolder,String ...profileNames){
        GraphHopper hopper=new GraphHopper();
        hopper.setOSMFile(osmFile);
        hopper.setGraphHopperLocation(cachefolder);
        MyElevationProvider provider=new MyElevationProvider(tiffFile);
        provider.initialize();
        hopper.setElevationProvider(provider);
        ProfileFactory fac=new ProfileFactory();
        Profile[] profiles=fac.createProfilesWithName(profileNames);
        CHProfile[] chProfiles=fac.createCHProfilesWithName(profileNames);
        hopper.setProfiles(profiles);
        hopper.getCHPreparationHandler().setCHProfiles(chProfiles);
        hopper.importAndClose();
    }

    public static GraphHopper GetInstance(String cacheFolder,String ...profileNames){
        GraphHopper hopper=new GraphHopper();
        hopper.setGraphHopperLocation(cacheFolder);
        ProfileFactory fac=new ProfileFactory();
        Profile[] profiles=fac.createProfilesWithName(profileNames);
        CHProfile[] chProfiles=fac.createCHProfilesWithName(profileNames);
        hopper.setProfiles(profiles);
        hopper.getCHPreparationHandler().setCHProfiles(chProfiles);
        hopper.importOrLoad();
        return hopper;
    }

    public static void main(String[] args) {
        //InitGraphHopper("d:/地图数据/osm/福建路网v1.5.osm","d:/cache","car","bike","foot");
        //InitGraphHopper3d("d:/地图数据/OSM/福建路网v1.5.osm","d:/cache/fujian.tif","d:/cache","car","bike","foot");
    }
}
