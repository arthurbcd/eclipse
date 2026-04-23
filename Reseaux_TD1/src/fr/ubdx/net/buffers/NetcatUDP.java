package fr.ubdx.net.buffers;

import java.io.IOException;



public class NetcatUDP {


    private static void usage(){
        System.out.println("Usage : NetcatUDP host port charset");
    }

    public static void main(String[] args) throws IOException {
        if (args.length!=3){
            usage();
            return;
        }


    }
}
