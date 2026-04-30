package fr.ubdx.net.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

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

        String csName = args[1];
        Charset cs = Charset.forName(csName);
        ByteBuffer bb = ByteBuffer.allocate(MAX_PACKET_SIZE);

        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(port));

            while (true) {
                bb.clear(); // ([...])
                InetSocketAddress exp = (InetSocketAddress) dc.receive(bb); // (len, charset, message[],)
                bb.flip(); // ([len,charset,message],)

                int oldLim = bb.limit();
                int len = bb.getInt(); // (len,[charset,message],)

                bb.limit(bb.position() + len); // (len,[charset],message,)
                String inCsName = Charset.forName("ASCII").decode(bb).toString(); // (len,charset[],message,)

                bb.limit(oldLim); // (len, charset,[message],)
                String msg = Charset.forName(inCsName).decode(bb).toString(); // (len,charset,message[],)

                ByteBuffer csNameBb = Charset.forName("ASCII").encode(csName);
                ByteBuffer msgBb = cs.encode(msg.toUpperCase());

                bb.clear(); // ([...])
                bb.putInt(csNameBb.remaining()); // (len,[...],)
                bb.put(csNameBb); // (len,charset,[...],)
                bb.put(msgBb); // (len,charset,message[],)
                bb.flip(); // ([len,charset,message],)

                dc.send(bb, exp); // (len,charset,message[],)
            }
        }
    }
}
