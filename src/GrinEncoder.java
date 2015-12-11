import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrinEncoder {
	public static Map<Integer, Integer> createFrequencyMap(String file, 
			List<Integer> charList) throws IOException {
		Map<Integer, Integer> map = new HashMap<>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		boolean empty = false;
		while (!empty) {
			int ch = reader.read();
			
			if (ch == -1) {
				empty = true;
			} else {
				//System.out.println(ch);
				charList.add(ch);
				if (map.containsKey(ch)) {
					map.put(ch, map.get(ch) + 1);
				} else {
					map.put(ch, 1);
				}
			}
		}
		reader.close();
		return map;
	}
	
	public static void encode(String infile, String outfile) throws IOException {
		List<Integer> charList = new ArrayList<Integer>();
		Map<Integer, Integer> map = createFrequencyMap(infile, charList);
		System.out.println("map size: " + map.size());
		Iterator<Integer> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			System.out.println("in freq map: " + Character.toChars(iter.next())[0]);
		}
		BitOutputStream bitStream = new BitOutputStream(outfile);
		writeHeader(bitStream, map);
		HuffmanTree ht = new HuffmanTree(map);
		ht.encode(charList, bitStream);
	}
	
	public static void writeHeader(BitOutputStream bitStream, Map<Integer, Integer> map) {
		bitStream.writeBits(1846, 32);
		bitStream.writeBits(map.size(), 32);
		Set<Integer> keys = map.keySet();
		for (Integer key : keys) {
			bitStream.writeBits(key, 32);
			bitStream.writeBits(map.get(key), 32);
		}
	}
}
