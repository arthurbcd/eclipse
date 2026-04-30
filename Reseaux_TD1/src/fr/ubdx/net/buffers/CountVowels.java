import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CountVowels {
    // Usamos um buffer de 1024 bytes pois o cliente vai enviar strings (tamanhos variados, ex: 3 ou 8 bytes/caracteres)
    private static final int BUFFER_SIZE = 1024; 

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Uso: CountVowels <porta>");
            return;
        }
        // No protocolo CountVowels, o servidor recebe uma string e conta quantas vogais ela possui.
        // Ele responde com:
        // 1 long em BigEndian: total de vogais recebidas deste cliente
        // 1 long em LittleEndian: total global de vogais recebidas de todos os clientes

        // Recuperar porta dos argumentos
        int port = Integer.parseInt(args[0]);

        // Memória do servidor: HashMap para clientes (logan) e contador global (nbTotal)
        HashMap<InetSocketAddress, Long> logan = new HashMap<>();
        long nbTotal = 0;

        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(port));
            System.out.println("Servidor escutando na porta " + port);

            // Buffer com tamanho suficiente para receber a string (ex: 1024 bytes)
            ByteBuffer bb = ByteBuffer.allocate(BUFFER_SIZE); 

            while (true) { // Loop infinito de serviço
                bb.clear();
                bb.order(ByteOrder.BIG_ENDIAN); // Reseta a ordem dos bytes pro padrão do Java

                // Recebe dados (string) e armazena o endereço do cliente
                InetSocketAddress exp = (InetSocketAddress) dc.receive(bb);

                // Prepara o buffer para leitura e decodifica a string usando UTF-8
                bb.flip();
                String message = StandardCharsets.UTF_8.decode(bb).toString().toLowerCase();

                // Conta as vogais na mensagem recebida
                long vowelsCount = 0;
                for (int i = 0; i < message.length(); i++) {
                    char ch = message.charAt(i);
                    if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                        vowelsCount++;
                    }
                }

                // Atualiza o contador deste cliente e o contador global
                long c = logan.getOrDefault(exp, 0L) + vowelsCount;
                logan.put(exp, c);
                nbTotal += vowelsCount;

                // Reutilizando o buffer bb para a resposta (apagamos e voltamos a escrever nele)
                bb.clear();

                // Primeiro Long: BigEndian (Padrão do Java)
                bb.order(ByteOrder.BIG_ENDIAN);
                bb.putLong(c);

                // Segundo Long: LittleEndian
                bb.order(ByteOrder.LITTLE_ENDIAN);
                bb.putLong(nbTotal);

                bb.flip(); // Prepara para envio (limita aos 16 bytes que acabamos de escrever)
                dc.send(bb, exp); // Envia resposta ao cliente
            }
        }
    }
}
