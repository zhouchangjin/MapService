package com.iwhere.pathfinding.dto;

public class CustomPriority{

    //主干道权重
    float primaryRoadWeight;

    float average_slope;

    boolean useSlope;

    public boolean isUseSlope() {
        return useSlope;
    }

    public void setUseSlope(boolean useSlope) {
        this.useSlope = useSlope;
    }

    public float getAverage_slope() {
        return average_slope;
    }

    public void setAverage_slope(float average_slope) {
        this.average_slope = average_slope;
    }

    public float getPrimaryRoadWeight() {
        return primaryRoadWeight;
    }

    public void setPrimaryRoadWeight(float primaryRoadWeight) {
        this.primaryRoadWeight = primaryRoadWeight;
    }

}
