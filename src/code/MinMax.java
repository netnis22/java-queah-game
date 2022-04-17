package code;

import java.util.Stack;

public class MinMax {

    private int[][] board;
    private int depth;
    private int maximzingPlayert; // 1 red | 2 black
    private int bestIndex;
    private int bestPop;

    private Computer computer, enemy;
    private Computer copyComputer, copyEnemy;

    private GameButton[][] gBoard;
    
    private boolean isEaten=false;
    

    public MinMax(int[][] lboard, Computer enemy, Computer computer, int depth, int maximzingPlayert, GameButton[][] gBoard) {
        this.board = lboard;
        this.computer = computer;
        this.depth = depth;
        this.maximzingPlayert = maximzingPlayert;
        this.gBoard=gBoard;
        this.enemy = enemy;

        this.copyComputer = new Computer(computer.getPlayer_color(),computer.getMap());
        this.copyEnemy = new Computer(enemy.getPlayer_color(),enemy.getMap());

        copyComputer.copyComputer(computer);
        copyEnemy.copyComputer(enemy);
    }

    public MinMax(int[][] lboard, Players enemy, Computer computer,boolean isEaten, int depth, int maximzingPlayert, GameButton[][] gBoard) {
        this.board = lboard;
        this.computer = computer;
        this.depth = depth;
        this.maximzingPlayert = maximzingPlayert;
        this.isEaten = isEaten;
        this.gBoard=gBoard;

        this.enemy=new Computer(enemy.player_color, computer, computer.getMap(), 2);
        this.enemy.copyPlayer(enemy);;
        this.copyComputer = new Computer(computer.getPlayer_color(),computer.getMap());
        this.copyEnemy = new Computer(this.enemy.getPlayer_color(),this.enemy.getMap());

        copyComputer.copyComputer(computer);
        copyEnemy.copyComputer(this.enemy);
    }

    public int getBestPop() {
        return bestPop;
    }

    public int getBestIndex() {
        return bestIndex;
    }


