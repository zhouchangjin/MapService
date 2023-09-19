package com.iwhere.pathfinding.test;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.*;
import com.graphhopper.util.details.PathDetail;
import com.graphhopper.util.details.PathDetailsBuilderFactory;
import com.iwhere.pathfinding.util.GraphHopperInitUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.graphhopper.json.Statement.If;
import static com.graphhopper.json.Statement.Op.LIMIT;
import static com.graphhopper.json.Statement.Op.MULTIPLY;

public class SimpleRouteTest {

    public static void routingCustom(GraphHopper hopper,double fromLat,double fromLon,
                                     double tolat,double toLong){
        GHRequest req=new GHRequest(fromLat,fromLon,tolat,toLong).setProfile("car_custom").setLocale(Locale.CHINA);
        List<String> pathdetail=new ArrayList<>();
        pathdetail.add("road_class");
        req.setPathDetails(pathdetail);

        CustomModel customModel=new CustomModel();
        customModel.addToPriority(If("road_class == PRIMARY", MULTIPLY, "1.0"));
        // unconditional limit to 100km/h
        customModel.addToPriority(If("true", LIMIT, "100"));

        req.setCustomModel(customModel);

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
        Map<String, List<PathDetail>> res= path.getPathDetails();
        System.out.println(res);

        PointList pl=path.getWaypoints();

        for(Instruction instruction:il){

            System.out.println("下一个目的地： "+instruction.getName());
            System.out.println("左右转："+instruction.getTurnDescription(tr));
            System.out.println("distance "+instruction.getDistance()+
                    " for instruction: "+instruction.getTurnDescription(tr));
        }
    }

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
        //GraphHopper gh=GraphHopperInitUtil.GetInstance("d:/cache","car","bike","foot");
        //GraphHopper gh3d=GraphHopperInitUtil.GetCustomInstance3d("d:/cache_custom",
        //        "d:/cache/fujian.tif","car_custom");
        GraphHopper gh2d=GraphHopperInitUtil.GetCusomInstance2d("d:/graphhoppercache/cache_custom2d",
                "car_custom");
        routingCustom(gh2d,fromLat,fromLongitude,toLatitude,toLongitude);
    }
}
