import java.awt.*;
import java.io.IOException;
import java.net.DatagramSocket;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ColoredJPanels extends JFrame {
    private static Colours[][] colarr = new Colours[201][201];
    JPanel pane = new JPanel();
    static int rows= 201;
    static int cols=201;
    JLabel[][] grid= new JLabel[rows][cols];
    Color old;

    public void createboard(){
        pane.setLayout(new GridLayout(rows,cols));

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                grid[i][j] = new JLabel();
                //grid[i][j].setBorder(new LineBorder(Color.black));
               // grid[i][j].setBackground(Color.black);
                grid[i][j].setOpaque(true);
                pane.add(grid[i][j]);
                old=grid[i][j].getBackground();
            }
        }
        this.setContentPane(pane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocation(100, 50);
        this.setVisible(true);
    }

    public void PAINT(String col, String x, String y){
            if(col.equals("RED")) {
                colarr[Integer.parseInt(x)][Integer.parseInt(y)] = Colours.RED;
            }
            else if(col.equals("CYAN")) {
                colarr[Integer.parseInt(x)][Integer.parseInt(y)] = Colours.CYAN;
             }
            else if(col.equals("YELLOW")) {
                colarr[Integer.parseInt(x)][Integer.parseInt(y)] = Colours.YELLOW;
            }
            else if(col.equals("BLUE")) {
                colarr[Integer.parseInt(x)][Integer.parseInt(y)] = Colours.BLUE;
            }
            else if(col.equals("GREEN")) {
                colarr[Integer.parseInt(x)][Integer.parseInt(y)] = Colours.GREEN;
            }
            else if(col.equals("BLACK")) {
                colarr[Integer.parseInt(x)][Integer.parseInt(y)] = Colours.BLACK;
            }
            else if(col.equals("PINK")) {
                colarr[Integer.parseInt(x)][Integer.parseInt(y)] = Colours.PINK;
            }
            else if(col.equals("DARK_GRAY")) {
                colarr[Integer.parseInt(x)][Integer.parseInt(y)] = Colours.DARK_GRAY;
            }
        }
    public void ERASE(String x, String y){
            colarr[Integer.parseInt(x)][Integer.parseInt(y)] = Colours.WHITE;
            grid[Integer.parseInt(x)][Integer.parseInt(y)].setBackground(old);
            this.repaint();
    }
    public void updatePAINT(String col) {
        for (int i = 0; i <cols ; i++)
            for (int j = 0; j <rows; j++) {
                if (colarr[i][j] == null) {
                } else if (colarr[i][j].equals(Colours.CYAN)) {
                    grid[i][j].setBackground(Color.cyan);
                } else if (colarr[i][j].equals(Colours.RED)) {
                    grid[i][j].setBackground(Color.red);
                } else if (colarr[i][j].equals(Colours.YELLOW)) {
                    grid[i][j].setBackground(Color.yellow);
                } else if (colarr[i][j].equals(Colours.BLUE)) {
                    grid[i][j].setBackground(Color.blue);
                } else if (colarr[i][j].equals(Colours.GREEN)) {
                    grid[i][j].setBackground(Color.green);
                } else if (colarr[i][j].equals(Colours.BLACK)) {
                    grid[i][j].setBackground(Color.black);
                } else if (colarr[i][j].equals(Colours.PINK)) {
                    grid[i][j].setBackground(Color.pink);
                } else if (colarr[i][j].equals(Colours.DARK_GRAY)) {
                    grid[i][j].setBackground(Color.darkGray);
                }
            }
        this.repaint();
    }
    public void recarr(){
        for (int i = 0; i <cols ; i++){
            System.out.println("-----------");
            for (int j = 0; j <rows; j++) {
                System.out.println(colarr[i][j]);
            }
    }
}
}