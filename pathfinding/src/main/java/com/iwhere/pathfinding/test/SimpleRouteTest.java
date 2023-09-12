package com.iwhere.pathfinding.test;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;
import com.graphhopper.util.Translation;
import com.iwhere.pathfinding.util.GraphHopperInitUtil;

import java.util.Locale;

public class SimpleRouteTest {

    public static void routing(GraphHopper hopper,double fromLat,double fromLon,
                               double tolat,double toLong){
        GHRequest req=new GHRequest(fromLat,fromLon,tolat,toLong).setProfile("car").setLocale(Locale.CHINA);
        GHResponse rsp=hopper.route(req);
        if(rsp.hasErrors()){
            throw new RuntimeException(rsp.getErrors().toString());
        }
        ResponsePath path=rsp.getBest();

        PointList pointList=path.getPoints();
        double distance=path.getDistance();
        long timeInMs=path.getTime();

        System.out.println("路线定位：");

        StringBuffer sb=new StringBuffer();

        for(int i=0;i<pointList.size();i++){
            if(i>0){
                sb.append("; ");
            }
            sb.append(pointList.getLon(i));
            sb.append(",");
            sb.append(pointList.getLat(i));
            if(pointList.is3D()){
                sb.append(",");
                sb.append(pointList.getEle(i));
            }
        }
        System.out.println(sb.toString()+"\n---------------------");
        System.out.println("总距离： "+distance+", 总用时： "+timeInMs);

        Translation tr=hopper.getTranslationMap().getWithFallBack(Locale.CHINA);
        InstructionList il= path.getInstructions();

        PointList pl=path.getWaypoints();

        for(Instruction instruction:il){
            System.out.println("下一个目的地： "+instruction.getName());
            System.out.println("左右转："+instruction.getTurnDescription(tr));
            System.out.println("distance "+instruction.getDistance()+
                    " for instruction: "+instruction.getTurnDescription(tr));
        }

    }

    public static void main(String[] args) {
        double fromLat=24.51429705;
        double fromLongitude=118.10531256;
        double toLatitude =24.47891989;
        double toLongitude=118.08894888;
        GraphHopper gh=GraphHopperInitUtil.GetInstance("d:/cache","car","bike","foot");
        routing(gh,fromLat,fromLongitude,toLatitude,toLongitude);
    }
}
