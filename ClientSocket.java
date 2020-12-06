


import java.io.*;
import java.net.*;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ClientSocket {

    public static void main(String[] args) throws IOException{
        start();
    }
    public static void start() throws IOException {
        //get input from user
        BufferedReader user_in = new BufferedReader(
                new InputStreamReader(System.in));

        //create udp socket connection
        DatagramSocket socket = new DatagramSocket();

        //creat buffers to process data
        byte[] inData = new byte[1024];
        byte[] outData = new byte[1024];

        //get ip destination wanted
        InetAddress IP = InetAddress.getByName("localhost");

        //read data from user
        System.out.println("Enter the move and Data to send to server: ");
        outData = user_in.readLine().getBytes();


        /*
         * make pkts for interaction
         */

        //send pkts
        DatagramPacket sendPkt = new DatagramPacket(outData, outData.length, IP, 9876);
        socket.send(sendPkt);

        //receive pkts
        DatagramPacket recievePkt = new DatagramPacket(inData, inData.length);
        socket.receive(recievePkt);

        String temp = new String(recievePkt.getData());
        temp = temp.toUpperCase();
        System.out.println(temp);
        start();
    }
}