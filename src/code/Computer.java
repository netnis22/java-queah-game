package code;

import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter namber not eten:");
        for(int i=0;i<7;i++){
            test[i]= Integer.parseInt(scanner.nextLine());
        }
        return test;

        /*
            int[][] copyLBoard = lBoard;
        GameButton[][] copyGBoard=gBoard;
        int test[]=new int[7];

        for(int i=0;i<lBoard.length;i++){
            for(int j=0;j<lBoard.length;j++){
                if(lBoard[i][j]==player_color){
                    
                }
            }
        }


        int data[]=findMostWeightBlock(copyLBoard,copyGBoard);
        
        //for(int i=0;i<7;i++) test[i]= Integer.parseInt(scanner.nextLine());

        return test;
        */
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
