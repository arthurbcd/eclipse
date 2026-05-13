package fr.ubdx.net.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class ServerEchoTCP {
    private static final Logger logger = Logger.getLogger(ServerEchoTCP.class.getName());

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: ServerEchoTCP port");
            return;
        }

        int port = Integer.parseInt(args[0]);

        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(port));
            System.out.println("ServerEchoTCP started on port " + port);

            while (true) {
                try (SocketChannel client = ssc.accept()) {
                    System.out.println("Client connected: " + client.getRemoteAddress());
                    ByteBuffer bb = ByteBuffer.allocate(1);

                    while (client.read(bb) != -1) {
                            bb.flip();
                            client.write(bb);
                            bb.clear();
                        }
                    }
                    System.out.println("Client disconnected");
                } catch (IOException e) {
                    logger.warning("Error with client connection: " + e.getMessage());
                }
            }
        }
    }
}
