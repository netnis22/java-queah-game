package code;

import java.awt.*;
import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {
        String map ="mid";

        Players playerRed = new Players(1,map);
        Players playerBlack = new Players(2,map);
        QueahBoard board = new QueahBoard(playerRed,playerBlack,map,Game.this);

        add(board,BorderLayout.CENTER);
        add(playerRed,BorderLayout.NORTH);
        add(playerBlack,BorderLayout.SOUTH);
        
        board.midMapSolid();
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
