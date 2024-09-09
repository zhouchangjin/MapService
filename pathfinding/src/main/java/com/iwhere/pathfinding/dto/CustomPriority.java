package com.iwhere.pathfinding.dto;

public class CustomPriority{

    //主干道权重
    float primaryRoadWeight;

    boolean useSlope;

    public boolean isUseSlope() {
        return useSlope;
    }

    public void setUseSlope(boolean useSlope) {
        this.useSlope = useSlope;
    }

    public float getPrimaryRoadWeight() {
        return primaryRoadWeight;
    }

    public void setPrimaryRoadWeight(float primaryRoadWeight) {
        this.primaryRoadWeight = primaryRoadWeight;
    }

}
