package code;

public class Computer extends Players {

    public Computer(int player_color, String map) {
        super(player_color, map);
    }

    //return newRow,newColumn,previsRow,previsColumn, eatRow,eatColum, isEat- 1(yes)/0(no)
    public int[] move() {
        int test[]= {2,4,2,3,0,0,0};
        return test;
    }

    @Override
    public boolean IsHuman() {return false;}
}
