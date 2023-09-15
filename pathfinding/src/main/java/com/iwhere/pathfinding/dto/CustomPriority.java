package com.iwhere.pathfinding.dto;

public class CustomPriority{

    //主干道权重
    float primaryRoadWeight;

    //次干道权重
    float secondaryRoadWeight;

    //车道限制
    int minLane=1;

    //走不走收费路段
    boolean tool=true;

    public float getPrimaryRoadWeight() {
        return primaryRoadWeight;
    }

    public void setPrimaryRoadWeight(float primaryRoadWeight) {
        this.primaryRoadWeight = primaryRoadWeight;
    }

    public float getSecondaryRoadWeight() {
        return secondaryRoadWeight;
    }

    public void setSecondaryRoadWeight(float secondaryRoadWeight) {
        this.secondaryRoadWeight = secondaryRoadWeight;
    }

    public int getMinLane() {
        return minLane;
    }

    public void setMinLane(int minLane) {
        this.minLane = minLane;
    }

    public boolean isTool() {
        return tool;
    }

    public void setTool(boolean tool) {
        this.tool = tool;
    }
}
