package com.iwhere.pathfinding.dto;

public class SearchRequest extends GPSPair{

    CustomPriority priority;

    public CustomPriority getPriority() {
        return priority;
    }

    public void setPriority(CustomPriority priority) {
        this.priority = priority;
    }
}