    /*public int minmax(){
        if(depth == 0 || isEndGame()){
            return maxWeight();
        }
        if(maximzingPlayert==computer.getPlayer_color()){
            int maxWeight = Integer.MIN_VALUE;
            int weight;
            int[] data;
            if(isEaten){
                data = computer.playMinMax(isEaten,null, null, null, board, gBoard);
                weight = new MinMax(updateBord(board, data, computer.getPlayer_color()), computer, enemy, false,depth-1, maximzingPlayert).minmax();
                maxWeight=Math.max(maxWeight,weight);
                return maxWeight;
            }
            else if(computer.getEatSoldierMovesStack()!=null){
                int popnum = 0;
                int bestSoldierWeight = Integer.MIN_VALUE;
                int soldierWeight;
                for (SoldierMoves soldierMoves : computer.getEatSoldierMovesStack()){
                    int index=0;
                    for (Coordinate[] coordinate : soldierMoves.getPossibleEatMoves()) {
                        data = computer.playMinMax(isEaten,coordinate[0] , coordinate[1], soldierMoves.getSoldierCoordinate(), board, gBoard);
                        weight = new MinMax(updateBord(board, data, computer.getPlayer_color()),computer,enemy,true,depth-1,maximzingPlayert).minmax();
                        if(maxWeight<weight){
                            maxWeight=weight;
                            bestIndex=index;
                        }
                        index++;
                    }
                    soldierWeight=maxWeight;
                    if(bestSoldierWeight<soldierWeight){
                        bestSoldierWeight=soldierWeight;
                        bestPop=popnum;
                    }
                    popnum++;
                }
                return maxWeight;
            }
            else if(computer.getNotSafeSoldierMovesStack() != null)
            {
                int popnum = 0;
                int bestSoldierWeight = Integer.MIN_VALUE;
                int soldierWeight;
                for (SoldierMoves soldierMoves : computer.getNotSafeSoldierMovesStack()) {
                    int index=0;
                    for (Coordinate coordinate : soldierMoves.getPossibleMoves()) {
                        data = computer.playMinMax(isEaten,coordinate, null, soldierMoves.getSoldierCoordinate(), board, gBoard);
                        weight = new MinMax(updateBord(board, data, computer.getPlayer_color()),computer,enemy,false,depth-1,maximzingPlayert).minmax();
                        if(maxWeight<weight){
                            maxWeight=weight;
                            bestIndex=index;
                        }
                        index++;
                    }
                    soldierWeight=maxWeight;
                    if(bestSoldierWeight<soldierWeight){
                        bestSoldierWeight=soldierWeight;
                        bestPop=popnum;
                    }
                    popnum++;
                }
                return maxWeight;
            }
            else if(computer.getEatSoldierMovesStack()==null && computer.getNotSafeSoldierMovesStack()==null){
                int popnum = 0;
                int bestSoldierWeight = Integer.MIN_VALUE;
                int soldierWeight;
                for (SoldierMoves soldierMoves : computer.getSoldierMovesStack()) {
                    int index=0;
                    for (Coordinate coordinate : soldierMoves.getPossibleMoves()) {
                        data = computer.playMinMax(isEaten,coordinate, null, soldierMoves.getSoldierCoordinate(), board, gBoard);
                        weight = new MinMax(updateBord(board, data, computer.getPlayer_color()),computer,enemy,false,depth-1,maximzingPlayert).minmax();
                        if(maxWeight<weight){
                            maxWeight=weight;
                            bestIndex=index;
                        }
                        index++;
                    }
                    soldierWeight=maxWeight;
                    if(bestSoldierWeight<soldierWeight){
                        bestSoldierWeight=soldierWeight;
                        bestPop=popnum;
                    }
                    popnum++;
                }
                return maxWeight;
            }
            else return maxWeight;
        }
        else{
            int minWeight = Integer.MAX_VALUE;
            int weight;
            int[] data;
            if(isEaten){
                data = enemy.playMinMax(isEaten,null, null, null, board, gBoard);
                weight = new MinMax(updateBord(board, data, enemy.getPlayer_color()), enemy, computer, false,depth-1, maximzingPlayert).minmax();
                minWeight=Math.min(minWeight,weight);
                return minWeight;
            }
            else if(enemy.getEatSoldierMovesStack()!=null){
                int popnum = 0;
                int bestSoldierWeight = Integer.MAX_VALUE;
                int soldierWeight;
                for (SoldierMoves soldierMoves : enemy.getEatSoldierMovesStack()) {
                    int index=0;
                    for (Coordinate[] coordinate : soldierMoves.getPossibleEatMoves()) {
                        data = enemy.playMinMax(isEaten,coordinate[0] , coordinate[1], soldierMoves.getSoldierCoordinate(), board, gBoard);
                        weight = new MinMax(updateBord(board, data, enemy.getPlayer_color()),enemy,computer,true,depth-1,maximzingPlayert).minmax();
                        if(weight<minWeight){
                            minWeight=weight;
                            bestIndex=index;
                        }
                        index++;
                    }
                    soldierWeight=minWeight;
                    if(bestSoldierWeight>soldierWeight){
                        bestSoldierWeight=soldierWeight;
                        bestPop=popnum;
                    }
                    popnum++;
                }
                return minWeight;
            }
            else if(enemy.getNotSafeSoldierMovesStack() != null)
            {
                int popnum = 0;
                int bestSoldierWeight = Integer.MAX_VALUE;
                int soldierWeight;
                for (SoldierMoves soldierMoves : enemy.getNotSafeSoldierMovesStack()) {
                    int index=0;
                    for (Coordinate coordinate : soldierMoves.getPossibleMoves()) {
                        data = enemy.playMinMax(isEaten,coordinate , null, soldierMoves.getSoldierCoordinate(), board, gBoard);
                        weight = new MinMax(updateBord(board, data, enemy.getPlayer_color()),enemy,computer,false,depth-1,maximzingPlayert).minmax();
                        if(weight<minWeight){
                            minWeight=weight;
                            bestIndex=index;
                        }
                        index++;
                    }
                    soldierWeight=minWeight;
                    if(bestSoldierWeight>soldierWeight){
                        bestSoldierWeight=soldierWeight;
                        bestPop=popnum;
                    }
                    popnum++;
                }
                return minWeight;
            }
            else if(enemy.getEatSoldierMovesStack()==null && enemy.getNotSafeSoldierMovesStack()==null){
                int popnum = 0;
                int bestSoldierWeight = Integer.MAX_VALUE;
                int soldierWeight;
                for (SoldierMoves soldierMoves : enemy.getSoldierMovesStack()) {
                    int index=0;
                    for (Coordinate coordinate : soldierMoves.getPossibleMoves()) {
                        data = enemy.playMinMax(isEaten,coordinate , null, soldierMoves.getSoldierCoordinate(), board, gBoard);
                        weight = new MinMax(updateBord(board, data, enemy.getPlayer_color()),enemy,computer,false,depth-1,maximzingPlayert).minmax();
                        if(weight<minWeight){
                            minWeight=weight;
                            bestIndex=index;
                        }
                        index++;
                    }
                    soldierWeight=minWeight;
                    if(bestSoldierWeight>soldierWeight){
                        bestSoldierWeight=soldierWeight;
                        bestPop=popnum;
                    }
                    popnum++;
                }
                return minWeight;
            }
            else return minWeight;
        }
    }*/


