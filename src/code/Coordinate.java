package code;

public class Coordinate {
    private int row,column,value;

    public Coordinate(int row, int column,int value) {
        this.row = row;
        this.column = column;
        this.value=value; //-1 null 0 empty 1 red 2 black
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setRow(int row){
        this.row=row;
    }
    public void setColumn(int column){
        this.column=column;
    }

    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }  
    
    public int getValue() {
        return value;
    }

    @Override
    public String toString(){
        return ("row:"+row+" column:"+column+" value:"+value);
    }
}
