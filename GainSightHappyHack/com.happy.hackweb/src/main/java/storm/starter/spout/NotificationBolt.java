package storm.starter.spout;

import java.util.Map;
import java.util.WeakHashMap;

import org.codehaus.jettison.json.JSONException;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class NotificationBolt implements IRichBolt {
	private OutputCollector collector;
	private WeakHashMap<String, String> inAppNotify;

	public void cleanup() {
		for (Map.Entry<String, String> entry : inAppNotify.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

	public void execute(Tuple tuple) {
		try {
			org.codehaus.jettison.json.JSONObject object = (org.codehaus.jettison.json.JSONObject) tuple.getValues().get(0);
			String userID = object.get("userid").toString();
			String event = object.get("event").toString();
			String event_type = object.get("eventType").toString();
			if(inAppNotify == null) inAppNotify = new WeakHashMap<String, String>();
			if ("Mobile".equals(event_type) && ("AddToCart".equals(event)||"RemoveFromCart".equals(event)||"OrderPlaced".equals(event))) {
				inAppNotify.put(userID, event);
				System.out.println("Its mobile");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		collector.ack(tuple);
	}

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("notification"));
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
