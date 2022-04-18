package code;

import java.util.List;
import java.util.Stack;

public class Computer extends Players {

    private int difficulty;
    private int player_color; //1 red 2 black
    private int isSoldierNotLeftFirstTime;
    private int sameMoveCount;
    private int depth=3;

    private Coordinate lestPos=null;

    private int [][]lBoard;
    private GameButton [][]gBoard;

    private Stack<SoldierMoves> soldierMovesStack;

    private Computer enamy;
    
    
    public Computer(int player_color, String map,int difficulty) {
        super(player_color, map);
        this.player_color = player_color;
        this.difficulty=difficulty;

        isSoldierNotLeftFirstTime =0;
        soldierMovesStack = new Stack<SoldierMoves>();
    }

    //this function is to manage the computer,isSoldierLeft-1(yes)/0(no)
    public int[] play(boolean isEaten,int [][]lBoard,GameButton [][]gBoard, Computer enamy){
        int test[]=new int[8];
        this.lBoard=lBoard;
        this.gBoard=gBoard;
        this.enamy=enamy;
        

        if(getSoldierLeft()==0) isSoldierNotLeftFirstTime++;

        if(isEaten && isSoldierNotLeftFirstTime<=1) test=addNewSolid();
        else test=move();

        soldierMovesStack.clear();

        return test;
    }

        //NOT FOR QUEAHBOARD ONLY MINMAX CAN USE THIS FUNCTION!!!!!!!!!!
        public int[] playNegamax(boolean isEaten,Coordinate moveCoordinate,Coordinate eat,Coordinate soldierCoordinates ,int [][]lBoard,GameButton [][]gBoard){
            int test[]=new int[8];
            this.lBoard=lBoard;
            this.gBoard=gBoard;
    
            if(getSoldierLeft()==0) isSoldierNotLeftFirstTime++;
    
            if(isEaten && isSoldierNotLeftFirstTime<=1) test=addNewSolid();
            else{
                if(isSoldierNotLeftFirstTime<=1) test[7]=1;
                else test[7]=0;
    
                if(eat !=null){
                    test[0] = moveCoordinate.getRow();
                    test[1] = moveCoordinate.getColumn();
                    test[2] = soldierCoordinates.getRow();
                    test[3] = soldierCoordinates.getColumn();
                    test[4] = eat.getRow();
                    test[5] = eat.getColumn();
                    test[6] = 1;
                }
                else{
                    test[0] = moveCoordinate.getRow();
                    test[1] = moveCoordinate.getColumn();
                    test[2] = soldierCoordinates.getRow();
                    test[3] = soldierCoordinates.getColumn();
                    test[4] = 0;
                    test[5] = 0;
                    test[6] = 0;
                }
            }
            return test;
        }

    //return newRow,newColumn,0,0,0,0,0,0
    //this function is for the computer to add new soldier if soldier is eaten
    private int[] addNewSolid(){
        int test[]=new int[8];
        int data[]=findMostWeightBlock();

        test[0]=data[0];
        test[1]=data[1];

        if(isSoldierNotLeftFirstTime<=1) test[7]=1;
        else test[7]=0;

        for(int i=2;i<7;i++) test[i]= 0;
        return test;
    }


