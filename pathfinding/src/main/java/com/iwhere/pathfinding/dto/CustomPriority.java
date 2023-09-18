package com.iwhere.pathfinding.dto;

public class CustomPriority{

    //主干道权重
    float primaryRoadWeight;

    //次干道权重
    float secondaryRoadWeight;


    float speed=110;



    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


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

}
