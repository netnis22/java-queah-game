package code;

import java.util.Scanner;
import java.awt.*;

public class Computer extends Players {

    public Computer(int player_color, String map) {
        super(player_color, map);
    }

    //this function is to manage the computer
    public int[] play(boolean isEaten,int [][]lBoard,GameButton [][]gBoard){
        int test[]=new int[7];

        if(isEaten) test=addNewSolid(lBoard,gBoard);
        else test=move(lBoard,gBoard);

        return test;
    }

    //return newRow,newColumn,previsRow,previsColumn, eatRow,eatColum, isEat- 1(yes)/0(no)
    //this function is for the computer to move the soldier
    private int[] move(int [][]lBoard,GameButton [][]gBoard){
        int test[]=new int[7];
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("enter number not eaten:");
        // for(int i=0;i<7;i++){
        //     test[i]= Integer.parseInt(scanner.nextLine());
        // }
        // return test;
        
        int[][] copyLBoard = lBoard;
        GameButton[][] copyGBoard=gBoard;

        int row ,column;
        Coordinate SoldierCoordinate,newCoordinate;

        for(int i=0;i<lBoard.length;i++){
            for(int j=0;j<lBoard.length;j++){
                if(lBoard[i][j]==player_color){
                    
                    if(isPlaterStuck(lBoard,i,j)) continue;

                    row = (int)(Math.random()*lBoard.length);
                    column = (int)(Math.random()*lBoard.length);
                    
                    newCoordinate=new Coordinate(row, column);
                    SoldierCoordinate=new Coordinate(i, j);

                    do{
                        row = (int)(Math.random()*lBoard.length);
                        column = (int)(Math.random()*lBoard.length);
                        newCoordinate=new Coordinate(row, column);
                    }while(!isValidMove(lBoard, SoldierCoordinate, newCoordinate));

                    test[0]=newCoordinate.getRow();
                    test[1]=newCoordinate.getColumn();
                    System.out.println(test[0]+" "+test[1]);
                    test[2]=i;
                    test[3]=j;

                    for(int k=4;k<test.length;k++){
                        test[i]=0;
                    }
                    return test;
                }
            }
        }
       // int data[]=findMostWeightBlock(copyLBoard,copyGBoard);
        
        //for(int i=0;i<7;i++) test[i]= Integer.parseInt(scanner.nextLine());

        return test;
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

        // if(!(previousRow == newRow && previousColumn == newColumn)){
        //     if(previousColumn == newColumn){
        //         if((previousRow == newRow+2 && (lBoard[newRow+1][newColumn] !=turn && lBoard[newRow+1][newColumn] !=0 ))){
        //             return true;
        //         } 
        //         else if((previousRow == newRow-2 && (lBoard[newRow-1][newColumn] !=turn && lBoard[newRow-1][newColumn] !=0 ))){
        //             return true;
        //         } 
                        
        //     }
        //     else if(previousRow == newRow){
        //         if((previousColumn == newColumn+2 && (lBoard[newRow][newColumn+1] !=turn && lBoard[newRow][newColumn+1] !=0 ))){
        //             return true;
        //         } 
        //         else if((previousColumn == newColumn-2 && (lBoard[newRow][newColumn-1] !=turn && lBoard[newRow][newColumn-1] !=0 ))){
        //             return true;
        //         } 
                        
        //     }
        // }
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
