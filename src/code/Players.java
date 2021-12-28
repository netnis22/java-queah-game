package code;

import java.awt.*;
import javax.swing.*;
import javax.swing.JPanel;

public class Players extends JPanel {

    private int soldier_left;
    private int soldier_on_board;
    private final int player_color; // 1 red | 2 black 

    private static final ImageIcon RedSoldier = new ImageIcon("src/img/Soldier_red.png");
    private static final ImageIcon BlackSoldier =  new ImageIcon("src/img/Soldier_black.png");
    private JLabel img;

    public Players(int player_color,int soldiers,String map){
        this.player_color = player_color;
        if(map.equals("small")) soldier_on_board=4;
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
}
