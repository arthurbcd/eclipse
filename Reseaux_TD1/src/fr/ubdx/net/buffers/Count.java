
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;

public class Count {
    private static final int BUFFER_SIZE = 1024; // Tamanho do buffer para receber a requisição

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Uso: Count <porta>");
            return;
        }
        // Dans le protocole Count, le serveur repond a ninporte quelle requete du client par
        // un paquet contenant un long en BigEndian qui donne le nombre de paquets reçus de ce client
        // depuis le demarrage du serveur.

        // Recuperar porta dos argumentos
        int port = Integer.parseInt(args[0]);

        // Memória do servidor: HashMap para clientes e total de pacotes por cliente
        HashMap<InetSocketAddress, Long> logan = new HashMap<>();

        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(port));
            System.out.println("Servidor escutando na porta " + port);

            ByteBuffer bb = ByteBuffer.allocate(Long.BYTES); // Buffer para receber a requisição (o conteúdo não importa, só o fato de receber algo)

            while (true) { // Loop infinito de serviço
                bb.clear();

                // Recebe dados (qualquer requisição) e armazena o endereço do cliente
                InetSocketAddress exp = (InetSocketAddress) dc.receive(bb);

                // No protocolo Count, cada datagrama recebido conta como 1 pacote
                long c = logan.getOrDefault(exp, 0L); // zero sur 8 octets (bytes)
                c += 1; // Incrementa o contador para este cliente (AQUI VAI MUDAR PRO EXAMEN)
                logan.put(exp, c);

                // Prepara resposta com apenas 1 long (8 bytes). ByteBuffer usa BigEndian por padrão.
                bb.clear();
                bb.putLong(c);

                bb.flip(); // Prepara para envio
                dc.send(bb, exp); // Envia resposta ao cliente
            }
        }
    }
}
