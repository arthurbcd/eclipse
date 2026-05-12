package fr.ubdx.net.buffers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
public class ClientTCP {

    private static void usage() {
        System.out.println("Usage : ClientTCP host port bbsize");
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
            String request = "GET / HTTP/1.1\r\nHost: " + host + "\r\n\r\n";
            ByteBuffer bbRequest = charset.encode(request);

            while (bbRequest.hasRemaining()) {
                sc.write(bbRequest);
            }
            sc.shutdownOutput();

            ByteBuffer bbResponse = ByteBuffer.allocate(bbsize);

            while (sc.read(bbResponse) != -1) {
                if (!bbResponse.hasRemaining()) {
                    ByteBuffer newBuffer = ByteBuffer.allocate(bbResponse.capacity() * 2);
                    bbResponse.flip();
                    newBuffer.put(bbResponse);
                    bbResponse = newBuffer;
                }
            }

            bbResponse.flip();
            System.out.println(charset.decode(bbResponse).toString());
        }
    }
}
