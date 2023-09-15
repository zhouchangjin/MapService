package com.iwhere.pathfinding.controller;

import java.util.List;

import com.iwhere.pathfinding.dto.GPSPointWithElevation;
import com.iwhere.pathfinding.dto.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iwhere.pathfinding.dto.Message;
import com.iwhere.pathfinding.dto.GPSPair;
import com.iwhere.pathfinding.service.PathService;

@CrossOrigin
@RestController
@RequestMapping("route")
public class RouteController {
	
	@Autowired
	PathService pathService;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public Message getRoute(@RequestBody GPSPair points) {
		Message message = new Message();
		message.setMessage("获取成功");
		List<GPSPointWithElevation> plist=pathService.searchRoute(points.getStart(), points.getEnd());
		message.setData(plist);
		return message;
	}

	@RequestMapping(value = "/searchCustom", method = RequestMethod.POST)
	@ResponseBody
	public Message getRouteCustom(@RequestBody SearchRequest request) {
		Message message = new Message();
		message.setMessage("获取成功");
		List<GPSPointWithElevation> plist=pathService.searchRouteCustom(
				request.getStart(),
				request.getEnd(),
				request.getPriority());
		message.setData(plist);
		return message;
	}

}
