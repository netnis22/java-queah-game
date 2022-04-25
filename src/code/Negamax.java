package code;

import java.util.Stack;

public class Negamax {
    public static int numofRecursion = -1;
    public int bestMoveIndex=0;
    public int bestpop=0;

    private int[][] board;
    private int depth;

    private Computer me;
    private Computer opponent;

    private GameButton [][]gBoard;

    private boolean isEaten;

    public Negamax(int[][] board,GameButton [][]gBoard, int depth,Computer me, Computer opponent, boolean isEaten) {
        this.depth = depth;
        this.isEaten = isEaten;

        this.board = new int[board.length][board[0].length];
        copyBoard(board,this.board);

        this.gBoard = new GameButton[gBoard.length][gBoard[0].length];
        copyGBoard(gBoard);
        
        this.me = new Computer(me.getPlayer_color(), me.getMap(), me.getDifficulty());
        this.me.copy(me);
        this.me.setBoard(board);
        this.me.setGBoard(gBoard);

        this.opponent = new Computer(opponent.getPlayer_color(), opponent.getMap(), opponent.getDifficulty());
        this.opponent.copy(opponent);
        this.opponent.setBoard(board);
        this.opponent.setGBoard(gBoard);
        
        numofRecursion++;
        //System.out.println("numofRecursion: "+numofRecursion);
    }

    public int negamax(){
        if (depth == 0 || gameOver()) return evaluate();

        int maxEval = Integer.MIN_VALUE;
        int bestPopEval = Integer.MIN_VALUE;
        int numOfpop = 0;
        int[] data;

        Stack<SoldierMoves> soldier = new Stack<>();
        

        me.findAllPossibleSoldier();

        soldier = (Stack<SoldierMoves>)me.getSoldierMovesStack().clone();
        while(!soldier.isEmpty()){
            SoldierMoves moves = soldier.pop();
            
            int eval=Integer.MIN_VALUE;
            int numOfindex = 0;
            int bestMoveIndexEval = Integer.MIN_VALUE;
            int[][] newBoard;

            if(isEaten && me.getIsSoldierNotLeftFirstTime()<=1){
                //data = me.play(isEaten, board, gBoard, opponent);
                data = me.playNegamax(true,null,null,null,board, gBoard);

                newBoard = new int[board.length][board[0].length];
                copyBoard(board,newBoard);
                updateBord(newBoard, data, me.getPlayer_color());

                eval = -1 * new Negamax(newBoard, gBoard, depth-1,opponent, me, false).negamax();    
                maxEval = Math.max(maxEval, eval);
            }
            else if(moves.getPossibleEatMoves()!=null && !moves.getPossibleEatMoves().isEmpty()){
                for(int i = 0; i<moves.getPossibleEatMoves().size();i++){

                    //data = me.play(false, board, gBoard, opponent);
                    data = me.playNegamax(false,moves.getPossibleEatMoves().get(i)[0],moves.getPossibleEatMoves().get(i)[1],moves.getSoldierCoordinate(),board, gBoard);

                    newBoard = new int[board.length][board[0].length];
                    copyBoard(board,newBoard);
                    updateBord(newBoard, data, me.getPlayer_color());

                    eval = -1 * new Negamax(newBoard, gBoard, depth-1, opponent, me, true).negamax();
                    maxEval = Math.max(maxEval, eval);

                    if(maxEval<bestMoveIndexEval){
                        maxEval=bestMoveIndexEval;
                        bestMoveIndex=numOfindex;
                    }
                    numOfindex++;
                }
            }
            else if(moves.getPossibleMoves()!=null && !moves.getPossibleMoves().isEmpty()){
                for(int i = 0; i<moves.getPossibleMoves().size();i++){

                    //data = me.play(false, board, gBoard, opponent);
                    data = me.playNegamax(false,moves.getPossibleMoves().get(i),null,moves.getSoldierCoordinate(),board, gBoard);
                    newBoard = new int[board.length][board[0].length];
                    copyBoard(board,newBoard);
                    updateBord(newBoard, data, me.getPlayer_color());

                    eval = -1 * new Negamax(newBoard, gBoard, depth-1, opponent, me, false).negamax();
                    maxEval = Math.max(maxEval, eval);

                    if(maxEval<bestMoveIndexEval){
                        maxEval=bestMoveIndexEval;
                        bestMoveIndex=numOfindex;
                    }
                    numOfindex++;
                }
            }

            if(maxEval<bestPopEval){
                bestPopEval = maxEval;
                bestpop = numOfpop;
            }
            numOfpop++;
        }

        return maxEval;
    }

    private int evaluate() {
        int meSoldiers = (me.getSoldierLeft()+me.getSoldier_on_board())*10;
        int opponentSoldiers = (opponent.getSoldierLeft()+opponent.getSoldier_on_board())*10;

        // if (meSoldiers == 0) return Integer.MIN_VALUE;
        // if (opponentSoldiers == 0) return Integer.MAX_VALUE;

        int meEval = evaluateBordByPlayer(me);
        int opponentEval = evaluateBordByPlayer(opponent);

        return meSoldiers+meEval - opponentSoldiers+opponentEval;
    }

    private int evaluateBordByPlayer(Computer computer){
        int eval = 0;
        Stack<SoldierMoves> soldiers;

        if(computer.getSoldierMovesStack()==null || computer.getSoldierMovesStack().isEmpty()) return 0;
        soldiers = computer.getSoldierMovesStack();

        while(!soldiers.isEmpty()){
            eval += soldiers.pop().weightSoldierMoves();
        }
        return eval;
    }

    private void updateBord(int[][] board,int[] data, int player_color) {
        if(data[6]==0 && data[2]!=0){
            board[data[0]][data[1]]=board[data[2]][data[3]];
            board[data[2]][data[3]]=0;
        }
        else if(data[6]==1){
            board[data[0]][data[1]]=board[data[2]][data[3]];
            board[data[2]][data[3]]=0;
            board[data[4]][data[5]]=0;
        }
        else{
            board[data[0]][data[1]] = player_color;
        }
    }

    public void copyBoard(int[][] board, int[][] newBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
    }


    public void copyGBoard(GameButton [][]gBoard) {
        for (int i = 0; i < gBoard.length; i++) {
            for (int j = 0; j < gBoard[i].length; j++) {
                this.gBoard[i][j] = gBoard[i][j];
            }
        }
    }

    public boolean gameOver() {
        return me.isSoldiersLeft() || opponent.isSoldiersLeft();
    }

}
