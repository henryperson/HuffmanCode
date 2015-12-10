import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
	private PriorityQueue<HuffmanNode> data;
	private HuffmanNode root;
	private int size;
	
	public HuffmanTree(Map<Integer, Integer> m) {
		root = null;
		data = new PriorityQueue<>();
		data.add(new HuffmanLeafNode (256, 1));
		Iterator<Integer> iter = m.keySet().iterator();
		while (iter.hasNext()) {
			int next = iter.next();
			HuffmanLeafNode leaf = new HuffmanLeafNode(next, m.get(next));
			data.add(leaf);
		}		
		condense();
	}
	
	public static class HuffmanNode implements Comparable {
		public int frequency;
		public HuffmanNode fst;
		public HuffmanNode snd;
		
		public HuffmanNode(int frequency) {
			this.frequency = frequency;
		}
		
		public HuffmanNode(HuffmanNode fst, HuffmanNode snd) {
			this.frequency = fst.frequency + snd.frequency;
			this.fst = fst;
			this.snd = snd;
		}
		
		public String toString() {
			return "[" + frequency + "]";
		}
		public HuffmanNode() { }
		@Override
		public int compareTo(Object o) {
			if (o instanceof HuffmanNode) {
				return frequency - ((HuffmanNode) o).frequency;
			} else {
				throw new IllegalArgumentException();
			}
		}
	}
	
	public static class HuffmanLeafNode extends HuffmanNode {
		public int character;
		
		public HuffmanLeafNode(int character, int frequency) {
			this.character = character;
			this.frequency = frequency;
		}
		public String toString() {
			return "[" + character + "," + frequency + "]";
		}
		
	}
	
	private void condense() {
		while (data.size() != 1){
			HuffmanNode fst = data.poll();
			HuffmanNode snd = data.poll();
			HuffmanNode node = new HuffmanNode(fst, snd);
			data.add(node);
			condense();
		}
		root = data.poll();
	}
	
	public void encode(List<Integer> characters, BitOutputStream stream) {
		Iterator<Integer> iter = characters.iterator();
		HashMap<Integer, Integer> freqMap = new HashMap<>();
		while (iter.hasNext()){
			int next = iter.next();
			if (freqMap.containsKey(next)){
				freqMap.replace(next, freqMap.get(next) + 1);
			} else {
				freqMap.put(next, 1);
			}
		}
		HuffmanTree ht = new HuffmanTree(freqMap);
		HuffmanNode node = ht.root;
		
	}
	
	public List decode(BitInputStream stream) {
		
	}
	
	
	public static void main(String[] args) {
		Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();
		freqMap.put((int) 'z', 1);
		freqMap.put((int) ' ', 2);
		freqMap.put((int) 'b', 2);
		freqMap.put((int) 'a', 3);
		/*HuffmanPriorityQueue pq = new HuffmanPriorityQueue(2*freqMap.size() + 1);
		pq.addFromMap(freqMap);
		pq.condense();
		System.out.println(pq.toString());*/
		HuffmanTree ht = new HuffmanTree(freqMap);
		
		
	}
}
