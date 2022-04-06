package code;

import java.util.List;
import java.util.Stack;

public class Computer extends Players {

    private final int difficulty;
    private int player_color; //1 red 2 black
    private int isSoldierNotLeftFirstTime;

    private int [][]lBoard;
    private GameButton [][]gBoard;

    private Stack<SoldierMoves> soldierMovesStack;
    
    private Players player;
    
    

    public Computer(int player_color, String map,int difficulty) {
        super(player_color, map);
        this.player_color = player_color;
        this.difficulty=difficulty;

        isSoldierNotLeftFirstTime =0;
        soldierMovesStack = new Stack<SoldierMoves>();
    }

    //this function is to manage the computer,isSoldierLeft-1(yes)/0(no)
    public int[] play(boolean isEaten,int [][]lBoard,GameButton [][]gBoard,Players player) {
        int test[]=new int[8];
        this.lBoard=lBoard;
        this.gBoard=gBoard;
        this.player=player;

        if(player.getSoldierLeft()==0) isSoldierNotLeftFirstTime++;

        if(isEaten && isSoldierNotLeftFirstTime<=1) test=addNewSolid();
        else test=move();

        soldierMovesStack.clear();

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

        findAllPossibleSoldier();

        copyStack(copySoldierMovesStack,soldierMovesStack);

        while(!copySoldierMovesStack.isEmpty()){
            if(!copySoldierMovesStack.peek().getPossibleEatMoves().isEmpty()) eatSoldierMovesStack.push(copySoldierMovesStack.peek());
            copySoldierMovesStack.pop();
        }
        
        
        if(!eatSoldierMovesStack.isEmpty()){
            System.out.println("eat");

            List<Coordinate[]> possibleEatMoves;

            if(difficulty == 0) popRandom(eatSoldierMovesStack);
            else popBestEat(eatSoldierMovesStack);

            possibleEatMoves=eatSoldierMovesStack.peek().getPossibleEatMoves();
            soldierCoordinate=eatSoldierMovesStack.peek().getSoldierCoordinate();

            size = possibleEatMoves.size();

            if(difficulty == 0) index=(int)(Math.random()*(size-1));
            else index=indexOfBestEat(possibleEatMoves);
            //System.out.println("index:"+index+" size-1:"+(size-1));

            test[0] = possibleEatMoves.get(index)[0].getRow();
            test[1] = possibleEatMoves.get(index)[0].getColumn();
            test[2] = soldierCoordinate.getRow();
            test[3] = soldierCoordinate.getColumn();
            test[4] = possibleEatMoves.get(index)[1].getRow();
            test[5] = possibleEatMoves.get(index)[1].getColumn();
            test[6] = 1;
        }
        else{
            System.out.println("move");
            
            List<Coordinate> possibleMoves;

            if(difficulty == 0) popRandom(soldierMovesStack);
            else popBestMove(soldierMovesStack);

            possibleMoves=soldierMovesStack.peek().getPossibleMoves();
            soldierCoordinate=soldierMovesStack.peek().getSoldierCoordinate();

            size = soldierMovesStack.peek().getPossibleMoves().size();

            if(difficulty == 0) index=(int)(Math.random()*(size-1));
            else index=indexOfBestMove(possibleMoves);
            //System.out.println("index:"+index+" size-1:"+(size-1));

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
    private void popBestMove(Stack<SoldierMoves> soldierMovesStack2){

    }
    //this function return the index of the best move in the list
    private int indexOfBestMove(List<Coordinate> possibleMoves){

        return 0;
    }

    //this function pop the best eat move from the stack
    private void popBestEat(Stack<SoldierMoves> eatSoldierMovesStack){
        
    }

    //this function retun the best index of the possible Eat moves in the list
    private int indexOfBestEat(List<Coordinate[]> possibleEatMoves){

        return 0;
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
    private void findAllPossibleSoldier(){
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
                if(gBoard[i][j].getWeight()>weight &&lBoard[i][j]==0){
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

    @Override
    public boolean IsHuman() {return false;}
}
