package code;

import java.util.Scanner;

public class Computer extends Players {

    public Computer(int player_color, String map) {
        super(player_color, map);
    }

    //return newRow,newColumn,previsRow,previsColumn, eatRow,eatColum, isEat- 1(yes)/0(no)
    public int[] move() {
        // int test[]=new int[7];
        // Scanner scanner = new Scanner(System.in);
        // for(int i=0;i<7;i++){
        //     test[i]= Integer.parseInt(scanner.nextLine());
        // }
        int test[]= {2,4,2,3,0,0,0};
        return test;
    }

    @Override
    public boolean IsHuman() {return false;}
}
