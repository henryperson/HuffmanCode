import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
	private HuffmanPriorityQueue.HuffmanNode[] data;
	private int size;
	
	public HuffmanTree(Map<Integer, Integer> m) {
		HuffmanPriorityQueue pq = new HuffmanPriorityQueue(2*m.size() + 1);
		pq.addFromMap(m);
		pq.condense();
		data = pq.getArray();
	}
	
	public static void main(String[] args) {
		Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();
		freqMap.put((int) 'z', 1);
		freqMap.put((int) ' ', 2);
		freqMap.put((int) 'b', 2);
		freqMap.put((int) 'a', 3);
		HuffmanPriorityQueue pq = new HuffmanPriorityQueue(2*freqMap.size() + 1);
		pq.addFromMap(freqMap);
		pq.condense();
		System.out.println(pq.toString());
	}
}
