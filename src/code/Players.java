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

    public Players(int player_color,int soldiers,int soldier_on_board){
        this.player_color = player_color;
        this.soldier_on_board = soldier_on_board;
        this.soldier_left=soldiers-soldier_on_board;

        setLayout(new GridLayout(1,soldier_left));
        drawSoldier_left();
    }

    public int getSoldier_left() {
        return soldier_left;
    }

    public int getPlayer_color() {
        return player_color;
    }

    public int getSoldier_on_board() {
        return soldier_on_board;
    }

    public void setSoldier_on_board(int soldier_on_board) {
        this.soldier_on_board = soldier_on_board;
    }

    public void setSoldier_left(int soldier_left) {
        this.soldier_left = soldier_left;
    }

    public int removeSoldier() {
        if(soldier_left <= 0){
            if(soldier_on_board == 0) return 0;
            else
            {
                soldier_on_board -= 1;
                return 0;
            }
        }
        else soldier_left -= 1;
        remove(soldier_left);
        revalidate();
        return 0;
    }

   public void drawSoldier_left(){   
        for(int i = 0; i <soldier_left;i++){
            if(player_color==1) img = new JLabel(RedSoldier);
            else img = new JLabel(BlackSoldier);
            add(img,i);
        }
   }
}
