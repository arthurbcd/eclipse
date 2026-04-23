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

+ java -version
openjdk version "1.8.0_312"
OpenJDK Runtime Environment (build 1.8.0_312-8u312-b07-1~deb9u1-b07)
OpenJDK 64-Bit Server VM (build 25.312-b07, mixed mode)
+ javac fr/ubdx/net/udp/NetcatUDP.java
+ java -jar ServerEchoUDP.jar 7777
+ java -jar ServerUpperCaseUDP.jar 4545 UTF-8
+ java fr.ubdx.net.udp.NetcatUDP localhost 7777 UTF-8
Error: Could not find or load main class fr.ubdx.net.udp.NetcatUDP
+ java fr.ubdx.net.udp.NetcatUDP localhost 4545 UTF-8
Error: Could not find or load main class fr.ubdx.net.udp.NetcatUDP
+ java fr.ubdx.net.udp.NetcatUDP localhost 4545 latin1
Error: Could not find or load main class fr.ubdx.net.udp.NetcatUDP
connection closed
///
package fr.ubdx.net.udp;

import java.io.IOException;



public class NetcatUDP {


    private static void usage(){
        System.out.println("Usage : NetcatUDP host port charset");
    }

    public static void main(String[] args) throws IOException {
        if (args.length!=3){
            usage();
            return;
        }


    }
}

+ java -version
openjdk version "1.8.0_312"
OpenJDK Runtime Environment (build 1.8.0_312-8u312-b07-1~deb9u1-b07)
OpenJDK 64-Bit Server VM (build 25.312-b07, mixed mode)
+ javac fr/ubdx/net/udp/NetcatUDP.java
+ java -jar ServerEchoUDP.jar 7777
+ java -jar ServerUpperCaseUDP.jar 4545 UTF-8
+ java fr.ubdx.net.udp.NetcatUDP localhost 7777 UTF-8
+ java fr.ubdx.net.udp.NetcatUDP localhost 4545 UTF-8
+ java fr.ubdx.net.udp.NetcatUDP localhost 4545 latin1
connection closed
