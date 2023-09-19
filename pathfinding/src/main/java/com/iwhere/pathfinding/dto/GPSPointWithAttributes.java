package com.iwhere.pathfinding.dto;

import java.util.HashMap;
import java.util.Map;

public class GPSPointWithAttributes extends GPSPointWithElevation{

    Map<String,String> edgeAtrribute=new HashMap<>();

    public Map<String, String> getEdgeAtrribute() {
        return edgeAtrribute;
    }

    public void setEdgeAtrribute(Map<String, String> edgeAtrribute) {
        this.edgeAtrribute = edgeAtrribute;
    }

    public void addAttribute(String key,String value){
        edgeAtrribute.put(key,value);
    }
}
