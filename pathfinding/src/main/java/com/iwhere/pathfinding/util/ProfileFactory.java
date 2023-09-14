package com.iwhere.pathfinding.util;

import com.graphhopper.config.CHProfile;
import com.graphhopper.config.LMProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.routing.weighting.custom.CustomProfile;
import com.graphhopper.util.CustomModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileFactory {

    public LMProfile[] createLMProfilesWithName(String... names){
        List<LMProfile> list =new ArrayList<>();
        for(String name:names){
            LMProfile lmProfile=new LMProfile(name);
            list.add(lmProfile);
        }
        return  list.toArray(new LMProfile[0]);
    }

    public CHProfile[] createCHProfilesWithName(String... names){
        List<CHProfile> list=new ArrayList<>();
        for(String name:names){
            CHProfile chProfile=new CHProfile(name);
            list.add(chProfile);
        }
        return  list.toArray(new CHProfile[0]);
    }


    public Profile[] createProfilesWithName(String... names){
        List<Profile> list=new ArrayList<>();
        for(String name:names){
            Profile newProfile=createProfileWithName(name);
            if(newProfile!=null){
                list.add(newProfile);
            }
        }
        return list.toArray(new Profile[0]);
    }

    public Profile createProfileWithName(String name){
        if(name.equals("car")){
            return new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false);
        }else if(name.equals("bike")){
            return new Profile("bike").setVehicle("bike").setWeighting("fastest").setTurnCosts(false);
        }else if(name.equals("foot")){
            return new Profile("foot").setWeighting("fastest");
        }else if(name.contains("custom")){
            CustomModel customModel=new CustomModel();
            if(name.contains("car")){
                return new CustomProfile(name).setCustomModel(customModel).setVehicle("car");
            }else if(name.contains("bike")){
                return new CustomProfile(name).setCustomModel(customModel).setVehicle("bike");
            }else if(name.contains("foot")){
                return new CustomProfile(name).setCustomModel(customModel).setVehicle("foot");
            }else{
                return null;
            }
        }
        else{
            return null;
        }
    }

}