    //return newRow,newColumn,previsRow,previsColumn, eatRow,eatColum, isEat- 1(yes)/0(no), isSoldierLeft-1(yes)/0(no)
    //this function is for the computer to move the soldier
    private int[] move(){
        int test[]=new int[8];
        int index;
        int size;
        Coordinate soldierCoordinate;
        Stack<SoldierMoves> copySoldierMovesStack=new Stack<SoldierMoves>();
        Stack<SoldierMoves> eatSoldierMovesStack=new Stack<SoldierMoves>();
        Stack<SoldierMoves> notSafeSoldierMovesStack=new Stack<SoldierMoves>();

        Negamax negamax;

        findAllPossibleSoldier();

        copyStack(copySoldierMovesStack,soldierMovesStack);

        while(!copySoldierMovesStack.isEmpty()){
            if(!copySoldierMovesStack.peek().getPossibleEatMoves().isEmpty()) eatSoldierMovesStack.push(copySoldierMovesStack.peek());
            copySoldierMovesStack.pop();
        }

        copySoldierMovesStack.clear();
        copyStack(copySoldierMovesStack,soldierMovesStack);

        while(!copySoldierMovesStack.isEmpty()){
            if(!copySoldierMovesStack.peek().isSoldierNotInDanger()) notSafeSoldierMovesStack.push(copySoldierMovesStack.peek());
            copySoldierMovesStack.pop();
        }
        
        
        if(!eatSoldierMovesStack.isEmpty()){
            System.out.println("eat");

            List<Coordinate[]> possibleEatMoves;

            if(difficulty==2){
                negamax = new Negamax(lBoard,gBoard,depth,Computer.this,enamy,false);
                negamax.negamax();
            }
            else negamax=null;

            SoldierMoves bestEatMoves;
            switch(difficulty){
                case 0:
                    popRandom(eatSoldierMovesStack);
                    possibleEatMoves=eatSoldierMovesStack.peek().getPossibleEatMoves();
                    soldierCoordinate=eatSoldierMovesStack.peek().getSoldierCoordinate();
                    break;
                case 1:
                    bestEatMoves = findBestEat(eatSoldierMovesStack);
                    possibleEatMoves=bestEatMoves.getPossibleEatMoves();
                    soldierCoordinate=bestEatMoves.getSoldierCoordinate();
                    break;
                case 2:
                    for(int i=0 ;i<negamax.bestpop;i++){
                        eatSoldierMovesStack.pop();
                    }
                    bestEatMoves=eatSoldierMovesStack.peek();
                    possibleEatMoves=bestEatMoves.getPossibleEatMoves();
                    soldierCoordinate=bestEatMoves.getSoldierCoordinate();
                    break;
                default:
                    popRandom(eatSoldierMovesStack);
                    possibleEatMoves=eatSoldierMovesStack.peek().getPossibleEatMoves();
                    soldierCoordinate=eatSoldierMovesStack.peek().getSoldierCoordinate();
                    break;
            }

            size = possibleEatMoves.size();

            switch(difficulty){
                case 0:
                    index=(int)(Math.random()*(size-1));
                    break;
                case 1:
                    index=indexOfBestEat(possibleEatMoves);
                    break;
                case 2:
                    index=negamax.bestMoveIndex;
                    break;
                default:
                    index=(int)(Math.random()*(size-1));
                    break;
            } 

            test[0] = possibleEatMoves.get(index)[0].getRow();
            test[1] = possibleEatMoves.get(index)[0].getColumn();
            test[2] = soldierCoordinate.getRow();
            test[3] = soldierCoordinate.getColumn();
            test[4] = possibleEatMoves.get(index)[1].getRow();
            test[5] = possibleEatMoves.get(index)[1].getColumn();
            test[6] = 1;
        }
        else if(!notSafeSoldierMovesStack.isEmpty()){
            System.out.println("move denger Soldier");

            List<Coordinate> possibleMoves;

            if(difficulty==2){
                negamax = new Negamax(lBoard,gBoard,depth,Computer.this,enamy,false);
                negamax.negamax();
            }
            else negamax=null;

            SoldierMoves bestMoves;
            switch(difficulty){
                case 0:
                    popRandom(notSafeSoldierMovesStack);
                    possibleMoves=notSafeSoldierMovesStack.peek().getPossibleMoves();
                    soldierCoordinate=notSafeSoldierMovesStack.peek().getSoldierCoordinate();
                    break;
                case 1:
                    bestMoves = findBestMove(notSafeSoldierMovesStack);
                    possibleMoves=bestMoves.getPossibleMoves();
                    soldierCoordinate=bestMoves.getSoldierCoordinate();
                    break;
                case 2:
                    for(int i=0 ;i<negamax.bestpop;i++){
                        soldierMovesStack.pop();
                    }
                    bestMoves=soldierMovesStack.peek();
                    possibleMoves=bestMoves.getPossibleMoves();
                    soldierCoordinate=bestMoves.getSoldierCoordinate();
                    break;
                default:
                    popRandom(notSafeSoldierMovesStack);
                    possibleMoves=notSafeSoldierMovesStack.peek().getPossibleMoves();
                    soldierCoordinate=notSafeSoldierMovesStack.peek().getSoldierCoordinate();
                    break;
            }

            size = notSafeSoldierMovesStack.peek().getPossibleMoves().size();

            switch(difficulty){
                case 0:
                    index=(int)(Math.random()*(size-1));
                    break;
                case 1:
                    index=indexOfBestMove(possibleMoves);
                    break;
                case 2:
                    index=negamax.bestMoveIndex;
                    break;
                default:
                    index=(int)(Math.random()*(size-1));
                    break;
            }

            //fixs loop infanetly problem
            if(lestPos==null)lestPos=possibleMoves.get(index);
            else if(lestPos.equals(possibleMoves.get(index))){
                sameMoveCount++;
                if(sameMoveCount>5){
                    index = 0;
                    while(lestPos.equals(possibleMoves.get(index)))
                    {
                        index++;
                        if(index>=size) break;
                    }
                }
            }
            else if(possibleMoves.size()>1){
                sameMoveCount=0;
                lestPos=possibleMoves.get(index);
            }
            test[0] = possibleMoves.get(index).getRow();
            test[1] = possibleMoves.get(index).getColumn();
            test[2] = soldierCoordinate.getRow();
            test[3] = soldierCoordinate.getColumn();
            test[4] = 0;
            test[5] = 0;
            test[6] = 0;

            if(isSoldierNotLeftFirstTime<=1) test[7]=1;
            else test[7]=0;
            
            return test;
        }
        else{
            System.out.println("move");
            
            List<Coordinate> possibleMoves;

            if(difficulty==2){
                negamax = new Negamax(lBoard,gBoard,depth,Computer.this,enamy,false);
                negamax.negamax();
            }
            else negamax=null;

            SoldierMoves bestMoves;
            switch(difficulty){
                case 0:
                    popRandom(soldierMovesStack);
                    possibleMoves=soldierMovesStack.peek().getPossibleMoves();
                    soldierCoordinate=soldierMovesStack.peek().getSoldierCoordinate();
                    break;
                case 1:
                    bestMoves = findBestMove(soldierMovesStack);
                    possibleMoves=bestMoves.getPossibleMoves();
                    soldierCoordinate=bestMoves.getSoldierCoordinate();
                    break;
                case 2:
                    for(int i=0 ;i<negamax.bestpop;i++){
                        soldierMovesStack.pop();
                    }
                    bestMoves=soldierMovesStack.peek();
                    possibleMoves=bestMoves.getPossibleMoves();
                    soldierCoordinate=bestMoves.getSoldierCoordinate();
                    break;
                default:
                    popRandom(soldierMovesStack);
                    possibleMoves=soldierMovesStack.peek().getPossibleMoves();
                    soldierCoordinate=soldierMovesStack.peek().getSoldierCoordinate();
                    break;
            }


            size = soldierMovesStack.peek().getPossibleMoves().size();

            switch(difficulty){
                case 0:
                    index=(int)(Math.random()*(size-1));
                    break;
                case 1:
                    index=indexOfBestMove(possibleMoves);
                    break;
                case 2:
                    index=negamax.bestMoveIndex;
                    break;
                default:
                    index=(int)(Math.random()*(size-1));
                    break;
            }

            //fixs loop infanetly problem
            if(lestPos==null)lestPos=possibleMoves.get(index);
            else if(lestPos.equals(possibleMoves.get(index))){
                sameMoveCount++;
                if(sameMoveCount>5){
                    index = 0;
                    while(lestPos.equals(possibleMoves.get(index)))
                    {
                        index++;
                        if(index>=size) break;
                    }
                }
            }
            else if(possibleMoves.size()>1){
                sameMoveCount=0;
                lestPos=possibleMoves.get(index);
            }

            test[0] = possibleMoves.get(index).getRow();
            test[1] = possibleMoves.get(index).getColumn();
            test[2] = soldierCoordinate.getRow();
            test[3] = soldierCoordinate.getColumn();
            test[4] = 0;
            test[5] = 0;
            test[6] = 0;
        }

        if(isSoldierNotLeftFirstTime<=1) test[7]=1;
        else test[7]=0;
        
        return test;
    }

