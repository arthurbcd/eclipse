
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ServerUDPStateful {
    private static final int BUFFER_SIZE = 1024; // Tamanho sugerido nas fontes [3]

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Uso: ServerUDPStateful <porta>");
            return;
        }

        // Recuperar porta dos argumentos [4, 5]
        int port = Integer.parseInt(args);

        // Memória do servidor: HashMap para clientes e contador global [2, 6, 7]
        HashMap<InetSocketAddress, Long> clientStats = new HashMap<>();
        long globalTotal = 0;

        // Try-with-resources para garantir o fechamento do canal [8]
        try (DatagramChannel dc = DatagramChannel.open()) {
            // Bind obrigatório para o servidor ser encontrável [5, 9]
            dc.bind(new InetSocketAddress(port));
            System.out.println("Servidor escutando na porta " + port);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (true) { // Loop infinito de serviço [10, 11]
                buffer.clear(); // Prepara buffer para nova recepção [12, 13]

                // Recebe dados e armazena o endereço do cliente [10, 14]
                InetSocketAddress exp = (InetSocketAddress) dc.receive(buffer);

                buffer.flip(); // Prepara para leitura dos dados recebidos [15, 16]

                // Processamento dos dados (Ex: contar 'X') [1, 2]
                String message = StandardCharsets.UTF_8.decode(buffer).toString();
                long currentCount = message.chars().filter(ch -> ch == 'X').count();

                // Atualiza estatísticas (Memória do servidor) [2]
                long clientTotal = clientStats.getOrDefault(exp, 0L) + currentCount;
                clientStats.put(exp, clientTotal);
                globalTotal += currentCount;

                // Prepara resposta com 3 longs (24 bytes) [1, 2]
                ByteBuffer response = ByteBuffer.allocate(24);
                response.putLong(currentCount);
                response.putLong(clientTotal);
                response.putLong(globalTotal);

                response.flip(); // Prepara para envio [15]
                dc.send(response, exp); // Envia resposta ao cliente [10, 14]
            }
        }
    }
}
