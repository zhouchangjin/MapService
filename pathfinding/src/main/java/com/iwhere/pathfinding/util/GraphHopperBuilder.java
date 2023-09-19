package com.iwhere.pathfinding.util;


import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.LMProfile;
import com.graphhopper.config.Profile;
import com.iwhere.pathfinding.elevation.MyElevationProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphHopperBuilder{
    String osmFile;
    String demFile;

    String cacheFolder;

    String[] profileNames;

    boolean initialize=false;

    boolean enableElevation=false;

    boolean customModel=false;

    String encodeString="";

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

    public GraphHopperBuilder encodeValues(List<EncodeValueEnum> values){
        encodeValues(values.toArray(new EncodeValueEnum[0]));
        return this;
    }

    public GraphHopperBuilder encodeValues(EncodeValueEnum ...values){
        List<String> strList=new ArrayList<>();
        for(EncodeValueEnum encodeValueEnum:values){
            strList.add(encodeValueEnum.getValue());
        }
        encodeValues(strList.toArray(new String[0]));
        return this;
    }

    public GraphHopperBuilder encodeValues(String ... values){
        this.encodeString=String.join(",",values);
        return this;
    }

    public GraphHopperBuilder customModel(){
        this.customModel=true;
        return this;
    }

    public GraphHopper build(){
        GraphHopper hopper=new GraphHopper();
        hopper.setGraphHopperLocation(cacheFolder);
        hopper.setEncodedValuesString(encodeString);
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
