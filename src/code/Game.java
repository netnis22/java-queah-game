package code;

import java.awt.*;
import java.io.IOException;
import java.nio.file.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Game extends JFrame {

    public String map;

	public int turn;
	public int gameMode=0;
	public int gameStart=1;
	private int difficultyComputer1; // 0 easy | 1 medium | 2 hard
	private int difficultyComputer2; // 0 easy | 1 medium | 2 hard

	public Players playerRed;
	public Players playerBlack;

	public Computer computerRed;
	public Computer computerBlack;
	private QueahBoard board;

    public Game() {
        
        menuMap();

        mapSolid(board);

        add(board,BorderLayout.CENTER);
    
        setTitle("Queah");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,350);
		setVisible(true);
		setLocationRelativeTo(null);
    }

	private static String getFileInfo(){ 
		String path = "././files/Game_Play_and_Rules.txt";

        try {
            String content = Files.readString(Paths.get(path));
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
	

    private void menuMap(){
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

    private void startMap(){
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
				turn = 1;
				gameModeMenu();
				break;
			case 1:
				turn = 2;	
				gameModeMenu();
				break;
			case  2:
				menuMap();
			default:
				break;		
		}
    }

	private void gameModeMenu(){
		String[] options =  {"Human Vs Human", "Human Vs Computer","Computer Vs Computer","Back"};
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
				gameMode=0;
				playerRed = new Players(1,map);
				playerBlack = new Players(2,map);
				board = new QueahBoard(Game.this);
				add(playerRed,BorderLayout.NORTH);
        		add(playerBlack,BorderLayout.SOUTH);
				break;
			case 1:
				gameMode = 1;
				difficultyComputer2();
				playerRed =  new Players(1,map);
				computerBlack =new Computer(2,playerRed,map,difficultyComputer2);
				board = new QueahBoard(Game.this);
				add(playerRed,BorderLayout.NORTH);
				add(computerBlack,BorderLayout.SOUTH);
				break;
			case  2:
				gameMode = 2;
				difficultyComputer1();
				computerRed = new Computer(1,computerBlack,map,difficultyComputer1);
				computerBlack = new Computer(2,computerRed,map,difficultyComputer2);
				board = new QueahBoard(Game.this);
				add(computerRed,BorderLayout.NORTH);
				add(computerBlack,BorderLayout.SOUTH);
				break;
			case 3:
				startMap();
				break;
			default:
				break;		
		}
    }

	private void difficultyComputer1(){
		String[] options =  {"eazy", "medium","hard","Back"};
		int response = JOptionPane.showOptionDialog(null, "what difficulty computer 1?", 
				"difficulty For Computer 1",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		
		switch(response)
		{
			case -1:
				System.out.println("difficulty Dialog Window Was Closed");
				System.exit(0);
			
			case 0:
				difficultyComputer1 = 0;
				difficultyComputer2();
				break;
			case 1:
				difficultyComputer1 = 1;
				difficultyComputer2();
				break;
			case  2:
				difficultyComputer1 = 2;
				difficultyComputer2();
				break;
			case 3:
				gameModeMenu();
				break;
			default:
				break;		
		}
    }

	private void difficultyComputer2(){
		String[] options =  {"eazy", "medium","hard","Back"};
		int response = JOptionPane.showOptionDialog(null, "what difficulty computer 2?", 
				"difficulty For Computer 2",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		
		switch(response)
		{
			case -1:
				System.out.println("difficulty Dialog Window Was Closed");
				System.exit(0);
			
			case 0:
				difficultyComputer2 = 0;
				break;
			case 1:
				difficultyComputer2 = 1;
				break;
			case  2:
				difficultyComputer2 = 2;
				break;
			case 3:
				if(gameMode == 1) gameModeMenu();
				else difficultyComputer1();
				break;
			default:
				break;		
		}
    }
	
	private void mapSolid(QueahBoard board){
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
