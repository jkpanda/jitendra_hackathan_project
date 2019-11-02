package storm.starter.spout;



import java.sql.Timestamp;
import java.util.Map;
import java.util.WeakHashMap;

import org.json.simple.JSONObject;

import storm.starter.spout.MongoDBQuery;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class ActiveGeographyCount implements IRichBolt {

	private MongoDBQuery dbQuery=null;
	private WeakHashMap<String, String> userIDWithTimeStamp;;
	
	public void execute(Tuple tuple) {
		// TODO Auto-generated method stub
		org.codehaus.jettison.json.JSONObject object=(org.codehaus.jettison.json.JSONObject)tuple;
		int count=0;
		//java.util.Date date= new java.util.Date();
		try {
			String userID = object.get("User ID").toString();
			DBObject userdbObj = dbQuery.getUser(userID);
			//Timestamp currentTimestamp= new Timestamp(date.getTime());
			new MongoDBQuery().setUserCountInGeography(object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	/*	
		String pinCode=dbQuery.getUser(userID).get("pin code").toString();
		DBObject pinCodeObject= dbQuery.getLocation(pinCode);
		String districtName=pinCodeObject.get("disrictname").toString();
		String stateName=pinCodeObject.get("statename").toString();
	
		userIDWithTimeStamp.put(userID, currentTimestamp.toString());*/
		
	}
	
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		
	}


	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