    //this function pop the best move from the stack
    private SoldierMoves findBestMove(Stack<SoldierMoves> soldierMovesStack){

        Stack<SoldierMoves> copySoldierMovesStack=new Stack<SoldierMoves>();
        SoldierMoves bestMove=null;

        copyStack(copySoldierMovesStack, soldierMovesStack);

        while (!copySoldierMovesStack.isEmpty()){
            if(bestMove==null) bestMove = copySoldierMovesStack.pop();
            else if(bestMove.weightSoldierMoves()<copySoldierMovesStack.peek().weightSoldierMoves()) bestMove = copySoldierMovesStack.pop();
            else copySoldierMovesStack.pop();
        }

        System.out.println("\nfindBestMove - bestMove: "+bestMove+"\nWeight: "+bestMove.weightSoldierMoves()+"\n");

        return bestMove;
    }
    //this function return the index of the best move in the list
    private int indexOfBestMove(List<Coordinate> possibleMoves){

        boolean isSafe=false;

        int index=0;
        int numOfindex=0;
        int bestWeight = Integer.MIN_VALUE;

        SoldierMoves bestMove=null;
        SoldierMoves Move=null;

        for (Coordinate coordinate : possibleMoves){
                int weight;

                Move = new SoldierMoves(lBoard,gBoard,coordinate);
                weight=Move.weightSoldierMoves();

                if(!isSafe && Move.isSoldierNotInDanger()){
                    System.out.println("indexOfBestMove - 1| "+" weight: "+weight+" isSafe: "+Move.isSoldierNotInDanger()+" CoordinateMove: "+Move.getSoldierCoordinate());
                    isSafe=true;
                    bestWeight=weight;
                    bestMove=Move;
                    index=numOfindex;
                }
                else if(weight>bestWeight && Move.isSoldierNotInDanger()){
                    System.out.println("indexOfBestMove - 2| "+" weight: "+weight+" isSafe: "+Move.isSoldierNotInDanger()+" CoordinateMove: "+Move.getSoldierCoordinate());
                    isSafe=Move.isSoldierNotInDanger();
                    bestWeight=weight;
                    bestMove=Move;
                    index=numOfindex;
                }
                else if(weight>bestWeight && !isSafe){
                    System.out.println("indexOfBestMove - 3| "+" weight: "+weight+" isSafe: "+Move.isSoldierNotInDanger()+" CoordinateMove: "+Move.getSoldierCoordinate());
                    isSafe=Move.isSoldierNotInDanger();
                    bestWeight=weight;
                    bestMove=Move;
                    index=numOfindex;
                }
                else System.out.println("indexOfBestMove - 4| "+" weight: "+weight+" isSafe: "+Move.isSoldierNotInDanger()+" CoordinateMove: "+Move.getSoldierCoordinate());
            numOfindex++;
        }

        System.out.println("\nindexOfBestMove - bestMove: "+bestMove+"\nWeight: "+bestMove.weightSoldierMoves()+"\nisSafe: "+isSafe+"\nindex: "+index+"\n");
        System.out.println();
        return index;
    }

