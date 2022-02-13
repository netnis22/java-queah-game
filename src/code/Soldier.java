package code;

import java.awt.*;

import javax.swing.ImageIcon;

public class Soldier{

    private Image img;
    private final String color;
    private int  x,y;

    Image redSoldier = new ImageIcon("././img/Soldier_red_New.png").getImage();
    Image blackSoldier =  new ImageIcon("././img/Soldier_black_New.png").getImage();

    public Soldier(Image img,String color,int x,int y) {
        this.img=img;
        this.color = color;
        this.setX(x);
        this.setY(y);
    }
    
    public Soldier(String color) {
        this.color = color;
        if(color.equals("red")) img = redSoldier;
        else  img = blackSoldier;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public String getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
