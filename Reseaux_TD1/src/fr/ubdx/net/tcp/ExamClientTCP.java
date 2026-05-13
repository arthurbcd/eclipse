package fr.ubdx.net.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ExamClientTCP {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: ExamClientTCP host port");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        InetSocketAddress isa = new InetSocketAddress(host, port);
        Charset cs = Charset.forName("utf8");

        try (SocketChannel sc = SocketChannel.open(isa);
             Scanner scanner = new Scanner(System.in)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.equals("")) {
                    sc.shutdownOutput();
                    ByteBuffer bbm = ByteBuffer.allocate(12);

                    while (bbm.hasRemaining()) {
                        if (sc.read(bbm) == -1) {
                            ByteBuffer newBuffer = ByteBuffer.allocate(bbm.capacity() * 2);
                            bbm.flip();
                            newBuffer.put(bbm);
                            bbm = newBuffer;
                        }
                    }
                bbm.flip();
                System.out.println(Charset.forName("ASCII").decode(bbm).toString());
                }



                // Encode usando ASCII conforme solicitado
                ByteBuffer bbSend = cs.encode(line);

                while (bbSend.hasRemaining()) {
                    sc.write(bbSend);
                }

                // Exemplo de leitura de resposta simples do servidor
                ByteBuffer bbResponse = ByteBuffer.allocate(1024);
                int bytesRead = sc.read(bbResponse);
                if (bytesRead == -1) {
                    System.out.println("Connection closed by server.");
                    break;
                }
                bbResponse.flip();
                System.out.println("Server response: " + StandardCharsets.UTF_8.decode(bbResponse));
            }
        }
    }
}
