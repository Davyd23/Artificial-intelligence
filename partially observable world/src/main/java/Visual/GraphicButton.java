package Visual;

import Visual.Visual;

import javax.swing.*;
import java.awt.*;

/**
 * Created by David on 7/16/2016.
 */
public class GraphicButton extends JButton {
    ImageIcon wall,player,exit,unknown;

    public GraphicButton(){
        wall=new ImageIcon("src/main/resources/wall.jpg");
        Image image=wall.getImage();
        Image resized=image.getScaledInstance(Visual.buttonWidth,Visual.buttonHeight,java.awt.Image.SCALE_SMOOTH);
        wall=new ImageIcon(resized);

        player=new ImageIcon("src/main/resources/robot.png");
        image=player.getImage();
        resized=image.getScaledInstance(Visual.buttonWidth,Visual.buttonHeight,java.awt.Image.SCALE_SMOOTH);
        player=new ImageIcon(resized);

        exit=new ImageIcon("src/main/resources/door.jpg");
        image=exit.getImage();
        resized=image.getScaledInstance(Visual.buttonWidth,Visual.buttonHeight,java.awt.Image.SCALE_SMOOTH);
        exit=new ImageIcon(resized);

        unknown=new ImageIcon("src/main/resources/question_mark.png");
        image=unknown.getImage();
        resized=image.getScaledInstance(Visual.buttonWidth,Visual.buttonHeight,java.awt.Image.SCALE_SMOOTH);
        unknown=new ImageIcon(resized);
    }

   public void setImage(char c){
       switch (c){
           case '-':setIcon(null);break;
           case '?':setIcon(unknown);break;
           case '#':setIcon(wall);break;
           case 'b':setIcon(player);break;
           case 'e':setIcon(exit);break;
           default:setIcon(null);
       }
   }
}
