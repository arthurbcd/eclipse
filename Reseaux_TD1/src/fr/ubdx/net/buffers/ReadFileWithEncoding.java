package fr.ubdx.net.buffers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// javac fr/ubdx/net/buffers/ReadFileWithEncoding.java
public class ReadFileWithEncoding {

    private static void usage(){
        System.out.println("Usage: ReadFileWithEncoding charset filename");
    }


    public static void main(String[] args) throws IOException {
        if (args.length!=2){
            usage();
            return;
        }

		String charsetName = args[0]; // charset
		String fileName = args[1]; // nom fichier
		
		Path path = Paths.get(fileName); // le chemin pour lire le test.txt
		
		if(!Charset.isSupported(charsetName)) {
			System.out.println("charsetName not supported!");
			return;
		}
		
		Charset charset = Charset.forName(charsetName);
		
		try (
			FileChannel fc = FileChannel.open(path, StandardOpenOption.READ);
			){
			int bufferSize = (int) fc.size();
			ByteBuffer bb = ByteBuffer.allocate(bufferSize);
			
			// "Vous devrez l'appeler jusqu'à ce que buff soit plein"
			while (bb.hasRemaining()) {
				int octets = fc.read(bb);
				bb.flip(); // prepare pour lire
				
				System.out.print("octets : ");
				System.out.println(octets);
				
				CharBuffer decoded = charset.decode(bb);

				System.out.print("decoded.length : ");
				System.out.println(decoded.length());

				System.out.print("decoded.toString() : ");
				System.out.println(decoded);
				
			}
		}
    }


}
