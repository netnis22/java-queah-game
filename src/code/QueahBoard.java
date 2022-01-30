package code;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class QueahBoard extends JPanel {

    //img
    private static final String IMG_BOARD="src/img/Wood.png";
    ImageIcon icon = new ImageIcon(IMG_BOARD);
	Image img = icon.getImage();

    private static int startPlayer=1;

    private static int sizeOfboard=5;
    private static int heightOfboard=2;
    private static int max_Player_soldiers_on_board=4;
    private static int turn;// 1 red 2 black

    private GameButton [][]gBoard;  //graphic board
	private int [][]lBoard;       //logic board, 0 free 1 red player  2 black Player

    private Soldier redSoldier = new Soldier("red");
    private Soldier blackSoldier = new Soldier("black");

    private Players playerRed;
    private Players playerBlack;
    private Computer computerRed;
    private Computer computerBlack;

    private Game game;

    public QueahBoard(Game game) {
        this.game= game;
        startPlayer=game.turn;

        constrictGamMode();
        constrictorMap(game.map);
        initBoard();
    }

    private void constrictGamMode(){
        switch (game.gameMode) {
            case 1:
                playerRed = game.playerRed;
                computerBlack = game.computerBlack;
                playerBlack = game.computerBlack;
                break;
            case 2:
                computerRed = game.computerRed;
                computerBlack = game.computerBlack;
                playerRed = game.computerRed;
                playerBlack = game.computerBlack;
                break;
            default:
                playerRed=game.playerRed;
                playerBlack = game.playerBlack;
                break;
        }
    }


    private void constrictorMap(String map){
        if(map.equals("small")){
            QueahBoard.sizeOfboard = 5;
            QueahBoard.heightOfboard = 2;
        }
        if(map.equals("mid")){
            QueahBoard.sizeOfboard = 7;
            QueahBoard.heightOfboard = 3;
        }
        if(map.equals("large")){
            QueahBoard.sizeOfboard = 9;
            QueahBoard.heightOfboard = 4;
        }
    }

    public void initBoard()
	{
		gBoard = new GameButton[sizeOfboard][sizeOfboard];
		lBoard = new int[sizeOfboard][sizeOfboard];
		
		setLayout(new GridLayout(sizeOfboard,sizeOfboard));
        //up
        for (int row = 0; row < heightOfboard; row ++) {
            for (int column = 0; column < sizeOfboard; column ++) {
                if(Math.abs(column - heightOfboard) <= row){
                    lBoard[row][column]=0;
                    gBoard[row][column]= new GameButton(img,null);
                    gBoard[row][column].addActionListener(new AL(row,column));
                }
                else{
                    lBoard[row][column]=-1;
                    gBoard[row][column] = new GameButton();
                    gBoard[row][column].setEnabled(false);
                    gBoard[row][column].setBackground(new Color(0,0,0,0.7f));
                }
            }
            for (int column = 0; column < sizeOfboard; column ++) {
                add(gBoard[row][column]);
            }
        }
        //mid
        for( int row=heightOfboard; row<(sizeOfboard-heightOfboard); row++){
            for(int column=0; column<sizeOfboard; column++){
                lBoard[row][column]=0;
                gBoard[row][column]= new GameButton(img,null);
                gBoard[row][column].addActionListener(new AL(row,column));
                add(gBoard[row][column]);
            }
        }
        //down
        for (int row = sizeOfboard-heightOfboard,k=heightOfboard-1; row < sizeOfboard; row ++,k--) {
            for (int column = 0; column < sizeOfboard; column ++) {
                if((lBoard[k][column]==0)){
                    lBoard[row][column]=0;
                    gBoard[row][column]= new GameButton(img,null);
                    gBoard[row][column].addActionListener(new AL(row,column));
                }
                else{
                    lBoard[row][column]=-1;
                    gBoard[row][column] = new GameButton();
                    gBoard[row][column].setEnabled(false);
                    gBoard[row][column].setBackground(new Color(0,0,0,0.7f));
                }
            }
            for (int column = 0; column < sizeOfboard; column ++) {
                add(gBoard[row][column]);
            }
        }		
		turn=startPlayer;
	}

    public static void setTurn(int start){
        turn=start;
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

    public void midMapSolid(){
        max_Player_soldiers_on_board=6;
        gBoard[0][3].setSoldier(blackSoldier);
        lBoard[0][3] = 2;
        gBoard[1][3].setSoldier(blackSoldier);
        lBoard[1][3] = 2;
        gBoard[1][4].setSoldier(blackSoldier);
        lBoard[1][4] = 2;
        gBoard[2][4].setSoldier(blackSoldier);
        lBoard[2][4] = 2;
        gBoard[2][5].setSoldier(blackSoldier);
        lBoard[2][5] = 2;
        gBoard[3][5].setSoldier(blackSoldier);
        lBoard[3][5] = 2;

        gBoard[6][3].setSoldier(redSoldier);
        lBoard[6][3] = 1;
        gBoard[5][3].setSoldier(redSoldier);
        lBoard[5][3] = 1;
        gBoard[5][2].setSoldier(redSoldier);
        lBoard[5][2] = 1;
        gBoard[4][2].setSoldier(redSoldier);
        lBoard[4][2] = 1;
        gBoard[4][1].setSoldier(redSoldier);
        lBoard[4][1] = 1;
        gBoard[3][1].setSoldier(redSoldier);
        lBoard[3][1] = 1;
    }

    public void largeMapSolid(){
        max_Player_soldiers_on_board=8;
        gBoard[0][4].setSoldier(blackSoldier);
        lBoard[0][4] = 2;
        gBoard[1][4].setSoldier(blackSoldier);
        lBoard[1][4] = 2;
        gBoard[1][5].setSoldier(blackSoldier);
        lBoard[1][5] = 2;
        gBoard[2][5].setSoldier(blackSoldier);
        lBoard[2][5] = 2;
        gBoard[2][6].setSoldier(blackSoldier);
        lBoard[2][6] = 2;
        gBoard[3][6].setSoldier(blackSoldier);
        lBoard[3][6] = 2;
        gBoard[3][7].setSoldier(blackSoldier);
        lBoard[3][7] = 2;
        gBoard[4][7].setSoldier(blackSoldier);
        lBoard[4][7] = 2;

        gBoard[8][4].setSoldier(redSoldier);
        lBoard[8][4] = 1;
        gBoard[7][4].setSoldier(redSoldier);
        lBoard[7][4] = 1;
        gBoard[7][3].setSoldier(redSoldier);
        lBoard[7][3] = 1;
        gBoard[6][3].setSoldier(redSoldier);
        lBoard[6][3] = 1;
        gBoard[6][2].setSoldier(redSoldier);
        lBoard[6][2] = 1;
        gBoard[5][2].setSoldier(redSoldier);
        lBoard[5][2] = 1;
        gBoard[5][1].setSoldier(redSoldier);
        lBoard[5][1] = 1;
        gBoard[4][1].setSoldier(redSoldier);
        lBoard[4][1] = 1;
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


        public void victory(int player){
            String playerColor;
            if(player==1) playerColor ="red";
            else playerColor = "black";

            javax.swing.JOptionPane.showMessageDialog(game,playerColor);
            game.dispose();
            new Game();
        }

        public void moveSoldier(){
            lBoard[row][column]=lBoard[previsRow][previsColumn];
            lBoard[previsRow][previsColumn] = 0;
            gBoard[row][column].setSoldier(gBoard[previsRow][previsColumn].getSoldier());
            gBoard[previsRow][previsColumn].setSoldier(null);
            repaint();
            if(turn==1) turn=2;
            else turn=1;
            //System.out.println("lBoard:"+lBoard[2][4]);
            //System.out.println("previsRow:"+previsRow+" "+"previsColumn:"+previsColumn+" "+"previslBoard:"+lBoard[previsRow][previsColumn]+" "+"row:"+row+" "+"column:"+column+" "+"lBoard:"+lBoard[row][column]);
            //System.out.println("turn change to " + turn);
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
            switch (game.gameMode) {
                case 1:
                    HumanMove();
                    break;
                case 2:
                    ComputerMove();
                    break;
                default:
                    HumanMove();
                    break;
            }
                
        }

        private void HumanMove(){
            if(turn == playerRed.getPlayer_color() && (!playerRed.IsHuman())) return;
            if(turn == playerBlack.getPlayer_color() && (!playerBlack.IsHuman())) return;

            if(playerRed.getSoldier_on_board() == 0) victory(2);
            if(playerBlack.getSoldier_on_board() == 0) victory(1);
        
            if(isSoldiersEaten) addSoldierToBoard();
            else if(previsButtonPressed){
                if(((previsRow == row+1 || previsRow == row-1) && previsColumn == column) || ((previsColumn == column+1 || previsColumn == column-1) && previsRow == row )){
                    if(lBoard[row][column] == 0 && lBoard[previsRow][previsColumn] == turn){
                        if(!(previsRow == row && previsColumn == column)){
                            System.out.println("1");
                            moveSoldier();
                            if(game.gameMode==1) ComputerMove(); 
                        }
                    }
                }
        
                if(lBoard[row][column] == 0 && lBoard[previsRow][previsColumn] == turn){
                    if(!(previsRow == row && previsColumn == column)){
                        if(previsColumn == column){
                            if((previsRow == row+2 && (lBoard[row+1][column] !=turn && lBoard[row+1][column] !=0 ))){
                                System.out.println("2");
                                removeSoldier(row+1, column);
                                moveSoldier();
                                if(game.gameMode==1) ComputerMove(); 
                            } 
                            else if((previsRow == row-2 && (lBoard[row-1][column] !=turn && lBoard[row-1][column] !=0 ))){
                                System.out.println("3");
                                removeSoldier(row-1,column);
                                moveSoldier();
                                if(game.gameMode==1) ComputerMove(); 
                            } 
                                    
                        }
                        else if(previsRow == row){
                            if((previsColumn == column+2 && (lBoard[row][column+1] !=turn && lBoard[row][column+1] !=0 ))){
                                System.out.println("4");
                                removeSoldier(row, column+1);
                                moveSoldier();
                                if(game.gameMode==1) ComputerMove(); 
                            } 
                            else if((previsColumn == column-2 && (lBoard[row][column-1] !=turn && lBoard[row][column-1] !=0 ))){
                                System.out.println("5");
                                removeSoldier(row, column-1);
                                moveSoldier();
                                if(game.gameMode==1) ComputerMove(); 
                            } 
                                    
                        }
                    }
                } 
                previsButtonPressed = false;
            }
            else if(lBoard[row][column] == 0 ){
                System.out.println("7");
                previsButtonPressed = false;
            }
            else
            {
                System.out.println("8");
                previsButtonPressed = true;
                previsRow=row;
                previsColumn=column;
            }
            //debag:
            System.out.println("previsRow:"+previsRow+" "+"previsColumn:"+previsColumn+" "+"previsButtonPressed:"+previsButtonPressed+" "+"row:"+row+" "+"column:"+column+" "+"player:"+lBoard[row][column]+" "+"turn:"+turn);
        }

        private void ComputerMove(){
            if(playerRed.getSoldier_on_board() == 0) victory(2);
            if(playerBlack.getSoldier_on_board() == 0) victory(1);

            int data[];
            if(turn == playerRed.getPlayer_color() && !playerRed.IsHuman()) data = computerRed.move();
            else if(turn == playerBlack.getPlayer_color() && !playerBlack.IsHuman()) data = computerBlack.move();
            else{
                // if(turn==1) turn=2;
                // else turn=1;

                return;
            }

            row = data[0];
            column = data[1];
            previsRow = data[2];
            previsColumn = data[3];

            if(data[6]==0){
                moveSoldier();
            }
            else
            {
                removeSoldier(data[4], data[5]);
                moveSoldier();
            }
        }
    }   
}
