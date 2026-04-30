
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;

public class CountPaquet {
    private static final int BUFFER_SIZE = 1024; // Tamanho do buffer para receber a requisição

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Uso: CountPaquet <porta>");
            return;
        }
        // Dans le protocole Count, le serveur repond a ninporte quelle requete du client par
        // un paquet contenant un long un long en BigEndien qui donne le nombre de paquests recus de ce client suivi dun long en LittleEndian qui donne le nombre de paquets total reçus
        // depuis le demarrage du serveur.

        // Recuperar porta dos argumentos
        int port = Integer.parseInt(args[0]);

        // Memória do servidor: HashMap para clientes e contador global
        HashMap<InetSocketAddress, Long> logan = new HashMap<>();
        long nbTotal = 0;

        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(port));
            System.out.println("Servidor escutando na porta " + port);

            ByteBuffer bb = ByteBuffer.allocate(2 * Long.BYTES); // Buffer para receber e responder

            while (true) { // Loop infinito de serviço
                bb.clear();

                // Recebe dados (qualquer requisição) e armazena o endereço do cliente
                InetSocketAddress exp = (InetSocketAddress) dc.receive(bb);

                // No protocolo Count, cada datagrama recebido conta como 1 pacote
                long c = logan.getOrDefault(exp, 0L);
                c += 1; // Incrementa o contador para este cliente

                logan.put(exp, c);
                nbTotal++;

                // Reutilizando o buffer bb para a resposta
                bb.clear();

                // Primeiro Long: BigEndian (Padrão do Java)
                bb.order(ByteOrder.BIG_ENDIAN);
                bb.putLong(c);

                // Segundo Long: LittleEndian
                bb.order(ByteOrder.LITTLE_ENDIAN);
                bb.putLong(nbTotal);

                bb.flip(); // Prepara para envio
                dc.send(bb, exp); // Envia resposta ao cliente
                // fazemos sempre um só send para garantir que os dois longs estão no mesmo datagrama, o que é importante para o cliente interpretar corretamente a resposta
            }
        }
    }
}
