package com.iwhere.pathfinding.util;

public enum EncodeValueEnum {

    MAX_WIDTH("max_width"),
    LANES("lanes"),

    TOLL("toll"),

    AVERAGE_SLOPE("average_slope"),

    MAX_SLOPE("max_slope");

    EncodeValueEnum(String value){
        this.value=value;
    }

    String value;

    public String getValue(){
        return value;
    }
}
