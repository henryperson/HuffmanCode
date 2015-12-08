import java.util.Iterator;
import java.util.Map;

public class HuffmanPriorityQueue {
	private HuffmanNode[] data;
	private int size;
	
	public class HuffmanNode {
		public int frequency;
		public HuffmanNode(int frequency) {
			this.frequency = frequency;
		}
		public String toString() {
			return "[" + frequency + "]";
		}
		public HuffmanNode() { }
	}
	
	public class HuffmanLeafNode extends HuffmanNode {
		public int character;
		public int frequency;
		
		public HuffmanLeafNode(int character, int frequency) {
			this.character = character;
			this.frequency = frequency;
		}
		public String toString() {
			return "[" + character + "," + frequency + "]";
		}
		
	}
	
	public HuffmanPriorityQueue(int size) {
		this.size = 0;
		data = new HuffmanNode[size];
	}
	
	private void swap(int i, int j) {
		HuffmanNode temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	
	private int firstChildOf(int i) {
		return (int) i * 2 + 1;
	}
	
	private int secondChildOf(int i) {
		return (int) i * 2 + 2;
	}
	
	private void sort(int i) {
		while (i < data.length - 1 && data[i].frequency < data[i + 1].frequency) {
			swap(i + 1, i);
			i++;
		}
	}
	
	public void add(int character, int frequency) {
		size++;
		data[data.length - size] = new HuffmanLeafNode(character, frequency);
		sort(data.length - size);
	}
	
	
	public void addFromMap(Map<Integer, Integer> m) {
		int eof = 256;
		add(eof, 1);
		Iterator<Integer> i = m.keySet().iterator();
		while (i.hasNext()) {
			int next = i.next();
			add(next, m.get(next));
		}
	}
	
	public void condense() {
		for (int i = data.length - size; i >= 0; i--) {
			data[i] = merge(data[firstChildOf(i)], data[secondChildOf(i)]);
		}
	}
	
	private HuffmanNode merge(HuffmanNode first, HuffmanNode second) {
		size++;
		return new HuffmanNode(first.frequency + second.frequency); 
	}
	
	public HuffmanNode[] getArray() {
		return data;
	}
	
	public String toString() {
		String ret = "";
		for (int i = 0; i < data.length; i++) {
			ret = ret + data[i].toString() + ", ";
		}
		return ret;
	}
}
