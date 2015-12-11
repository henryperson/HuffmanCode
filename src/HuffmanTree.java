import java.util.ArrayList;
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
		if (data.size() == 1) {
			root = data.poll();
		} else {
			HuffmanNode fst = data.poll();
			HuffmanNode snd = data.poll();
			HuffmanNode node = new HuffmanNode(fst, snd);
			data.add(node);
			condense();
		}
	}
	
	public void encode(List<Integer> characters, BitOutputStream stream) {
		HashMap<Integer, String> hufMap = new HashMap<>();
		String currentCode = "";
		encodeHelper(this.root, hufMap, currentCode, "");
		for (Integer ch : characters){
			System.out.println(ch);
			String hufCode = hufMap.get(ch);
			System.out.println(hufCode);
			for (int i = 0; i < hufCode.length(); i++){
				
				stream.writeBit(Character.getNumericValue(hufCode.charAt(i)));
			}
		}
	}
	
	private void encodeHelper(HuffmanNode root, HashMap<Integer, String> hufMap, String currentCode, String num){
		currentCode = currentCode + num;
		System.out.println(root);
		if (root instanceof HuffmanLeafNode){
			hufMap.put(((HuffmanLeafNode) root).character, currentCode);
			System.out.println(currentCode);
			return;
		}
		if (root.fst != null) {
			encodeHelper(root.fst, hufMap, currentCode, "0");
		}
		if (root.snd != null) {
			encodeHelper(root.snd, hufMap, currentCode, "1");
		}
	}
	
	public List<String> decode(BitInputStream stream) {
		List<String> list = new ArrayList<>();
		int characterValue = decodeHelper(this.root, stream);
		while (characterValue != -1) {
			char input = (char) characterValue;
			list.add(Character.toString(input));
		}
		return list;
	}
	
	private int decodeHelper(HuffmanNode node, BitInputStream stream) {
		if (node instanceof HuffmanLeafNode) {
			return ((HuffmanLeafNode) node).character;
		} else {
			int bit = stream.readBit();
			if (bit == -1){
				return -1;
			} else if (bit == 0) {
				return decodeHelper(node.fst, stream);
			} else if (bit == 1) {
				return decodeHelper(node.snd, stream);
			} else {
				throw new IllegalArgumentException();
			}
		}
	}
	
	
	public static void main(String[] args) {
		Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();
		freqMap.put((int) 'z', 1);
		freqMap.put((int) ' ', 2);
		freqMap.put((int) 'b', 2);
		freqMap.put((int) 'a', 3);
		HuffmanTree ht = new HuffmanTree(freqMap);
		System.out.println(ht.root.snd.fst.snd);
		
	}
}
