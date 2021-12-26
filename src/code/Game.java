package code;

import java.awt.*;
import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {
        Players playerRed = new Players(1,6,4);
        Players playerBlack = new Players(2,6,4);
        QueahBoard board = new QueahBoard(playerRed,playerBlack);

        add(board,BorderLayout.CENTER);
        add(playerRed,BorderLayout.NORTH);
        add(playerBlack,BorderLayout.SOUTH);
        
        // playerRed.removeSoldier();

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
