package fr.ubdx.net.buffers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ClientBetterUpperCaseUDP {
    private static final int MAX_PACKET_SIZE = 1024;

    private static void usage() {
        System.out.println("Usage : ClientBetterUpperCaseUDP host port charset");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            usage();
            return;
        }

        InetSocketAddress serverAddress = new InetSocketAddress(args[0], Integer.parseInt(args[1])); // destination
        String charsetName = args[2];
        Charset charset = Charset.forName(charsetName); // langue
        ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE); // papier

        try (DatagramChannel dc = DatagramChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // lire clavier

                // envoi
                buffer.clear(); // prep ecrire
                ByteBuffer charsetNameBuffer = Charset.forName("ASCII").encode(charsetName); // nom charset
                ByteBuffer contentBuffer = charset.encode(line); // msg pure

                // ca passe?
                if (Integer.BYTES + charsetNameBuffer.remaining() + contentBuffer.remaining() > MAX_PACKET_SIZE) {
                    System.err.println("trop long");
                    continue;
                }

                buffer.putInt(charsetNameBuffer.remaining()); // taille nom
                buffer.put(charsetNameBuffer); // le nome
                buffer.put(contentBuffer); // la msg
                buffer.flip(); // prep lire
                dc.send(buffer, serverAddress);

                // reception
                buffer.clear(); // prep a recevoir
                dc.receive(buffer);
                buffer.flip(); // prep a lire

                int resCharsetLen = buffer.getInt(); // taille charset & positionne

                int oldLimit = buffer.limit();
                buffer.limit(buffer.position() + resCharsetLen); // limite pour lire q le nom charset
                String resCharsetName = Charset.forName("ASCII").decode(buffer).toString(); // quelle langue

                buffer.limit(oldLimit); // reste du buffer pour lire le reste (message)
                String message = Charset.forName(resCharsetName).decode(buffer).toString(); // affiche reponse

                System.out.println("Serveur: " + message);
            }
        }
    }
}
