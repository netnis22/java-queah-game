package code;

import java.awt.*;
import javax.swing.*;
import javax.swing.JPanel;

public class Players extends JPanel {

    protected int soldier_left;
    protected int soldier_on_board;
    protected final int player_color; // 1 red | 2 black 

    protected String map;

    private static final ImageIcon RedSoldier = new ImageIcon("././img/Soldier_red_New.png");
    private static final ImageIcon BlackSoldier =  new ImageIcon("././img/Soldier_black_New.png");
    private JLabel img;

    public Players(int player_color,String map){
        this.player_color = player_color;
        this.map = map;
        int soldiers;

        if(map.equals("small")){
            soldier_on_board=4;
            soldiers=10;
        }
        else if(map.equals("mid")){
            soldier_on_board=6;
            soldiers=14;
        }
        else if(map.equals("large"))
        {
            soldier_on_board=8;
            soldiers=18;
        }
        else
        {
            soldier_on_board=4;
            soldiers=10;
        }
        this.soldier_left=soldiers-soldier_on_board;

        setLayout(new GridLayout(1,soldier_left));
        drawSoldier_left();
    }

    public int getPlayer_color() {
        return player_color;
    }

    public int getSoldier_on_board() {
        return soldier_on_board;
    }

    public int getSoldierLeft() {
        return soldier_left;
    }

    public String getMap() {
        return map;
    }

    public void copy(Players player){
        this.soldier_left=player.soldier_left;
        this.soldier_on_board=player.soldier_on_board;
    }

    // 0 no soldier left | 1 soldier on board is 4 and thir is soldier_left
    public boolean removeSoldierFromStack() {
        if(soldier_left <= 0) return false;

        soldier_left -= 1;
        remove(soldier_left);
        revalidate();
        return true;
    }

    public boolean removeSoldierFromBoard()
    {
        if(soldier_on_board <= 0) return false;

        soldier_on_board -= 1;
        return true;
    }

    public boolean addSoldierToBoard(int max_soldier_on_board){
        if(max_soldier_on_board<soldier_on_board+1) return false;

        soldier_on_board += 1;
        return true;
    }

    public boolean isSoldiersLeft() {
        if (soldier_left == 0 && soldier_on_board ==0) return true;
        return false;
    }

   public void drawSoldier_left(){   
        for(int i = 0; i <soldier_left;i++){
            if(player_color==1) img = new JLabel(RedSoldier);
            else img = new JLabel(BlackSoldier);
            add(img,i);
        }
   }

   public boolean IsHuman() {return true;}

//    public int getWeight(int [][]lBoard){

//    }
}
