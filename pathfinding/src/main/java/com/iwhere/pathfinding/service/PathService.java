package com.iwhere.pathfinding.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.graphhopper.util.CustomModel;
import com.iwhere.pathfinding.dto.GPSPointWithElevation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.PointList;
import com.iwhere.pathfinding.dto.GPSPoint;

import static com.graphhopper.json.Statement.If;
import static com.graphhopper.json.Statement.Op.LIMIT;
import static com.graphhopper.json.Statement.Op.MULTIPLY;

@Service
public class PathService {
	
	@Autowired
	GraphHopper graphhopper;

	@Autowired
	@Qualifier("customGraphHopper")
	GraphHopper customModelGraphhopper;

	public List<GPSPointWithElevation> searchRouteCustom(GPSPoint start,GPSPoint end){
		List<GPSPointWithElevation> plist=new ArrayList<>();
		GHRequest req=new GHRequest(start.getLatitude(),start.getLongitude(),
				end.getLatitude(),end.getLongitude())
				.setProfile("car_custom")
				.setLocale(Locale.CHINA);
		CustomModel customModel=new CustomModel();
		customModel.addToPriority(If("road_class == PRIMARY", MULTIPLY, 0.5));
		//customModel.addToPriority(If("true", LIMIT, 100));

		//customModel.addToPriority(If("average_slope > 10",MULTIPLY,0.1));

		req.setCustomModel(customModel);
		GHResponse rsp=customModelGraphhopper.route(req);
		if (rsp.hasErrors()) {
			System.out.println(rsp.getErrors().toString());
		}else {
			ResponsePath path = rsp.getBest();
			// 导航结果点位集合
			PointList pointList = path.getPoints();
			for (int i = 0; i < pointList.size(); ++i) {
				GPSPointWithElevation p=new GPSPointWithElevation();
				double longi=pointList.getLon(i);
				double lati=pointList.getLat(i);
				double elevation=pointList.getEle(i);
				p.setLatitude(lati);
				p.setLongitude(longi);
				p.setElevation(elevation);
				plist.add(p);
			}
		}
		return plist;
	}
	
	public List<GPSPointWithElevation> searchRoute(GPSPoint start, GPSPoint end) {
		ArrayList<GPSPointWithElevation> plist = new ArrayList<GPSPointWithElevation>();
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
	        	 GPSPointWithElevation p=new GPSPointWithElevation();
	        	 double longi=pointList.getLon(i);
	        	 double lati=pointList.getLat(i);
				 double elevation=pointList.getEle(i);
	        	 p.setLatitude(lati);
	        	 p.setLongitude(longi);
				 p.setElevation(elevation);
	        	 plist.add(p);
	         }
		}
		return plist;
	}

}
