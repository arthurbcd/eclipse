package fr.ubdx.net.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerEchoNonBlocking {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: ServerEchoNonBlocking port");
            return;
        }

        int port = Integer.parseInt(args[0]);

        try (ServerSocketChannel ssc = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            ssc.bind(new InetSocketAddress(port));
            ssc.configureBlocking(false); // Configura o servidor como não-bloqueante

            // Registra o servidor no seletor para aceitar conexões (OP_ACCEPT)
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Non-blocking Server started on port " + port);

            while (true) {
                selector.select(); // Espera por eventos

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        // Aceita a nova conexão
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel sc = server.accept();
                        sc.configureBlocking(false);
                        // Registra o cliente para leitura (OP_READ)
                        sc.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        System.out.println("Client connected: " + sc.getRemoteAddress());
                    }

                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer bb = (ByteBuffer) key.attachment();

                        int bytesRead = sc.read(bb);
                        if (bytesRead == -1) {
                            System.out.println("Client disconnected");
                            sc.close();
                        } else if (bytesRead > 0) {
                            bb.flip();
                            while (bb.hasRemaining()) {
                                sc.write(bb);
                            }
                            bb.clear();
                        }
                    }

                    iter.remove(); // Remove a chave processada
                }
            }
        }
    }
}
