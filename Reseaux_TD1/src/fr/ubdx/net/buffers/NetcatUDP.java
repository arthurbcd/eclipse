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

        InetSocketAddress serverAddress = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
        Charset charset = Charset.forName(args[2]);
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        try (DatagramChannel dc = DatagramChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Envoi
                ByteBuffer outBuffer = charset.encode(line);
                dc.send(outBuffer, serverAddress);

                // Réception
                buffer.clear();
                InetSocketAddress sender = (InetSocketAddress) dc.receive(buffer);
                buffer.flip();

                System.out.println("Received " + buffer.remaining() + " bytes from " + sender);
                System.out.println("String: " + charset.decode(buffer));
            }
        }
    }
}
