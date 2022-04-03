package code;

import java.util.Scanner;
import java.awt.*;
import java.io.*;

import java.util.*;

public class Computer extends Players {

    private int turn;

    public Computer(int player_color, String map) {
        super(player_color, map);
        turn=player_color;
    }

    //this function is to manage the computer
    public int[] play(boolean isEaten,int [][]lBoard,GameButton [][]gBoard){
        int test[]=new int[7];

        if(isEaten) test=addNewSolid(lBoard,gBoard);
        else test=move(lBoard,gBoard);

        return test;
    }

    //find all the soldier of the computer that is not stuck
    private Stack<Coordinate> findAllPossibleSoldier(int [][]lBoard,GameButton [][]gBoard){
        Stack<Coordinate> comSoldierStack = new Stack<Coordinate>();
        for(int i=0;i<lBoard.length;i++){
            for(int j=0;j<lBoard.length;j++){
                if(lBoard[i][j]==player_color){
                    if(isPlaterStuck(lBoard,i,j)) continue;
                    comSoldierStack.push(new Coordinate(i,j));
                }
            }
        }
        return comSoldierStack;
    }

    //return newRow,newColumn,previsRow,previsColumn, eatRow,eatColum, isEat- 1(yes)/0(no)
    //this function is for the computer to move the soldier
    private int[] move(int [][]lBoard,GameButton [][]gBoard){
        int test[]=new int[7];
        Stack<Coordinate> comSoldierStack = findAllPossibleSoldier(lBoard, gBoard);
        
        // int[][] copyLBoard = lBoard;
        // GameButton[][] copyGBoard=gBoard;

        int row ,column;
        Coordinate soldierCoordinate,newCoordinate;


        while(!comSoldierStack.isEmpty()){
            int conter=0;
            Coordinate afterEatCoordinate=null;

            boolean isEat=false;
            boolean isValidMove=false;

            row = (int)(Math.random()*lBoard.length);
            column = (int)(Math.random()*lBoard.length);
            
            newCoordinate=new Coordinate(row, column);
            soldierCoordinate=comSoldierStack.pop();

            do{
                conter++;
                row = (int)(Math.random()*lBoard.length);
                column = (int)(Math.random()*lBoard.length);
                newCoordinate=new Coordinate(row, column);
                isValidMove=isValidMove(lBoard, soldierCoordinate, newCoordinate);
                // afterEatCoordinate=canEat(lBoard, soldierCoordinate, newCoordinate);
                // if((afterEatCoordinate.getRow()!=-1 && afterEatCoordinate.getColumn()!=-1)) isEat=true;
                // if(isEat) break;
                //System.out.println("newCoordinate: "+newCoordinate.getRow()+" "+newCoordinate.getColumn());
            }while(!isValidMove || conter<1000000);

            if(conter==100) continue;

            test[2]=soldierCoordinate.getRow();
            test[3]=soldierCoordinate.getColumn();
            test[0]=newCoordinate.getRow();
            test[1]=newCoordinate.getColumn();
            for(int k=4;k<test.length;k++){
                test[k]=0;
            }


            // if(!isValidMove && isEat){
            //     System.out.println("heee");
            //     test[0]=afterEatCoordinate.getRow();
            //     test[1]=afterEatCoordinate.getColumn();
            //     test[4]=newCoordinate.getRow();
            //     test[5]=newCoordinate.getColumn();
            //     test[6]=1;
            // }
            // if(isValidMove && !isEat){
            //     System.out.println("nnnnnn");
            //     test[0]=newCoordinate.getRow();
            //     test[1]=newCoordinate.getColumn();
            //     for(int k=4;k<test.length;k++){
            //         test[k]=0;
            //     }
            // }

            for(int i=0;i<test.length;i++){
                System.out.print(test[i]+" ");
            }
            System.out.println();
            
            return test;
        }
        return test;
    }

    private Coordinate canEat(int [][]lBoard, Coordinate SoldierCoordinate, Coordinate newCoordinate){
        Coordinate eatCoordinate = new Coordinate(-1,-1);
        int previousRow=SoldierCoordinate.getColumn();
        int previousColumn=SoldierCoordinate.getRow();
        int newColumn=newCoordinate.getColumn();
        int newRow=newCoordinate.getRow();

        if(!(previousRow == newRow && previousColumn == newColumn)){
            if(previousColumn == newColumn){
                if((previousRow == newRow+2 && (lBoard[newRow+1][newColumn] !=turn && lBoard[newRow+1][newColumn] !=0 ))){
                    eatCoordinate.setRow(newRow+2);
                    eatCoordinate.setColumn(newColumn);
                    return eatCoordinate;
                } 
                else if((previousRow == newRow-2 && (lBoard[newRow-1][newColumn] !=turn && lBoard[newRow-1][newColumn] !=0 ))){
                    eatCoordinate.setRow(newRow-2);
                    eatCoordinate.setColumn(newColumn);
                    return eatCoordinate;
                } 
                        
            }
            else if(previousRow == newRow){
                if((previousColumn == newColumn+2 && (lBoard[newRow][newColumn+1] !=turn && lBoard[newRow][newColumn+1] !=0 ))){
                    eatCoordinate.setRow(newRow);
                    eatCoordinate.setColumn(newColumn+2);
                    return eatCoordinate;
                } 
                else if((previousColumn == newColumn-2 && (lBoard[newRow][newColumn-1] !=turn && lBoard[newRow][newColumn-1] !=0 ))){
                    eatCoordinate.setRow(newRow);
                    eatCoordinate.setColumn(newColumn-2);
                    return eatCoordinate;
                } 
                        
            }
        }
        return eatCoordinate;
    }

    private boolean isValidMove(int [][]lBoard, Coordinate SoldierCoordinate, Coordinate newCoordinate){
         
        int previousRow=SoldierCoordinate.getColumn();
        int previousColumn=SoldierCoordinate.getRow();
        int newColumn=newCoordinate.getColumn();
        int newRow=newCoordinate.getRow();
        
        if(((previousRow == newRow+1 || previousRow == newRow-1) && previousColumn == newColumn) || ((previousColumn == newColumn+1 || previousColumn == newColumn-1) && previousRow == newRow )){
            if(lBoard[newRow][newColumn] == 0){
                if(!(previousRow == newRow && previousColumn == newColumn)){
                    return true; 
                }
            }
        }


        return false;
    }

    private boolean isPlaterStuck(int[][] lBoard,int row,int column){
        if((column-1<0 || lBoard[row][column-1] !=0) && (column+1>lBoard.length || lBoard[row][column+1] !=0) && (row+1>lBoard.length || lBoard[row+1][column] !=0) && (row-1<0 || lBoard[row-1][column] !=0)) return true;
        return false;
    }

    
    //return newRow,newColumn,0,0,0,0,0
    //this function is for the computer to add new soldier if soldier is eaten
    private int[] addNewSolid(int [][]lBoard,GameButton [][]gBoard){
        int[][] copyLBoard = lBoard;
        int test[]=new int[7];
        int data[]=findMostWeightBlock(copyLBoard,gBoard);


        test[0]=data[0];
        test[1]=data[1];

        for(int i=2;i<7;i++) test[i]= 0;

        return test;
    }

    //this function is to find the max weight coordinate
    private int[] findMostWeightBlock(int [][]lBoard,GameButton [][]gBoard){
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