    //this function pop the best eat move from the stack
    private SoldierMoves findBestEat(Stack<SoldierMoves> eatSoldierMovesStack){
        
        Stack<SoldierMoves> copyEatSoldierMovesStack=new Stack<SoldierMoves>();
        SoldierMoves bestEatMoves=null;

        copyStack(copyEatSoldierMovesStack, eatSoldierMovesStack);

        while (!copyEatSoldierMovesStack.isEmpty()){
            if(bestEatMoves==null) bestEatMoves = copyEatSoldierMovesStack.pop();
            else if(bestEatMoves.weightSoldierMoves()<copyEatSoldierMovesStack.peek().weightSoldierMoves()) bestEatMoves = copyEatSoldierMovesStack.pop();
            else copyEatSoldierMovesStack.pop();
        }

        System.out.println("\nfindBestEat - bestEatMoves: "+bestEatMoves+"\nWeight: "+bestEatMoves.weightSoldierMoves()+"\n");

        return bestEatMoves;
    }

    //this function retun the best index of the possible Eat moves in the list
    private int indexOfBestEat(List<Coordinate[]> possibleEatMoves){

        int index=0;
        int numOfindex=0;
        int bestWeight = Integer.MIN_VALUE;

        SoldierMoves bestEatMoves=null;
        SoldierMoves EatMoves=null;

        for (Coordinate[] coordinate : possibleEatMoves){
                int weight;

                EatMoves = new SoldierMoves(lBoard,gBoard,coordinate[0]);
                weight=EatMoves.weightSoldierMoves();

                if(weight>bestWeight){
                    bestWeight=weight;
                    index=numOfindex;
                    bestEatMoves=EatMoves;
                }

            numOfindex++;
        }

        System.out.println("\nindexOfBestEat - bestEatMoves: "+bestEatMoves+"\nWeight: "+bestEatMoves.weightSoldierMoves()+"\n");

        return index;
    }

