import java.io.FileNotFoundException;

public class GrinDecoder {
	public static void decode(String infile, String outfile) throws FileNotFoundException {
		BitInputStream bitStream = new BitInputStream(infile);
		int magicNumber = bitStream.readBits(32);
		if (magicNumber != 1846) { return; }
		int characters = bitStream.readBits(32);
		
		
		
	}
}
