package com.iwhere.pathfinding.util;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.iwhere.pathfinding.elevation.MyElevationProvider;

import java.util.List;

public class GraphHopperInitUtil {


    public static void InitGraphHopper(String osmFile,String cachefolder,String ...profileNames){
        new GraphHopperBuilder(cachefolder).initialize(osmFile).withProfile(profileNames).build();
    }

    public static void InitGraphHopper3d(String osmFile,String demFile,String cachefolder,String ...profileNames){
        new GraphHopperBuilder(cachefolder).initialize(osmFile).useElevation(demFile).withProfile(profileNames).build();
    }

    public static GraphHopper GetInstance3d(String cacheFolder,String demFile,String ...profileNames){

        return  new GraphHopperBuilder(cacheFolder).useElevation(demFile).withProfile(profileNames).build();
    }

    public static GraphHopper GetInstance(String cacheFolder,String ...profileNames){
        return new GraphHopperBuilder(cacheFolder).withProfile(profileNames).build();
    }

    public static void main(String[] args) {

        //InitGraphHopper("d:/地图数据/osm/福建路网v1.5.osm","d:/cache","car","bike","foot");
        //InitGraphHopper3d("d:/地图数据/OSM/福建路网v1.5.osm","d:/cache/fujian.tif","d:/cache","car","bike","foot");
    }
}
