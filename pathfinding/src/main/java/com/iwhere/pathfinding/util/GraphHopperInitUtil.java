package com.iwhere.pathfinding.util;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.iwhere.pathfinding.elevation.MyElevationProvider;

import java.util.ArrayList;
import java.util.List;

public class GraphHopperInitUtil {


    public static void InitGraphHopper(String osmFile,String cacheFolder,List<EncodeValueEnum> encodedValues,String ...profileNames){
        new GraphHopperBuilder(cacheFolder)
                .initialize(osmFile)
                .encodeValues(encodedValues)
                .withProfile(profileNames).build();
    }

    public static void InitGraphHopper3d(String osmFile,String demFile,String cacheFolder,
                                         List<EncodeValueEnum> encodeValueEnums,String ...profileNames){
        new GraphHopperBuilder(cacheFolder)
                .initialize(osmFile)
                .useElevation(demFile)
                .encodeValues(encodeValueEnums)
                .withProfile(profileNames).build();
    }

    public static void InitGraphHopper2dWithCustomModel(String osmFile,String cacheFolder,
                                                        List<EncodeValueEnum> encodeValueEnums,
                                                        String ...profileNames){
        new GraphHopperBuilder(cacheFolder)
                .initialize(osmFile)
                .encodeValues(encodeValueEnums)
                .withProfile(profileNames)
                .customModel().build();
    }

    public static void InitGraphHopper3dWithCustomModel(String osmFile,
                                                        String demFile,
                                                        String cacheFolder,
                                                        List<EncodeValueEnum> encodeValueEnums,
                                                        String ...profileNames){
        new GraphHopperBuilder(cacheFolder)
                .initialize(osmFile)
                .useElevation(demFile)
                .encodeValues(encodeValueEnums)
                .withProfile(profileNames)
                .customModel().build();
    }

    public static GraphHopper GetCustomInstance3d(String cacheFolder,String demFile,String ...profileNames){
        return new GraphHopperBuilder(cacheFolder).useElevation(demFile)
                .withProfile(profileNames).customModel().build();
    }

    public static GraphHopper GetCusomInstance2d(String cacheFolder,String ...profileNames){
        return new GraphHopperBuilder(cacheFolder).withProfile(profileNames).customModel().build();
    }

    public static GraphHopper GetInstance3d(String cacheFolder,String demFile,String ...profileNames){

        return  new GraphHopperBuilder(cacheFolder).useElevation(demFile).withProfile(profileNames).build();
    }

    public static GraphHopper GetInstance2d(String cacheFolder,String ...profileNames){
        return new GraphHopperBuilder(cacheFolder).withProfile(profileNames).build();
    }

    public static void main(String[] args) {
        String osmFile="d:/地图数据/osm/福建路网v1.5.osm";
        String cacheFolderCustom2d="d:/GraphhopperCache/cache_custom2d";
        String cacheFolder2d="d:/GraphhopperCache/cache2d";
        String cacheFolder3d="d:/GraphhopperCache/cache3d";
        String cacheFolderCustom3d="d:/GraphhopperCache/cache_custom3d";
        String profileNamesCustom[]={"car_custom"};
        String profileNames[]={"car","bike","foot"};
        String demFile="d:/GraphhopperCache/fujian.tif";
        List<EncodeValueEnum> encodeValueEnums=new ArrayList<>();
        encodeValueEnums.add(EncodeValueEnum.MAX_WIDTH);
        encodeValueEnums.add(EncodeValueEnum.LANES);
        encodeValueEnums.add(EncodeValueEnum.TOLL);
        encodeValueEnums.add(EncodeValueEnum.AVERAGE_SLOPE);
        encodeValueEnums.add(EncodeValueEnum.MAX_SLOPE);
        InitGraphHopper3d(osmFile,demFile,cacheFolder3d,encodeValueEnums,profileNames);
        //InitGraphHopper(osmFile,cacheFolder2d,encodeValueEnums,profileNames);
        //InitGraphHopper2dWithCustomModel(osmFile,cacheFolderCustom2d,encodeValueEnums,profileNamesCustom);
        //InitGraphHopper3dWithCustomModel(osmFile,demFile,cacheFolderCustom3d,encodeValueEnums,profileNamesCustom);

    }
}
