import java.awt.*;
import java.io.IOException;
import java.net.DatagramSocket;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ColoredJPanels extends JFrame {
    private static int[][] colarr = new int[201][201];
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

    public void PAINT(int color, int x, int y){
        switch (color){
            case 1:
                colarr[x][y] = 1;
                break;
            case 2:
                colarr[x][y] = 2;
                break;
            case 3:
                colarr[x][y] = 3;
                break;
            case 4:
                colarr[x][y] = 4;
                break;
            case 5:
                colarr[x][y] = 5;
                break;
            case 6:
                colarr[x][y] = 6;
                break;
            case 7:
                colarr[x][y] = 7;
                break;
            case 8:
                colarr[x][y] = 8;
                break;
        }
    }
    public void updatePAINT(int col) {
        for (int i = 0; i <cols ; i++)
            for (int j = 0; j <rows; j++) {
                switch(colarr[i][j]) {
                    case 1:
                        grid[i][j].setBackground(Color.red);
                        break;
                    case 2:
                        grid[i][j].setBackground(Color.cyan);
                        break;
                    case 3:
                        grid[i][j].setBackground(Color.yellow);
                        break;
                    case 4:
                        grid[i][j].setBackground(Color.blue);
                        break;
                    case 5:
                        grid[i][j].setBackground(Color.green);
                        break;
                    case 6:
                        grid[i][j].setBackground(Color.black);
                        break;
                    case 7:
                        grid[i][j].setBackground(Color.pink);
                        break;
                    case 8:
                        grid[i][j].setBackground(Color.darkGray);
                        break;
                }
            }
        this.repaint();
    }
    public boolean check(int c, int x, int y){
        if(colarr[x][y]==0) {
            return true;
        }
        return false;
    }
}

