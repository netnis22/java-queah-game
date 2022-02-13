package code;

import java.util.Scanner;

public class Computer extends Players {

    public Computer(int player_color, String map) {
        super(player_color, map);
    }

    //return newRow,newColumn,previsRow,previsColumn, eatRow,eatColum, isEat- 1(yes)/0(no)
    public int[] move(boolean isEaten){
        int test[]=new int[7];
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter namber not eten:");
        for(int i=0;i<7;i++){
            test[i]= Integer.parseInt(scanner.nextLine());
        }
         return test;
    }

    public int[] play(boolean isEaten){
        int test[]=new int[7];
        if(isEaten){
         a
        }
    }

    public int[] addNewSolid(){
        int test[]=new int[7];
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter namber new solder:");
        for(int i=0;i<2;i++){
            test[i]= Integer.parseInt(scanner.nextLine());
        }
        for(int i=2;i<7;i++){
            test[i]= 0;
        }
        return test;
    }

    @Override
    public boolean IsHuman() {return false;}
}
