
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Objects;

public class ServerSocket {
    static DatagramSocket socket;
    static ColoredJPanels j=new ColoredJPanels();


    public static void main(String[] args) throws IOException{
       socket=new DatagramSocket(9876);
       j.createboard();
        while(true){
            repeat();
        }
    }
    public static void repeat() throws IOException {
        //pkt buffers
        byte[] inServer = new byte[1024];
        byte[] outServer = new byte[1024];

        //receive pkt
        DatagramPacket rcvPkt = new DatagramPacket(inServer,inServer.length);
        socket.receive(rcvPkt);
        //display receive
        System.out.println("Packet Received!");


        //retrive pkt info to send response to same sender
        InetAddress IP = rcvPkt.getAddress();
        int port = rcvPkt.getPort();

        //process data
        String temp = new String(rcvPkt.getData());
        temp = temp.toUpperCase();



        int check=temp.length()-1;
        String move=new String();
        String colour=new String();
        String x=new String();
        String y=new String();
        int start=0;
        int next=0;

        while(check>start){
            char c=temp.charAt(start);
            if(Objects.equals(c, ';')){
                next++;
                start++;
            }else if(next==0){
                move=move+c;
                start++;
            }else if(next==1){
                colour=colour+c;
                start++;
            }else if(next==2){
                x=x+c;
                start++;
            }else if(next==3){
                y=y+c;
                start++;
            }else if(next>3){
                start++;
            }
        }
        if(move.equals("PAINT") && Integer.parseInt(x)<ColoredJPanels.rows && Integer.parseInt(y)<ColoredJPanels.cols){
            j.PAINT(colour, x, y);
            j.updatePAINT(colour);
            temp="color: "+colour+" \npainted at: x="+x+" y="+y;
        }else if(move.equals("REMOVE") && Integer.parseInt(x)<ColoredJPanels.rows && Integer.parseInt(y)<ColoredJPanels.cols){
            j.ERASE(x,y);
            temp="Tile erased\n"+ "at: x="+x+" y="+y;
        }else if(move.equals("CHECK")){
            j.recarr();
        }else{
            temp="PLEASE FOLLOW THE PROTOCOL (COMMAND;COLOUR/TILE;X-CORD;Y-CORD;)".toUpperCase();
        }



        outServer = temp.getBytes();

        //send response packet to sender
        DatagramPacket sndPkt = new DatagramPacket(outServer, outServer.length, IP, port);
        socket.send(sndPkt);


    }
}