package fr.ubdx.net.buffers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ClientEOS {

    private static void usage() {
        System.out.println("Usage : ClientEOS host port bbsize");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            usage();
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        int bbsize = Integer.parseInt(args[2]);

        InetSocketAddress serverAddress = new InetSocketAddress(host, port);
        Charset charset = StandardCharsets.UTF_8;

        try (SocketChannel sc = SocketChannel.open(serverAddress)) {
            String req = "GET / HTTP/1.1\r\nHost: " + host + "\r\n\r\n";
            ByteBuffer contentBuffer = charset.encode(req);

            // Send request
            while (contentBuffer.hasRemaining()) {
                sc.write(contentBuffer);
            }
            sc.shutdownOutput();

            // Read response until the buffer is full or the server closes the connection
            // Part 1: "Si la réponse du serveur dépasse la taille du buffer, on ignore la fin de la réponse."
            ByteBuffer buffer = ByteBuffer.allocate(bbsize);

            while (buffer.hasRemaining() && sc.read(buffer) != -1) {
                // Keep reading until full or EOS
            }

            buffer.flip();
            System.out.println(charset.decode(buffer).toString());
        }
    }
}
