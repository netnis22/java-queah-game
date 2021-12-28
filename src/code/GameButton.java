package code;

import java.awt.*;

import javax.swing.*;

public class GameButton extends JButton {

	private Image img;
	private Soldier soldier=null;

	public GameButton(Image img,Soldier soldier) {
		this.img=img;
		this.soldier=soldier;
	}
	
	public GameButton(Image img){
		this.img=img;
	}

	public GameButton(Soldier soldier){
		this.soldier=soldier;
		this.img=soldier.getImg();
	}

	public GameButton(){}
	
	public Image getImg() {
		return img;
	}
	
	public void setImg(Image img){
		this.img = img;
	}

	public Soldier getSoldier(){
		return soldier;
	}

	public void setSoldier(Soldier soldier){
		this.soldier=soldier;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		if(!(soldier==null)){
			g.drawImage(soldier.getImg(), 0, 0, getWidth(), getHeight(), null);
		}
	}
}
