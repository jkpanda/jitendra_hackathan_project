package com.happy.hackweb.rest;

import java.util.concurrent.LinkedBlockingQueue;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONObject;

import storm.starter.spout.InAppNotificationSpout;
import storm.starter.spout.NotificationBolt;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

@Path("activity")
public class HappyHackJSONService {

	private static LocalCluster cluster = new LocalCluster();

	
	
	private static InAppNotificationSpout inAppNot =null;
	private static void setTopologies() {
		try{
			inAppNot =new InAppNotificationSpout();
		Config config = new Config();
		config.setDebug(true);
		config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("inApp-spout", inAppNot);
		builder.setBolt("inApp-bolt", new NotificationBolt()).shuffleGrouping("inApp-spout");
		
		
		cluster.submitTopology("InAppTopology", config, builder.createTopology());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createXmlEventsInJSON(String request) {

		if(null == inAppNot){
			setTopologies();
		}
		System.out.println("request is===" + request);
		try {
			org.codehaus.jettison.json.JSONObject jsonObject  = new org.codehaus.jettison.json.JSONObject(request);
			inAppNot.queue.put(jsonObject);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity("").build();

	}

	@GET
	@Path("shutdown")
	public Response shutDown() {

		System.out.println("Stopping the cluster");
		cluster.shutdown();
		return Response.status(200).entity("").build();
	}

}
