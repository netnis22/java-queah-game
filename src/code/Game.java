package code;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame {

    String map="small";

    public Game() {
        
        //menuMap();

        Players playerRed = new Players(1,map);
        Players playerBlack = new Players(2,map);
        QueahBoard board = new QueahBoard(playerRed,playerBlack,map,Game.this);

        add(board,BorderLayout.CENTER);
        add(playerRed,BorderLayout.NORTH);
        add(playerBlack,BorderLayout.SOUTH);
        
        
        setTitle("Queah");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,350);
		setVisible(true);
    }

    public void menuMap(){
        String[] options =  {"small", "mid", "large","Exit"};
		int response = JOptionPane.showOptionDialog(null, "Choose Type map", 
				"Starting map Options",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		
		switch( response)
		{
			case -1:
				System.out.println("Option Dialog Window Was Closed");
				System.exit(0);
			
			case 0:
				map = "small";
				break;
			case 1:
				map = "mid";	
				break;
				
			case 2:
				map = "large";
				break;
				
			case  3:
				System.exit(0);
				
			default:
				break;		
		}

    }
    public static void main(String[] args)
    {
           new Game();
    }
}
