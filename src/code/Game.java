package code;

import java.awt.*;
import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {
        String map ="small";

        Players playerRed = new Players(1,10,map);
        Players playerBlack = new Players(2,10,map);
        QueahBoard board = new QueahBoard(playerRed,playerBlack,map,Game.this);

        add(board,BorderLayout.CENTER);
        add(playerRed,BorderLayout.NORTH);
        add(playerBlack,BorderLayout.SOUTH);
        
        board.smallMapSolid();
        setTitle("Queah");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,350);
		setVisible(true);
    }
    public static void main(String[] args)
    {
           new Game();
    }
}
