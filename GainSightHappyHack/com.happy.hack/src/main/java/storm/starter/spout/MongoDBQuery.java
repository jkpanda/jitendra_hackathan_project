package storm.starter.spout;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBQuery {
	private static MongoClient mongoClient = null;
	private static DB db =null;
	public MongoDBQuery() throws Exception{
		mongoClient = new MongoClient("192.168.0.76", 27017);
		db = mongoClient.getDB("gainsight");
	}
	
	
	public DBObject getUser(String userId){
		DBCollection coll = db.getCollection("users_master");
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("id", userId);
		DBCursor dbCursor = coll.find(fields);
		DBObject dbObject = null;
		while(dbCursor.hasNext()){
			dbObject = dbCursor.next();
			System.out.println(dbObject.get("email"));
			break;
		}
		
		return dbObject;
	}
	
	

	public DBObject getLocation(String pin){
		DBCollection coll = db.getCollection("pin_master");
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("pincode", pin);
		DBCursor dbCursor = coll.find(fields);
		DBObject dbObject = null;
		while(dbCursor.hasNext()){
			dbObject = dbCursor.next();
		}
		
		return dbObject;
	}
	
	public DBObject getProduct(String sku){
		DBCollection coll = db.getCollection("products_master");
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("sku", sku);
		DBCursor dbCursor = coll.find(fields);
		DBObject dbObject = null;
		while(dbCursor.hasNext()){
			dbObject = dbCursor.next();
		}
		return dbObject;
	}
	
	public void setEvent(JSONObject eventObj){
		DBCollection coll = db.getCollection("notification_table");
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("id", eventObj.get("userId"));
		DBCursor cursor = coll.find(fields);
		 while(cursor.hasNext()){
			 DBObject update = cursor.next();
			 
			 JSONArray timeArray = (JSONArray) update.get("time");
			 if(timeArray == null) timeArray = new JSONArray();
			 timeArray.add(eventObj.get("time"));
			 update.put("time", timeArray);
			 
			 JSONArray eventArray = (JSONArray) update.get("event");
			 if(eventArray == null) eventArray = new JSONArray();
			 eventArray.add(eventObj.get("event"));
			 update.put("event", eventArray);
			 
			 JSONArray skuArray = (JSONArray) update.get("sku");
			 if(skuArray == null) eventArray = new JSONArray();
			 skuArray.add(eventObj.get("sku"));
			 update.put("sku", skuArray);
			 
			 JSONArray priceArray = (JSONArray)update.get("price");
			 if(priceArray == null) priceArray = new JSONArray();
			 priceArray.add(eventObj.get("price"));
			 update.put("price", priceArray);
			 
			 JSONArray searchArray = (JSONArray)update.get("serach string");
			 if(searchArray == null) searchArray = new JSONArray();
			 searchArray.add(eventObj.get("search string"));
			 update.put("search string", searchArray);
		 }
	}
	/*public static void main(String args[]) throws Exception{
		DBObject obj = new MongoDBQuery().getUser("080060cb-5111-J02S-a96e-7d6df4228cbe");
		System.out.println(obj.get("email")+" "+obj.get("name"));
	}*/
}
