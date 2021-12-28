package code;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class QueahBoard extends JPanel {

    //img
    private static final String FREE="src/img/free.png";


    private static int startPlayer=1; 

    private static int sizeOfboard=5;
    private static int heightOfboard=2;
    private int turn;

    private GameButton [][]gBoard;  //graphic board
	private int [][]lBoard;       //logic board, 0 free 1 red player  2 black Player

    private Soldier redSoldier = new Soldier("red");
    private Soldier blackSoldier = new Soldier("black");

    private Players playerRed;
    private Players playerBlack;

    public QueahBoard(Players playerRed,Players playerBlack,int sizeOfboard,int heightOfboard) {
        this.playerRed = playerRed;
        this.playerBlack = playerBlack;
        QueahBoard.sizeOfboard = sizeOfboard;
        QueahBoard.heightOfboard = heightOfboard;
        initBoard();
    }

    public void initBoard()
	{
		gBoard = new GameButton[sizeOfboard][sizeOfboard];
		lBoard = new int[sizeOfboard][sizeOfboard];
		
		setLayout(new GridLayout(sizeOfboard,sizeOfboard));
        //up
        for (int row = 0; row < heightOfboard; row ++) {
            for (int column = 0; column < sizeOfboard; column ++) {

                ImageIcon icon = new ImageIcon(FREE);
				Image img = icon.getImage();

                if(Math.abs(column - heightOfboard) <= row){
                    lBoard[row][column]=0;
                    gBoard[row][column]= new GameButton(img);
                    //gBoard[row][column].addActionListener(new AL(playerRed.getPlayer_color()));
                }
                else{
                    lBoard[row][column]=-1;
                    gBoard[row][column] = new GameButton();
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
                gBoard[row][column]= new GameButton(img);
                //gBoard[row][column].addActionListener(new AL(row,column));
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
                    gBoard[row][column]= new GameButton(img);
                    //gBoard[row][column].addActionListener(new AL(row,column));
                }
                else{
                    lBoard[row][column]=-1;
                    gBoard[row][column] = new GameButton();
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

    class AL implements ActionListener{
        private int player;
        public AL(int player){
            this.player = player;
        }

        public void actionPerformed(ActionEvent e){
            if(player == 1){
                playerRed.removeSoldier();
            }
            else
            {
                playerBlack.removeSoldier();
            }
            
        }
    }

    public int[][] getlBoard(){
        return lBoard;
    }

    public void setlBoaed(int[][] lBoard){
        this.lBoard=lBoard;
    }

    public GameButton [][]gBoard(){
        return gBoard;
    }

}
