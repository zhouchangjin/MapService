package com.iwhere.pathfinding.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.graphhopper.util.CustomModel;
import com.graphhopper.util.details.PathDetail;
import com.iwhere.pathfinding.dto.CustomPriority;
import com.iwhere.pathfinding.dto.GPSPointWithAttributes;
import com.iwhere.pathfinding.dto.GPSPointWithElevation;
import io.swagger.models.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.PointList;
import com.iwhere.pathfinding.dto.GPSPoint;

import static com.graphhopper.json.Statement.*;
import static com.graphhopper.json.Statement.Op.LIMIT;
import static com.graphhopper.json.Statement.Op.MULTIPLY;

@Service
public class PathService {
	
	@Autowired
	GraphHopper graphhopper;

	@Autowired
	@Qualifier("customGraphHopper")
	GraphHopper customModelGraphhopper;

	private void prePareRequestDetail(GHRequest request){
		List<String> pathDetails=new ArrayList<>();
		pathDetails.add("road_class");
		pathDetails.add("max_speed");
		pathDetails.add("max_width");
		pathDetails.add("lanes");
		pathDetails.add("max_slope");
		pathDetails.add("average_slope");
		pathDetails.add("toll");
		request.setPathDetails(pathDetails);
	}

	private List<GPSPointWithAttributes> postRequestProcess(GHResponse rsp){
		List<GPSPointWithAttributes> plist=new ArrayList<>();
		ResponsePath path = rsp.getBest();
		// 导航结果点位集合
		PointList pointList = path.getPoints();

		for (int i = 0; i < pointList.size(); ++i) {
			GPSPointWithAttributes p=new GPSPointWithAttributes();
			double longi=pointList.getLon(i);
			double lati=pointList.getLat(i);
			double elevation=pointList.getEle(i);
			p.setLatitude(lati);
			p.setLongitude(longi);
			p.setElevation(elevation);
			plist.add(p);
		}
		Map<String,List<PathDetail>> pathDetail=path.getPathDetails();
		for(String key:pathDetail.keySet()){
			List<PathDetail> pathDetailList=pathDetail.get(key);
			for(PathDetail pd:pathDetailList){
				int first=pd.getFirst();
				int last=pd.getLast();
				for(int i=first;i<last;i++){
					plist.get(i).addAttribute(key,pd.getValue().toString());
				}
			}
		}
		return plist;
	}

	public List<GPSPointWithAttributes> searchRouteCustom(GPSPoint start, GPSPoint end, CustomPriority priority){

		GHRequest req=new GHRequest(start.getLatitude(),start.getLongitude(),
				end.getLatitude(),end.getLongitude())
				.setProfile("car_custom")
				.setLocale(Locale.CHINA);

		prePareRequestDetail(req);

		CustomModel customModel=new CustomModel();
		double priCoef=priority.getPrimaryRoadWeight();
		boolean useSlope= priority.isUseSlope();

		customModel.addToPriority(If("road_class == PRIMARY", MULTIPLY, ""+priCoef));

		if(useSlope){
			customModel.addToPriority(If("average_slope>20 || average_slope<-20 ",MULTIPLY,"0.1"));
			customModel.addToPriority(ElseIf("average_slope>10 || average_slope<-10 ",MULTIPLY,"0.4"));
			customModel.addToPriority(ElseIf("average_slope>5 || average_slope<-5 ",MULTIPLY,"0.7"));
			customModel.addToPriority(ElseIf("average_slope>3 || average_slope<-3 ",MULTIPLY,"0.8"));
			customModel.addToPriority(ElseIf("average_slope>1 || average_slope<-1 ",MULTIPLY,"0.9"));
			customModel.addToPriority(Else(MULTIPLY,"1.0"));
		}

		req.setCustomModel(customModel);
		GHResponse rsp=customModelGraphhopper.route(req);
		if (rsp.hasErrors()) {
			System.out.println(rsp.getErrors().toString());
		}else {
			return postRequestProcess(rsp);
		}
		return new ArrayList<>();
	}
	
	public List<GPSPointWithAttributes> searchRoute(GPSPoint start, GPSPoint end) {

		GHRequest req = new GHRequest(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude()).setProfile("car")
				.setLocale(Locale.CHINA);

		prePareRequestDetail(req);

		GHResponse rsp = graphhopper.route(req);
		if (rsp.hasErrors()) {
			System.out.println(rsp.getErrors().toString());
		}else {
			return postRequestProcess(rsp);
		}
		return new ArrayList<>();
	}

	public List<GPSPointWithAttributes> searchBike(GPSPoint start,GPSPoint end){
		GHRequest req = new GHRequest(start.getLatitude(),
				start.getLongitude(),
				end.getLatitude(),
				end.getLongitude())
				.setProfile("bike")
				.setLocale(Locale.CHINA);
		prePareRequestDetail(req);

		GHResponse rsp = graphhopper.route(req);
		if (rsp.hasErrors()) {
			System.out.println(rsp.getErrors().toString());
		}else {
			return postRequestProcess(rsp);
		}
		return new ArrayList<>();
	}

	public List<GPSPointWithAttributes> searchFoot(GPSPoint start,GPSPoint end){
		GHRequest req = new GHRequest(start.getLatitude(),
				start.getLongitude(),
				end.getLatitude(),
				end.getLongitude())
				.setProfile("foot")
				.setLocale(Locale.CHINA);
		prePareRequestDetail(req);

		GHResponse rsp = graphhopper.route(req);
		if (rsp.hasErrors()) {
			System.out.println(rsp.getErrors().toString());
		}else {
			return postRequestProcess(rsp);
		}
		return new ArrayList<>();
	}

}
