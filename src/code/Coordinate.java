package code;

import java.util.Set;

public class Coordinate {
    private int row,column;

    public Coordinate(int row, int y) {
        this.row = row;
        this.column = y;
    }

    public void SetX(int row){
        this.row=row;
    }
    public void SetColumn(int y){
        this.column=y;
    }

    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }    
}
