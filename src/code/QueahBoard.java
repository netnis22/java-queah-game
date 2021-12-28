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
    private static int max_Player_soldiers_on_board=4;
    private int turn;

    private GameButton [][]gBoard;  //graphic board
	private int [][]lBoard;       //logic board, 0 free 1 red player  2 black Player

    private Soldier redSoldier = new Soldier("red");
    private Soldier blackSoldier = new Soldier("black");

    private Players playerRed;
    private Players playerBlack;

    private Game game;

    public QueahBoard(Players playerRed,Players playerBlack,String map,Game game) {
        this.playerRed = playerRed;
        this.playerBlack = playerBlack;
        this.game= game;
        if(map.equals("small")){
            QueahBoard.sizeOfboard = 5;
            QueahBoard.heightOfboard = 2;
        }
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
                    gBoard[row][column]= new GameButton(img,null);
                    gBoard[row][column].addActionListener(new AL(row,column));
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
                gBoard[row][column]= new GameButton(img,null);
                gBoard[row][column].addActionListener(new AL(row,column));
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
                    gBoard[row][column]= new GameButton(img,null);
                    gBoard[row][column].addActionListener(new AL(row,column));
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

    public void smallMapSolid(){
        max_Player_soldiers_on_board=4;

        gBoard[0][2].setSoldier(blackSoldier);
        lBoard[0][2] = 2;
        gBoard[1][2].setSoldier(blackSoldier);
        lBoard[1][2] = 2;
        gBoard[1][3].setSoldier(blackSoldier);
        lBoard[1][3] = 2;
        gBoard[2][3].setSoldier(blackSoldier);
        lBoard[2][3] = 2;

        gBoard[4][2].setSoldier(redSoldier);
        lBoard[4][2] = 1;
        gBoard[3][2].setSoldier(redSoldier);
        lBoard[3][2] = 1;
        gBoard[3][1].setSoldier(redSoldier);
        lBoard[3][1] = 1;
        gBoard[2][1].setSoldier(redSoldier);
        lBoard[2][1] = 1;
    }

    class AL implements ActionListener{
        private int row,column;
        private static int previsRow,previsColumn;

        private static boolean isSoldiersEaten = false;
        private static boolean previsButtonPressed = false;

        public AL(int row , int column){
            this.row=row;
            this.column=column;
        }

        public void moveSoldier(){
            lBoard[row][column]=lBoard[previsRow][previsColumn];
            lBoard[previsRow][previsColumn] = 0;
            gBoard[row][column].setSoldier(gBoard[previsRow][previsColumn].getSoldier());
            gBoard[previsRow][previsColumn].setSoldier(null);
            repaint();
            if(turn==1) turn=2;
            else turn=1;
        }

        public void victory(int player){
            String playerColor;
            if(player==1) playerColor ="red";
            else playerColor = "black";

            javax.swing.JOptionPane.showMessageDialog(game,playerColor);
            game.dispose();
            new Game();
        }

        public void removeSoldier(int row, int column){
            boolean isRemoveSoldierFromStack;
            if(turn==1){
                isRemoveSoldierFromStack = playerBlack.removeSoldierFromStack();
                playerBlack.removeSoldierFromBoard();
                if(!isRemoveSoldierFromStack)
                {
                    lBoard[row][column] = 0;
                    gBoard[row][column].setSoldier(null);
                }
                else
                {
                    lBoard[row][column] = 0;
                    gBoard[row][column].setSoldier(null);
                    isSoldiersEaten = true;
                }
            }
            else
            {
                isRemoveSoldierFromStack = playerRed.removeSoldierFromStack();
                playerRed.removeSoldierFromBoard();
                if(!isRemoveSoldierFromStack)
                {
                    lBoard[row][column] = 0;
                    gBoard[row][column].setSoldier(null);
                }
                else
                {
                    lBoard[row][column] = 0;
                    gBoard[row][column].setSoldier(null);
                    isSoldiersEaten = true;
                }
            }
        }

        public void addSoldierToBoard(){
            if(turn==1){
                playerRed.addSoldierToBoard(max_Player_soldiers_on_board);
                if(lBoard[row][column]==0){
                    lBoard[row][column]=1;
                    gBoard[row][column].setSoldier(redSoldier);
                    isSoldiersEaten=false;
                    turn=2;
                }
            }
            else
            {
                playerBlack.addSoldierToBoard(max_Player_soldiers_on_board);
                if(lBoard[row][column]==0){
                    lBoard[row][column]=2;
                    gBoard[row][column].setSoldier(blackSoldier);
                    isSoldiersEaten=false;
                    turn=1;
                }
            }
        }

        public void actionPerformed(ActionEvent e){
            if(playerRed.getSoldier_on_board() == 0) victory(2);
            if(playerBlack.getSoldier_on_board() == 0) victory(1);

            if(isSoldiersEaten) addSoldierToBoard();
            else if(previsButtonPressed){
                if(((previsRow == row+1 || previsRow == row-1) && previsColumn == column) || ((previsColumn == column+1 || previsColumn == column-1) && previsRow == row )){
                    if(lBoard[row][column] == 0 && lBoard[previsRow][previsColumn] == turn){
                        if(!(previsRow == row && previsColumn == column)){
                            moveSoldier();
                        }
                    }
                }

                if(lBoard[row][column] == 0 && lBoard[previsRow][previsColumn] == turn){
                    if(!(previsRow == row && previsColumn == column)){
                        if(previsColumn == column){
                            if((previsRow == row+2 && (lBoard[row+1][column] !=turn && lBoard[row+1][column] !=0 ))) removeSoldier(row+1, column);
                            else if((previsRow == row-2 && (lBoard[row-1][column] !=turn && lBoard[row-1][column] !=0 ))) removeSoldier(row-1,column);
                            moveSoldier();
                        }
                        else if(previsRow == row){
                            if((previsColumn == column+2 && (lBoard[row][column+1] !=turn && lBoard[row][column+1] !=0 ))) removeSoldier(row, column+1);
                            else if((previsColumn == column-2 && (lBoard[row][column-1] !=turn && lBoard[row][column-1] !=0 ))) removeSoldier(row, column-1);
                            moveSoldier();
                        }
                    }
                } 

                previsButtonPressed = false;
            }
            else if(lBoard[row][column] == 0 ){
                previsButtonPressed = false;
            }
            else
            {
                previsButtonPressed = true;
                previsRow=row;
                previsColumn=column;
            }
            //debag:
            //System.out.println("previsRow:"+previsRow+" "+"previsColumn:"+previsColumn+" "+"previsButtonPressed:"+previsButtonPressed+" "+"row:"+row+" "+"column:"+column+" "+"player:"+lBoard[row][column]);
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