    public int negaMax(){

        // int[][] computerlboardCopy=copyComputer.getLbord();
        // int[][] enemylboardCopy=copyEnemy.getLbord();
        // GameButton[][] computergBoardCopy=copyComputer.getGBoard();
        // GameButton[][] enemygBoardCopy=copyEnemy.getGBoard();

        // copyComputer.setLbord(board);
        // copyEnemy.setLbord(board);
        // copyComputer.setGbord(gBoard);
        // copyEnemy.setGbord(gBoard);
        // copyComputer.updateStaks();
        // copyEnemy.updateStaks();

        // computer.setLbord(computerlboardCopy);
        // enemy.setLbord(enemylboardCopy);
        // computer.setGbord(computergBoardCopy);
        // enemy.setGbord(enemygBoardCopy);

       // System.out.println("depth: "+depth+" is end: "+(depth == 0 || isEndGame()));
        if(depth == 0 || isEndGame()){
            //System.out.println(maxWeight());
            return maxWeight();
        }

        int maxWeight = Integer.MIN_VALUE;
        int weight;
        int[] data;
        if(isEaten){
            //System.out.println("isEaten");
            data = copyComputer.playMinMax(isEaten,null, null, null, board, gBoard);
            weight = - new MinMax(updateBord(board, data, copyComputer.getPlayer_color()), copyComputer, copyEnemy, false,depth-1, maximzingPlayert,gBoard).negaMax();
            maxWeight=Math.max(maxWeight,weight);
            return maxWeight;
        } 
        else if(copyComputer.getEatSoldierMovesStack()!=null && !copyComputer.getEatSoldierMovesStack().isEmpty()){
            int popnum = 0;
            int bestSoldierWeight = Integer.MIN_VALUE;
            int soldierWeight;
            Stack<SoldierMoves> temp = copyComputer.getEatSoldierMovesStack();
            while(!temp.isEmpty()){
                //System.out.println("soldiers");
                int index=0;
                for (Coordinate[] coordinate : temp.peek().getPossibleEatMoves()) {
                    data = copyComputer.playMinMax(isEaten,coordinate[0] , coordinate[1], temp.peek().getSoldierCoordinate(), board, gBoard);
                    weight = - new MinMax(updateBord(board, data, copyComputer.getPlayer_color()),copyComputer,copyEnemy,true,depth-1,maximzingPlayert,gBoard).negaMax();
                    //System.out.println("coordinate");
                    if(maxWeight<weight){
                        maxWeight=weight;
                        bestIndex=index;
                    }
                    index++;
                }
                soldierWeight=maxWeight;
                if(bestSoldierWeight<soldierWeight){
                    bestSoldierWeight=soldierWeight;
                    bestPop=popnum;
                }
                popnum++;
                temp.pop();
            }
            return maxWeight;
        }
        else if(copyComputer.getNotSafeSoldierMovesStack() != null && !copyComputer.getNotSafeSoldierMovesStack().isEmpty())
        {
            int popnum = 0;
            int bestSoldierWeight = Integer.MIN_VALUE;
            int soldierWeight;
            Stack<SoldierMoves> temp = copyComputer.getNotSafeSoldierMovesStack();
            while(!temp.isEmpty()){
                //System.out.println("soldiers");
                int index=0;
                for (Coordinate coordinate : temp.peek().getPossibleMoves()) {
                    //System.out.println("coordinate");
                    data = copyComputer.playMinMax(isEaten,coordinate, null, temp.peek().getSoldierCoordinate(), board, gBoard);
                    weight = - new MinMax(updateBord(board, data, copyComputer.getPlayer_color()),copyComputer,copyEnemy,false,depth-1,maximzingPlayert,gBoard).negaMax();
                    if(maxWeight<weight){
                        maxWeight=weight;
                        bestIndex=index;
                    }
                    index++;
                }
                soldierWeight=maxWeight;
                if(bestSoldierWeight<soldierWeight){
                    bestSoldierWeight=soldierWeight;
                    bestPop=popnum;
                }
                popnum++;
                temp.pop();
            }
            return maxWeight;
        }
        else if(copyComputer.getSoldierMovesStack()!=null && !copyComputer.getSoldierMovesStack().isEmpty()){
            int popnum = 0;
            int bestSoldierWeight = Integer.MIN_VALUE;
            int soldierWeight;
            Stack<SoldierMoves> temp= new Stack<SoldierMoves>();
            copyStack(temp,copyComputer.getSoldierMovesStack());

            while(!temp.isEmpty()){
                //System.out.println("soldiers");
                int index=0;
                for (Coordinate coordinate : temp.peek().getPossibleMoves()) {
                    //System.out.println("coordinate");
                    data = copyComputer.playMinMax(isEaten,coordinate, null, temp.peek().getSoldierCoordinate(), board, gBoard);
                    weight = - new MinMax(updateBord(board, data, copyComputer.getPlayer_color()),copyComputer,copyEnemy,false,depth-1,maximzingPlayert,gBoard).negaMax();
                    if(maxWeight<weight){
                        maxWeight=weight;
                        bestIndex=index;
                    }
                    index++;
                }
                soldierWeight=maxWeight;
                if(bestSoldierWeight<soldierWeight){
                    bestSoldierWeight=soldierWeight;
                    bestPop=popnum;
                }
                popnum++;
                temp.pop();
            }
            return maxWeight;
        }
        else{
            //System.out.println("else");
            return maxWeight;
        }

    }

    
    //this function is copy stack from one stack to another
    private void copyStack(Stack<SoldierMoves> copySoldierMovesStack,Stack<SoldierMoves> soldierMovesStack){
        Stack<SoldierMoves> copySoldierMovesStack2=new Stack<SoldierMoves>();

        while(!soldierMovesStack.isEmpty()){
            copySoldierMovesStack.push(soldierMovesStack.peek());
            copySoldierMovesStack2.push(soldierMovesStack.pop());
        }

        while(!copySoldierMovesStack2.isEmpty()) soldierMovesStack.push(copySoldierMovesStack2.pop());            

    }

