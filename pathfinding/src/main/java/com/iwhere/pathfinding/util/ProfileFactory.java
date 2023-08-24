package com.iwhere.pathfinding.util;

import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileFactory {

    CHProfile[] createCHProfilesWithName(String... names){
        List<CHProfile> list=new ArrayList<CHProfile>();
        for(String name:names){
            CHProfile chProfile=new CHProfile(name);
            list.add(chProfile);
        }
        return (CHProfile[]) list.toArray(new CHProfile[0]);
    }

    Profile[] createProfilesWithName(String... names){
        List<Profile> list=new ArrayList<Profile>();
        for(String name:names){
            Profile newProfile=createProfileWithName(name);
            if(newProfile!=null){
                list.add(newProfile);
            }
        }
        return (Profile[]) list.toArray(new Profile[0]);
    }

    Profile createProfileWithName(String name){
        if(name.equals("car")){
            return new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false);
        }else if(name.equals("bike")){
            return new Profile("bike").setVehicle("bike").setWeighting("fastest").setTurnCosts(false);
        }else if(name.equals("foot")){
            return new Profile("foot").setWeighting("fastest");
        }else{
            return null;
        }
    }

}
