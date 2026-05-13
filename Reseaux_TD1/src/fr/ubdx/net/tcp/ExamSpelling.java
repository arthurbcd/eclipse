package fr.ubdx.net.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ExamSpelling {

    private static final int BUFFER_SIZE = 1024;
    private static int errTotal = 0;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: ExamSpelling port");
            return;
        }

        int port = Integer.parseInt(args[0]);
        HashMap<InetSocketAddress, Integer> mem = new HashMap<>();
        Charset charset = StandardCharsets.UTF_8;

        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(port));
            System.out.println("Server started on port " + port);

            ByteBuffer bb = ByteBuffer.allocate(BUFFER_SIZE);

            while (true) {
                bb.clear();
                InetSocketAddress exp = (InetSocketAddress) dc.receive(bb);
                bb.flip();

                String msn = charset.decode(bb).toString();
                int err = 0;
                int cpt = 0;

                while (cpt < msn.length() - 1) {
                    if (msn.charAt(cpt) == 'é' && msn.charAt(cpt + 1) == 'x') {
                        err++;
                    }
                    cpt++;
                }

                errTotal += err;
                int errThisClient = err + mem.getOrDefault(exp, 0);
                mem.put(exp, errThisClient);

                ByteBuffer bbSend = ByteBuffer.allocate(3 * Integer.BYTES);
                bbSend.putInt(err);
                bbSend.putInt(errThisClient);
                bbSend.putInt(errTotal);

                bbSend.flip();
                dc.send(bbSend, exp);
            }
        }
    }
}