    //this function is pop random soldierMoves from stack
    private void popRandom(Stack<SoldierMoves> stack){
        int numOfpop=(int)(Math.random()*(stack.size()));

        while(numOfpop>0){
            stack.pop();
            numOfpop--;
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

    //find all the soldier of the computer that is not stuck
    public void findAllPossibleSoldier(){
        soldierMovesStack.clear();
        for(int i=0;i<lBoard.length;i++){
            for(int j=0;j<lBoard.length;j++){
                if(lBoard[i][j]==player_color){
                    soldierMovesStack.push(new SoldierMoves(lBoard,gBoard,new Coordinate(i,j,player_color)));
                    if(soldierMovesStack.peek().isSoldierStuck()) soldierMovesStack.pop();
                }
            }
        }
    }

    //this function is to find the max weight coordinate
    private int[] findMostWeightBlock(){
        int data[]=new int[3];
        int weight=0;
        for(int i=0;i<lBoard.length;i++){
            for(int j=0;j<lBoard.length;j++){
                if(gBoard[i][j].getWeight()>weight && lBoard[i][j]==0){
                    weight = gBoard[i][j].getWeight();
                    data[0]=i;
                    data[1]=j;
                    data[2]=weight;
                }
            }
        }

        return data;
    }

    //this function is printing test
    private void printTest(int[] test){
        for (int i : test) {
            System.out.print(i+" ");
        }
    }

    public int getDifficulty(){
        return difficulty;
    }

    public Stack<SoldierMoves> getSoldierMovesStack(){
        return soldierMovesStack;
    }

    public void copy(Computer computer){
        super.copy(computer);

        this.map=computer.map;
        copyBoard(lBoard, computer.lBoard);
        copyGBoard(gBoard, computer.gBoard);
        this.difficulty=computer.difficulty;
        this.soldierMovesStack=computer.soldierMovesStack;
        this.isSoldierNotLeftFirstTime=computer.isSoldierNotLeftFirstTime;
    }


    public void copyBoard(int[][] board, int[][] newBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
    }


    public void copyGBoard(GameButton [][]gBoard, GameButton [][]newGBoard) {
        for (int i = 0; i < gBoard.length; i++) {
            for (int j = 0; j < gBoard[i].length; j++) {
                newGBoard[i][j] = gBoard[i][j];
            }
        }
    }

    @Override
    public boolean IsHuman() {return false;}
}
