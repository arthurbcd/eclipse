package fr.ubdx.net.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ClientLongSum {
    public static void usage() {
        System.out.println("Usage : ClientLongSum host port");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            usage();
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        InetSocketAddress isa = new InetSocketAddress(host, port);

        try (SocketChannel sc = SocketChannel.open(isa);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("nbOperands?");

            while (scanner.hasNextInt()) {
                int nbOperands = scanner.nextInt();

                ByteBuffer bbRequest = ByteBuffer.allocate(Integer.BYTES + nbOperands * Long.BYTES);
                bbRequest.putInt(nbOperands);

                for (int i = 0; i < nbOperands; i++) {
                    System.out.println("Le " + (i + 1) + "º operand:");
                    bbRequest.putLong(scanner.nextLong());
                }

                bbRequest.flip();
                sc.write(bbRequest);

                ByteBuffer bbResponse = ByteBuffer.allocate(Long.BYTES);
                while (bbResponse.hasRemaining()) {
                    if (sc.read(bbResponse) == -1) {
                        System.out.println("Déconnexion du serveur");
                        return;
                    }
                }

                bbResponse.flip();
                System.out.println("------------------------------");
                System.out.println("Sum: " + bbResponse.getLong());
                System.out.println("------------------------------");
                System.out.println("\nnbOperands?");
            }
        }
    }
}
