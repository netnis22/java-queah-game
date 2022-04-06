package code;

import java.util.Scanner;
import java.awt.*;
import java.io.*;

import java.util.*;

public class Computer extends Players {

    private int player_color; //1 red 2 black
    private Stack<SoldierMoves> soldierMovesStack;
    private int [][]lBoard;
    private GameButton [][]gBoard;
    private Players player;
    private int isSoldierNotLeftFirstTime;

    public Computer(int player_color, String map) {
        super(player_color, map);
        this.player_color = player_color;
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

        System.out.println(player.getSoldierLeft()!=0);
        if(isSoldierNotLeftFirstTime<=1) test[7]=1;
        else test[7]=0;

        for(int i=2;i<7;i++) test[i]= 0;
        return test;
    }


    //return newRow,newColumn,previsRow,previsColumn, eatRow,eatColum, isEat- 1(yes)/0(no), isSoldierLeft-1(yes)/0(no)
    //this function is for the computer to move the soldier
    private int[] move(){
        int test[]=new int[8];
        Stack<SoldierMoves> copySoldierMovesStack=new Stack<SoldierMoves>();
        Stack<SoldierMoves> eatSoldierMovesStack=new Stack<SoldierMoves>();

        findAllPossibleSoldier();

        copyStack(copySoldierMovesStack,soldierMovesStack);

        while(!copySoldierMovesStack.isEmpty()){
            if(!copySoldierMovesStack.peek().getPossibleEatMoves().isEmpty()) eatSoldierMovesStack.push(copySoldierMovesStack.peek());
            copySoldierMovesStack.pop();
        }
        
        int index;
        if(!eatSoldierMovesStack.isEmpty()){
            System.out.println("eat");

            int size = eatSoldierMovesStack.peek().getPossibleEatMoves().size();
            index=(int)(Math.random()*(size-1));
            //System.out.println("index:"+index+" size-1:"+(size-1));

            test[0]=eatSoldierMovesStack.peek().getPossibleEatMoves().get(index)[0].getRow();
            test[1]=eatSoldierMovesStack.peek().getPossibleEatMoves().get(index)[0].getColumn();
            test[2]=eatSoldierMovesStack.peek().getsoldierCoordinate().getRow();
            test[3]=eatSoldierMovesStack.peek().getsoldierCoordinate().getColumn();
            test[4]=eatSoldierMovesStack.peek().getPossibleEatMoves().get(index)[1].getRow();
            test[5]=eatSoldierMovesStack.peek().getPossibleEatMoves().get(index)[1].getColumn();
            test[6]=1;
        }
        else{
            System.out.println("move");
            
            int size = soldierMovesStack.peek().getPossibleMoves().size();
            index=(int)(Math.random()*(size-1));
            //System.out.println("index:"+index+" size-1:"+(size-1));

            test[0]=soldierMovesStack.peek().getPossibleMoves().get(index).getRow();
            test[1]=soldierMovesStack.peek().getPossibleMoves().get(index).getColumn();
            test[2]=soldierMovesStack.peek().getsoldierCoordinate().getRow();
            test[3]=soldierMovesStack.peek().getsoldierCoordinate().getColumn();
            test[4]=0;
            test[5]=0;
            test[6]=0;
        }

        if(isSoldierNotLeftFirstTime<=1) test[7]=1;
        else test[7]=0;
        
        return test;
    }

    private void printTest(int[] test){
        for (int i : test) {
            System.out.print(i+" ");
        }
    }

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

    @Override
    public boolean IsHuman() {return false;}
}
