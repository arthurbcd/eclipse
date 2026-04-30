package fr.ubdx.net.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ServerUpperCaseUDP {

    private static final int MAX_PACKET_SIZE = 1024;

    public static void usage() {
        System.out.println("Usage : ServerUpperCaseUDP port charset");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            usage();
            return;
        }
        int port = Integer.parseInt(args[0]);
        if (port < 1024 || port > 65535) {
            System.out.println("The port number must be between 1024 and 65535");
            return;
        }

        String charsetName = args[1];
        Charset charset = Charset.forName(charsetName);

        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(port));
            ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE);

            while (true) {
                buffer.clear();
                InetSocketAddress sender = (InetSocketAddress) dc.receive(buffer);

                buffer.flip();

                // Decode incoming packet
                int inCharsetLen = buffer.getInt();

                int oldLimit = buffer.limit();
                buffer.limit(buffer.position() + inCharsetLen);
                String inCharsetName = StandardCharsets.US_ASCII.decode(buffer).toString();

                buffer.limit(oldLimit); // Restore limit for reading the payload
                String message = Charset.forName(inCharsetName).decode(buffer).toString();

                // Process String
                String upperMessage = message.toUpperCase();

                // Prepare response
                buffer.clear();
                ByteBuffer outCharsetNameBuffer = StandardCharsets.US_ASCII.encode(charsetName);
                ByteBuffer outMessageBuffer = charset.encode(upperMessage);

                buffer.putInt(outCharsetNameBuffer.remaining());
                buffer.put(outCharsetNameBuffer);
                buffer.put(outMessageBuffer);

                buffer.flip();

                // Send response
                dc.send(buffer, sender);
            }
        }
    }
}
