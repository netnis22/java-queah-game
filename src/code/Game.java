package code;

import java.awt.*;
import java.io.IOException;
import java.nio.file.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Game extends JFrame {

    String map;
	int tarn;
	int gameMode=0;

    public Game() {
        
        menuMap();
		Players playerRed;
		Players playerBlack;
		QueahBoard board;

		switch (gameMode) {
			case 0:
				playerRed = new Players(1,map);
				playerBlack = new Players(2,map);
				board = new QueahBoard(playerRed,playerBlack,map,Game.this,tarn);
				break;
			case 1:
				playerRed = new Computer(1,map);
				playerBlack = new Players(2,map);
				board = new QueahBoard(playerRed,playerBlack,map,Game.this,tarn);
				break;
			case 2:
				playerBlack = new Computer(1,map);
				playerRed = new Computer(2,map);
				board = new QueahBoard(playerRed,playerBlack,map,Game.this,tarn);
				break;
			default:
				playerRed = new Players(1,map);
				playerBlack = new Players(2,map);
				board = new QueahBoard(playerRed,playerBlack,map,Game.this,tarn);
				break;
		}
        
        

        mapSolid(board);

        add(board,BorderLayout.CENTER);
        add(playerRed,BorderLayout.NORTH);
        add(playerBlack,BorderLayout.SOUTH);
        
        
        setTitle("Queah");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,350);
		setVisible(true);
    }

	private static String getFileInfo(){ 
		String path = "src/Game_Play_and_Rules.txt";

        try {
            String content = Files.readString(Paths.get(path));
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
	

    public void menuMap(){
        String[] options =  {"small", "mid", "large","Rules","Exit"};
		int response = JOptionPane.showOptionDialog(null, "Choose Type map", 
				"Starting map Options",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		
		switch(response)
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

			case 3:
			    Runnable  rule = () -> {String html =("<html><body width='%1s'><h1>Rules</h1><p>"+getFileInfo());
				JOptionPane.showMessageDialog(Game.this, String.format(html, 500, 500));
				};
				SwingUtilities.invokeLater(rule);
				menuMap();
				break;
			case  4:
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
				gameModeMenu();
				break;
			case 1:
				tarn = 2;	
				gameModeMenu();
				break;
			case  2:
				menuMap();
			default:
				break;		
		}
    }

	public void gameModeMenu(){
		String[] options =  {"Human Vs Human", "Human Vs Computer","Computer Vs Computer"};
		int response = JOptionPane.showOptionDialog(null, "what Game Mode?", 
				"Game Mode",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		
		switch(response)
		{
			case -1:
				System.out.println("gameModeMenu Dialog Window Was Closed");
				System.exit(0);
			
			case 0:
				gameMode = 0;
				break;
			case 1:
				gameMode = 1;
				break;
			case  2:
				gameMode = 2;
				break;
			case 3:
				startMap();
				break;
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
