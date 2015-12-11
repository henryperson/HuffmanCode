import java.io.IOException;

public class Grin {

	public static void main(String[] args) throws IOException {
		
		
		String choice = args[0];
		String infile = args[1];
		String outfile = args[2];
		if (choice.equals("encode")){
			GrinEncoder.encode(infile, outfile);
		} else if (choice.equals("decode")){
			GrinDecoder.decode(infile, outfile);
		} else {
			System.out.println("Wrong input!");
		}
	}
}
