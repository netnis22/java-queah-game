package code;

import java.util.*;

public class SoldierMoves {

    private int player_color;

    private int [][]lBoard;
    private GameButton [][]gBoard;

    private Coordinate up, down, left, right;
    private Coordinate[] twoUp, twoDown, twoLeft, twoRight;
    private Coordinate soldierCoordinate;

    private List<Coordinate> possibleMoves;
    private List<Coordinate[]> possibleEatMoves;
    private List<Coordinate> coordinatesOfEnemySoldiercanNotEat;
    
    //this function is a constructor
    public SoldierMoves(int [][]lBoard,GameButton [][]gBoard,Coordinate soldierCoordinate){
        this.lBoard=lBoard;
        this.gBoard=gBoard;
        this.soldierCoordinate=soldierCoordinate;
        this.player_color=soldierCoordinate.getValue(); // 0 empty 1 red 2 black
        

        possibleMoves=new ArrayList<Coordinate>();
        possibleEatMoves=new ArrayList<Coordinate[]>();
        coordinatesOfEnemySoldiercanNotEat=new ArrayList<Coordinate>();

        scannMap();
        findPossibleMoves();
        findPossibleEatMoves();
        findCoordinatesOfEnemySoldiercanNotEat();
    }

    //this function scann the map and update the directions and the Two_directions
    public void scannMap(){
        if(soldierCoordinate.getRow()+1>=lBoard.length) up=new Coordinate(-1,-1,-1);
        else up=new Coordinate(soldierCoordinate.getRow()+1,soldierCoordinate.getColumn(),lBoard[soldierCoordinate.getRow()+1][soldierCoordinate.getColumn()]);

        if(soldierCoordinate.getRow()-1<0) down=new Coordinate(-1,-1,-1);
        else down=new Coordinate(soldierCoordinate.getRow()-1,soldierCoordinate.getColumn(),lBoard[soldierCoordinate.getRow()-1][soldierCoordinate.getColumn()]);

        if(soldierCoordinate.getColumn()+1>=lBoard.length) right=new Coordinate(-1,-1,-1);
        else right=new Coordinate(soldierCoordinate.getRow(),soldierCoordinate.getColumn()+1,lBoard[soldierCoordinate.getRow()][soldierCoordinate.getColumn()+1]);

        if(soldierCoordinate.getColumn()-1<0) left=new Coordinate(-1,-1,-1);
        else left=new Coordinate(soldierCoordinate.getRow(),soldierCoordinate.getColumn()-1,lBoard[soldierCoordinate.getRow()][soldierCoordinate.getColumn()-1]);

        if(soldierCoordinate.getRow()+2>=lBoard.length){
            twoUp=new Coordinate[2];
            twoUp[0]=new Coordinate(-1,-1,-1);
            twoUp[1]=new Coordinate(-1,-1,-1);
        }
        else{
            twoUp=new Coordinate[2];
            twoUp[0]=new Coordinate(soldierCoordinate.getRow()+2,soldierCoordinate.getColumn(),lBoard[soldierCoordinate.getRow()+2][soldierCoordinate.getColumn()]);
            twoUp[1]=up;
        }

        if(soldierCoordinate.getRow()-2<0){
            twoDown=new Coordinate[2];
            twoDown[0]=new Coordinate(-1,-1,-1);
            twoDown[1]=new Coordinate(-1,-1,-1);
        }
        else{
            twoDown=new Coordinate[2];
            twoDown[0]=new Coordinate(soldierCoordinate.getRow()-2,soldierCoordinate.getColumn(),lBoard[soldierCoordinate.getRow()-2][soldierCoordinate.getColumn()]);
            twoDown[1]=down;
        }

        if(soldierCoordinate.getColumn()+2>=lBoard.length){
            twoRight=new Coordinate[2];
            twoRight[0]=new Coordinate(-1,-1,-1);
            twoRight[1]=new Coordinate(-1,-1,-1);
        }
        else{
            twoRight=new Coordinate[2];
            twoRight[0]=new Coordinate(soldierCoordinate.getRow(),soldierCoordinate.getColumn()+2,lBoard[soldierCoordinate.getRow()][soldierCoordinate.getColumn()+2]);
            twoRight[1]=right;
        }

        if(soldierCoordinate.getColumn()-2<0){
            twoLeft=new Coordinate[2];
            twoLeft[0]=new Coordinate(-1,-1,-1);
            twoLeft[1]=new Coordinate(-1,-1,-1);
        }
        else{
            twoLeft=new Coordinate[2];
            twoLeft[0]=new Coordinate(soldierCoordinate.getRow(),soldierCoordinate.getColumn()-2,lBoard[soldierCoordinate.getRow()][soldierCoordinate.getColumn()-2]);
            twoLeft[1]=left;
        }
    }

