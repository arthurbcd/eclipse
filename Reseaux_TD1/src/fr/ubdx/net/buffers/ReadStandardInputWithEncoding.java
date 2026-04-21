package fr.ubdx.net.buffers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

//javac fr/ubdx/net/buffers/ReadStandardInputWithEncoding.java
public class ReadStandardInputWithEncoding {


    private static void usage(){
        System.out.println("Usage: ReadStandardInputWithEncoding charset");
    }

    public static void main(String[] args) throws IOException {
        if (args.length!=1){
            usage();
            return;
        }
        
        String charsetName = args[0]; // charset
        
		if(!Charset.isSupported(charsetName)) {
			System.out.println("charsetName not supported!");
			return;
		}
		
		Charset charset = Charset.forName(charsetName);

        ReadableByteChannel in = Channels.newChannel(System.in);
        
        // "taille fixe initiale"
        int bufferSize = Long.BYTES;
        ByteBuffer bb = ByteBuffer.allocate(bufferSize);
        
        while (in.read(bb) != -1) {
        	if (bb.hasRemaining()) continue;
        	
        	bb.flip();
        	int newCapacity = bb.capacity()*2;
        	
        	// System.out.print("newCapacity : ");
        	// System.out.println(newCapacity);
        	
        	ByteBuffer nb = ByteBuffer.allocate(newCapacity);
        	nb.put(bb);
        	bb = nb;
        }
        
        bb.flip();
		CharBuffer decoded = charset.decode(bb);
		System.out.print(decoded);
    }


}