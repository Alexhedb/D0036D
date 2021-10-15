
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Objects;

public class ServerSocket {
    static DatagramSocket socket;
    static ColoredJPanels j=new ColoredJPanels();


    public static void main(String[] args) throws IOException{
       socket=new DatagramSocket(5300);
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
        System.out.println(rcvPkt);
        //display receive


        //retrive pkt info to send response to same sender
        InetAddress IP = rcvPkt.getAddress();
        int port = rcvPkt.getPort();

        //process data
        String temp = new String(rcvPkt.getData());
        temp = temp.toUpperCase();
        System.out.println(temp);
        for(int i=0;i<temp.length()-1;i++){
            System.out.println(temp.charAt(i));
        }



        int check=temp.length()-1;
        String move=new String();
        int colour=0;
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
                colour=Character.getNumericValue(c);
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
            if (colour==0 || colour==9){
                temp="Enter a valid colour (1-8)";
            }
            else {
                if(!j.check(colour,Integer.parseInt(x),Integer.parseInt(y))) {
                    temp = "Tile is already taken by another player!";
                }
                if(j.check(colour,Integer.parseInt(x),Integer.parseInt(y))){
                    j.PAINT(colour,Integer.parseInt(x),Integer.parseInt(y));
                    j.updatePAINT(colour);
                    temp = "color: " + colour + " \npainted at: x=" + x + " y=" + y;
                }
            }
        }else{
            temp="PLEASE FOLLOW THE PROTOCOL (COMMAND;COLOUR/TILE;X-CORD;Y-CORD;)".toUpperCase();
        }



        outServer = temp.getBytes();

        //send response packet to sender
        DatagramPacket sndPkt = new DatagramPacket(outServer, outServer.length, IP, port);
        socket.send(sndPkt);


    }
}