    //returns the list of possible moves
    public void findPossibleMoves(){
        if(up.getValue()==0){
            possibleMoves.add(up);
        }
        if(down.getValue()==0){
            possibleMoves.add(down);
        }
        if(right.getValue()==0){
            possibleMoves.add(right);
        }
        if(left.getValue()==0){
            possibleMoves.add(left);
        }
    }

    //returns the list of possible eat moves
    public void findPossibleEatMoves(){
        if(twoUp[0].getValue()==0 && twoUp[1].getValue() != player_color && twoUp[1].getValue() != 0){
            possibleEatMoves.add(twoUp);
        }
        if(twoDown[0].getValue()==0 && twoDown[1].getValue() != player_color && twoDown[1].getValue() != 0){
            possibleEatMoves.add(twoDown);
        }
        if(twoRight[0].getValue()==0 && twoRight[1].getValue() != player_color && twoRight[1].getValue() != 0){
            possibleEatMoves.add(twoRight);
        }
        if(twoLeft[0].getValue()==0 && twoLeft[1].getValue() != player_color && twoLeft[1].getValue() != 0){
            possibleEatMoves.add(twoLeft);
        }
        
    }

    //returns the list of coordinates of enemy soldier that can not be eaten
    public void findCoordinatesOfEnemySoldiercanNotEat(){
        if(up.getValue()!=0 && up.getValue() !=-1 && up.getValue()!=player_color && twoUp[0].getValue()!=0 && twoUp[0].getValue()!=-1){
            coordinatesOfEnemySoldiercanNotEat.add(up);
        }
        if(down.getValue()!=0 && down.getValue() !=-1 && down.getValue()!=player_color && twoDown[0].getValue()!=0 && twoDown[0].getValue()!=-1){
            coordinatesOfEnemySoldiercanNotEat.add(down);
        }
        if(right.getValue()!=0 && right.getValue() !=-1 && right.getValue()!=player_color && twoRight[0].getValue()!=0 && twoRight[0].getValue()!=-1){
            coordinatesOfEnemySoldiercanNotEat.add(right);
        }
        if(left.getValue()!=0 && left.getValue() !=-1 && left.getValue()!=player_color && twoLeft[0].getValue()!=0 && twoLeft[0].getValue()!=-1){
            coordinatesOfEnemySoldiercanNotEat.add(left);
        }
    }

    //this function is to find if the soldier is stuck
    public boolean isSoldierStuck(){
        if(possibleMoves.isEmpty() && possibleEatMoves.isEmpty()){
            return true;
        }
        return false;
    }

    public List<Coordinate> getCoordinatesOfEnemySoldier() {
        return coordinatesOfEnemySoldiercanNotEat;
    }

    public List<Coordinate[]> getPossibleEatMoves() {
        return possibleEatMoves;
    }

    public List<Coordinate> getPossibleMoves() {
        return possibleMoves;
    }

    public Coordinate getSoldierCoordinate() {
        return soldierCoordinate;
    }

    @Override
    public String toString(){
        return("\n"+"up:"+up+" down:"+down+" left:"+left+" right:"+right+"\n"+"twoUp:"+twoUp[0]+" twoDown:"+twoDown[0]+" twoLeft:"+twoLeft[0]+" twoRight"+twoRight[0]+"\n"+"possibleMoves:"+possibleMoves+"\n"+"possibleEatMoves:"+possibleEatMoves+"\n"+"coordinatesOfEnemySoldiercanNotEat:"+coordinatesOfEnemySoldiercanNotEat+"\n"+"soldierCoordinate:"+soldierCoordinate);
    }
}
