package com.iwhere.pathfinding.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.PointList;
import com.iwhere.pathfinding.dto.GPSPoint;

@Service
public class PathService {
	
	@Autowired
	GraphHopper graphhopper;
	
	public List<GPSPoint> searchRoute(GPSPoint start, GPSPoint end) {
		ArrayList<GPSPoint> plist = new ArrayList<GPSPoint>();
        //System.out.println(start.getLatitude()+"_"+start.getLongitude());
		GHRequest req = new GHRequest(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude()).setProfile("car")
				.setLocale(Locale.CHINA);
		GHResponse rsp = graphhopper.route(req);
		if (rsp.hasErrors()) {
			System.out.println(rsp.getErrors().toString());
		}else {
			ResponsePath path = rsp.getBest();
	         // 导航结果点位集合
	         PointList pointList = path.getPoints();
	         for (int i = 0; i < pointList.size(); ++i) {
	        	 GPSPoint p=new GPSPoint();
	        	 double longi=pointList.getLon(i);
	        	 double lati=pointList.getLat(i);
	        	 p.setLatitude(lati);
	        	 p.setLongitude(longi);
	        	 plist.add(p);
	         }
		}
		return plist;
	}

}