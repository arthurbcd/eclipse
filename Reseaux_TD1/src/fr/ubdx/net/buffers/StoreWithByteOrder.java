package fr.ubdx.net.buffers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

// javac fr/ubdx/net/buffers/StoreWithByteOrder.java
// java fr.ubdx.net.buffers.StoreWithByteOrder BE long-be.bin
// java fr.ubdx.net.buffers.StoreWithByteOrder LE long-le.bin
// https://moodle.caseine.org/mod/vpl/view.php?id=59289
public class StoreWithByteOrder {

	public static void usage() {
		System.out.println("USAGE : StoreWithByteOrder [LE|BE] filename");
		System.out.println("\t then provide long numbers separated by new lines");
		System.out.println("\t end using CTRL+D");
	}

	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) {
			usage();
			return;
		}
		
		String order = args[0]; // LE, BE
		String fileName = args[1]; // nom fichier
		
		ByteOrder byteOrder = order.equals("LE") ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
		System.out.println(byteOrder);
		
		Path path = Paths.get(fileName); // le chemin pour sauvagarder
		
		try (
				FileChannel fc = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
				Scanner scanner = new Scanner(System.in);
				){
			ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
			
			// config le ordre recu par args[0]
			bb.order(byteOrder);
			
			
			while (scanner.hasNextLong()) {
				long next = scanner.nextLong();
				bb.putLong(next);
				bb.flip(); // prepare pour lire
				fc.write(bb); // lire et ecrire
				bb.clear();	// min 0 et max a la limite			
			}
		}
		
	}
}