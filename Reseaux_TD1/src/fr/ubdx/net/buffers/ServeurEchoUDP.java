package fr.ubdx.net.buffers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ServerEchoUDP {
    private static final int BUFFER_SIZE = 1024;

    private static void usage(){
        System.out.println("Usage : ServerEchoUDP host port charset");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            usage();
            return;
        }

		int port = Integer.parseInt(args[0]); // num porte

        try(DatagramChannel dc = DatagramChannel.open();) {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        InetSocketAddress isa = new InetSocketAddress(port);
        dc.bind(isa);

        while (true) {
            bb.clear();
            SocketAddress exp = dc.receive(bb);
            bb.flip(); // si on fait pas flip, on envoie un paquet vide ou des infos precédentes
            System.out.println("Received " + bb.remaining() + " bytes from " + exp);
            dc.send(bb, exp);
        }

        }
    }
}
