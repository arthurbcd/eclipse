import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientUDPLongSum {

	public static void main(String[] args) throws IOException {
		String ip = args[0];
		Integer port_num = Integer.parseInt(args[1]);
		InetSocketAddress dest = new InetSocketAddress(ip, port_num);

		try (DatagramChannel dc = DatagramChannel.open(); Scanner scan = new Scanner(System.in);) {
			dc.bind(null);// se connecte au premier port disponible

			ByteBuffer bb = ByteBuffer.allocate(1 + 4 * Long.BYTES);

			Long idSession = 0L;
			while (scan.hasNextLong()) {
				idSession++;
				Long nbOps = scan.nextLong();
				if (nbOps > 0) {
					long i = 0L;
					while (i < nbOps) {
						if (scan.hasNextLong()) {
							Long op = scan.nextLong();
							bb.put((byte) 1);
							bb.putLong(idSession);
							bb.putLong(i);
							bb.putLong(nbOps);
							bb.putLong(op);
							bb.flip();
							dc.send(bb, dest);
							bb.clear();
							dc.receive(bb);
							bb.flip();
							if (bb.get() == 2) {// ACK
								Long idSessionServ = bb.getLong();
								Long posServ = bb.getLong();
								if (idSessionServ == idSession && i == posServ) {
									i++;// On passe à l'opérateur suivant
								}
							}
							bb.clear();
						}
					}
					boolean result = false;
					while (!result) {
						dc.receive(bb);
						bb.flip();
						if (bb.get() == 3) {// SUM
							Long idSessionServ = bb.getLong();
							Long sum = bb.getLong();
							if (idSessionServ == idSession) {
								System.out.println("Sum:" + sum);
								result = true;
							}
						}
						bb.clear();
					}

				}

			}
		}

	}

}
