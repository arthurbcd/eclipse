package fr.ubdx.net.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;
import fr.ubdx.net.buffers.Calcul;

public class ServerLongSumUDP {

    public static void usage() {
        System.out.println("Usage : ServerLongSumUDP port");
    }

    private static final byte OP = 1;
    private static final byte ACK = 2;
    private static final byte RES = 3;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            usage();
            return;
        }
        int p = Integer.parseInt(args[0]);
        if (p < 1024 || p > 65535) {
            System.out.println("The port number must be between 1024 and 65535");
            return;
        }

        ByteBuffer bb = ByteBuffer.allocate(33);
        Map<InetSocketAddress, Map<Long, Calcul>> map = new HashMap<>();

        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(p));
            while (true) {
                bb.clear();
                InetSocketAddress isa = (InetSocketAddress) dc.receive(bb);
                bb.flip();

                if (bb.hasRemaining() && bb.get() == OP) { // (type=1,[sessionID,posOper,totalOper,opValue])
                    long sessionID = bb.getLong(); // (type=1,sessionID,[posOper,totalOper,opValue])
                    long idPosOper = bb.getLong(); // (type=1,sessionID,posOper,[totalOper,opValue])
                    long totalOper = bb.getLong(); // (type=1,sessionID,posOper,totalOper,[opValue])
                    long opValue = bb.getLong(); // (type=1,sessionID,posOper,totalOper,opValue[])

                    Map<Long, Calcul> cm = map.computeIfAbsent(isa, k -> new HashMap<>()); // ??=
                    Calcul calc = cm.computeIfAbsent(sessionID, k ->  new Calcul(totalOper));

                    calc.setOperands(idPosOper, opValue);

                    bb.clear();
                    bb.put(ACK);
                    bb.putLong(sessionID);
                    bb.putLong(idPosOper);
                    bb.flip();
                    dc.send(bb, isa);

                    if (calc.isComplete()) {
                        long sum = calc.compute();
                        bb.clear();
                        bb.put(RES);
                        bb.putLong(sessionID);
                        bb.putLong(sum);
                        bb.flip();
                        dc.send(bb, isa);
                    }
                }
            }
        }
    }
}
