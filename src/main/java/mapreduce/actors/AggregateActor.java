package mapreduce.actors;

import java.util.HashMap;
import java.util.Map;

import akka.actor.UntypedActor;
import mapreduce.messages.ReduceData;
import mapreduce.messages.Result;

public class AggregateActor extends UntypedActor {

	private Map<String, Integer> finalReducedMap = new HashMap<String, Integer>();

	@Override
	public void onReceive(Object message) throws Exception {
		
		System.out.println((String)message);
		System.out.println("onReceive"+ finalReducedMap.toString());
		
		if (message instanceof ReduceData) {
			ReduceData reduceData = (ReduceData) message;
			aggregateInMemoryReduce(reduceData.getReduceDataList());
		} else if (message instanceof Result) {
			System.out.println(finalReducedMap.toString());
		} else
			unhandled(message);
	}

	private void aggregateInMemoryReduce(Map<String, Integer> reducedList) {
		System.out.println("aggregateInMemoryReduce"+ finalReducedMap.toString());
		
		Integer count = null;
		for (String key : reducedList.keySet()) {
			if (finalReducedMap.containsKey(key)) {
				count = reducedList.get(key) + finalReducedMap.get(key);
				finalReducedMap.put(key, count);
			} else {
				finalReducedMap.put(key, reducedList.get(key));
			}
		}
	}
}
