package fr.ubdx.net.buffers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class NetcatUDP {
    private static final int BUFFER_SIZE = 1024;

    private static void usage(){
        System.out.println("Usage : NetcatUDP host port charset");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            usage();
            return;
        }
        
		String hostName = args[0]; // nom "addresse destination"
		int port = Integer.parseInt(args[1]); // num porte
		String charsetName = args[2]; // nom charset

		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE); // le papier
        InetSocketAddress serverAddress = new InetSocketAddress(hostName, port); // bon de livraison
        Charset charset = Charset.forName(charsetName); // "langue" message

        try (DatagramChannel dc = DatagramChannel.open();
        	// dc.bind(null)
        		
        	// pour ecrire chaque lettre
            Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // mots

                // Envoi
                ByteBuffer outBuffer = charset.encode(line); // ecrire au papier
                dc.send(outBuffer, serverAddress); // expedition

                // Réception
                buffer.clear(); // repositionne la main au debut du papier pour pouvoir ecrire jusq a la fin
                InetSocketAddress sender = (InetSocketAddress) dc.receive(buffer); // reception
                buffer.flip(); // repositionne les yeaux au debut pour ne lire q ce q a ete ecrit

                System.out.println("Received " + buffer.remaining() + " bytes from " + sender);
                System.out.println("String: " + charset.decode(buffer));
            }
        }
    }
}
