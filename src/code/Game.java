package code;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame {

    String map;
	int tarn;

    public Game() {
        
        menuMap();

        Players playerRed = new Players(1,map);
        Players playerBlack = new Players(2,map);
        QueahBoard board = new QueahBoard(playerRed,playerBlack,map,Game.this,tarn);

        mapSolid(board);

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
				System.out.println("map Dialog Window Was Closed");
				System.exit(0);
			
			case 0:
				map = "small";
				startMap();
				break;
			case 1:
				map = "mid";
				startMap();	
				break;
				
			case 2:
				map = "large";
				startMap();
				break;
				
			case  3:
				System.exit(0);
				
			default:
				break;		
		}
    }

    public void startMap(){
		String[] options =  {"RED", "BLACK","Back"};
		int response = JOptionPane.showOptionDialog(null, "Choose how start?", 
				"start player",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		
		switch(response)
		{
			case -1:
				System.out.println("map Dialog Window Was Closed");
				System.exit(0);
			
			case 0:
				tarn = 1;
				break;
			case 1:
				tarn = 2;	
				break;
			case  2:
			menuMap();
			default:
				break;		
		}
    }
	
	public void mapSolid(QueahBoard board){
        switch (map) {
            case "small":
                board.smallMapSolid();
                break;
            case "mid":
                board.midMapSolid();
                break;
            case "large":
            board.largeMapSolid();
                break;
            default:
                board.smallMapSolid();
                break;
        }
    }

    public static void main(String[] args)
    {
           new Game();
    }
}