    private int[][] updateBord(int[][] board, int[] data, int player_color) {
        int[][] board_copy = board.clone();
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
        return board_copy;
    }

    private boolean isEndGame(){
        //System.out.println(copyComputer.getSoldier_on_board()+" "+copyEnemy.getSoldier_on_board());
        if(copyComputer.getSoldier_on_board() == 0 || copyEnemy.getSoldier_on_board() == 0){
            return true;
        }
        return false;
    }

    private int maxWeight(){
        int weight = Integer.MIN_VALUE;
        int weightGame=0; //num of ally solder - num of copyEnemy soldier 
        if(copyComputer.getEatSoldierMovesStack()!=null && !copyComputer.getEatSoldierMovesStack().isEmpty()){
            weight = copyComputer.findBestEat(copyComputer.getEatSoldierMovesStack()).weightSoldierMoves();
        }
        else if(copyComputer.getSoldierMovesStack()!=null &&!copyComputer.getSoldierMovesStack().isEmpty()){
            weight = copyComputer.findBestMove(copyComputer.getSoldierMovesStack()).weightSoldierMoves();
        }
        else if(copyComputer.getNotSafeSoldierMovesStack()!=null && !copyComputer.getNotSafeSoldierMovesStack().isEmpty()){
            weight = copyComputer.findBestMove(copyComputer.getNotSafeSoldierMovesStack()).weightSoldierMoves();
        }
        weightGame = copyComputer.getSoldierLeft()*100 - copyEnemy.getSoldierLeft()*100;
        return weight + weightGame;
    }

    private int maxWeight2(){
        int weightGame; //num of ally solder - num of copyEnemy soldier 
        weightGame = copyComputer.getSoldierLeft()*1000 - copyEnemy.getSoldierLeft()*1000;
        return weightGame;
    }

}
