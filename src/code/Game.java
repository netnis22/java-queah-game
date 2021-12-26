package code;

import java.awt.*;
import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {
        QueahBoard board = new QueahBoard();
        add(board,BorderLayout.CENTER);
        setTitle("Queah");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,300);
		setVisible(true);
    }
    public static void main(String[] args)
    {
           new Game();
    }
}
