package fr.ubdx.net.buffers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

        InetSocketAddress serverAddress = new InetSocketAddress(args[0], Integer.parseInt(args[1])); // bon de livraison
        String charsetName = args[2];
        Charset charset = Charset.forName(charsetName); // "langue" message
        ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE); // le papier

        try (DatagramChannel dc = DatagramChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // mots

                // Envoi
                buffer.clear(); // rafraichir le papier
                ByteBuffer charsetNameBuffer = Charset.forName("US-ASCII").encode(charsetName); // encodage du nom
                ByteBuffer contentBuffer = charset.encode(line); // ecrire le message

                if (4 + charsetNameBuffer.remaining() + contentBuffer.remaining() > MAX_PACKET_SIZE) {
                    System.err.println("Message too long");
                    continue;
                }

                buffer.putInt(charsetNameBuffer.remaining()); // taille du nom
                buffer.put(charsetNameBuffer); // le nom du charset
                buffer.put(contentBuffer); // le contenu
                buffer.flip(); // pret pour l'expedition
                dc.send(buffer, serverAddress);

                // Réception
                buffer.clear(); // metre la main au debut du papier pour ecrire la reponse
                dc.receive(buffer);
                buffer.flip(); // metre les yeaux au debut pour lire la reponse

                int resCharsetLen = buffer.getInt(); // lire la taille du nom recu
                // On utilise limit() pour ne pas dépasser la taille du nom du charset
                int oldLimit = buffer.limit();
                buffer.limit(buffer.position() + resCharsetLen);
                Charset resCharset = Charset.forName("US-ASCII").decode(buffer); // nouvelle langue

                buffer.limit(oldLimit); // On remet la limite pour lire le reste
                System.out.println(resCharset.decode(buffer).toString()); // lecture finale
            }
        }
    }
}
