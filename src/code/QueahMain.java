package code;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class QueahMain extends JFrame {

    //img
    private static final String FREE="src/img/free.gif";


    private static int startPlayer=1; 

    private static int sizeOfboard=5;
    private static int heightOfboard=2;
    private int turn;

    private MyButton [][] gBoard;  //graphic board
	private int [][] lBoard;       //logic board, 0 free 1 red player  2 black Player

    public QueahMain() {

        initBoard();

        setTitle("QueahMain");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,300);
		setVisible(true);
    }

    public void initBoard()
	{
		gBoard = new MyButton[sizeOfboard][sizeOfboard];
		lBoard = new int[sizeOfboard][sizeOfboard];
		
		setLayout(new GridLayout(sizeOfboard,sizeOfboard));
		
        //up
        for (int row = 0; row < heightOfboard; row ++) {
            for (int column = 0; column < sizeOfboard; column ++) {

                ImageIcon icon = new ImageIcon(FREE);
				Image img = icon.getImage();

                if(Math.abs(column - heightOfboard) <= row){
                    lBoard[row][column]=0;
                    gBoard[row][column]= new MyButton(img);
                }
                else{
                    lBoard[row][column]=-1;
                    gBoard[row][column] = new MyButton();
                    gBoard[row][column].setEnabled(false);
                    gBoard[row][column].setBackground(Color.BLACK);
                }
            }
            for (int column = 0; column < sizeOfboard; column ++) {
                add(gBoard[row][column]);
            }
        }
        //mid
        for( int row=heightOfboard; row<(sizeOfboard-heightOfboard); row++){
            for(int column=0; column<sizeOfboard; column++){
                ImageIcon icon = new ImageIcon(FREE);
				Image img = icon.getImage();

                lBoard[row][column]=0;
                gBoard[row][column]= new MyButton(img);
                add(gBoard[row][column]);
            }
        }
        //down
        for (int row = sizeOfboard-heightOfboard,k=heightOfboard-1; row < sizeOfboard; row ++,k--) {
            for (int column = 0; column < sizeOfboard; column ++) {

                ImageIcon icon = new ImageIcon(FREE);
				Image img = icon.getImage();

                if((lBoard[k][column]==0)){
                    lBoard[row][column]=0;
                    gBoard[row][column]= new MyButton(img);
                }
                else{
                    lBoard[row][column]=-1;
                    gBoard[row][column] = new MyButton();
                    gBoard[row][column].setEnabled(false);
                    gBoard[row][column].setBackground(Color.BLACK);
                }
            }
            for (int column = 0; column < sizeOfboard; column ++) {
                add(gBoard[row][column]);
            }
        }		
		turn=startPlayer;		
	}


    public static void main(String[] args)
    {
        new QueahMain();

        
    }
}
