import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrinDecoder {
	public static void decode(String infile, String outfile) throws IOException {
		BitInputStream bitStream = new BitInputStream(infile);
		
		int magicNumber = bitStream.readBits(32);
		if (magicNumber != 1846) { throw new IllegalArgumentException(); }
		
		int characters = bitStream.readBits(32);
		Map<Integer, Integer> map = createMap(bitStream, characters);	
		HuffmanTree hTree = new HuffmanTree(map);
		
		List<String> charList = hTree.decode(bitStream);
		writeToFile(charList, outfile);
	}
	
	public static Map<Integer, Integer> createMap(BitInputStream b, int characters) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < characters; i++) {
			int key = b.readBits(32);
			int value = b.readBits(32);
			map.put(key, value);
		}
		return map;
	}
	
	public static void writeToFile(List<String> charList, String outfile) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
		for (String ch : charList) {
			writer.write(ch);
		}
		writer.close();
	}
}
