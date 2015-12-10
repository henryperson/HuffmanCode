import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GrinEncoder {
	public static Map<Integer, Integer> createFrequencyMap(String file) throws IOException {
		Map<Integer, Integer> map = new HashMap<>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		boolean empty = false;
		while (!empty) {
			int ch = reader.read();
			
			if (ch == -1) {
				empty = true;
			} else {
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
	
	public static void encode(String infile, String outfile) throws FileNotFoundException {
		Map<Integer, Integer> map = createFrequencyMap(infile);
		BitOutputStream bitStream = new BitOutputStream(outfile);
		writeHeader(bitStream);
	}
	
	public static void writeHeader(BitOutputStream bitStream, Map<Integer, Integer> map) {
		bitStream.writeBits(1846, 32);
		// TODO: finish this
	}
}
