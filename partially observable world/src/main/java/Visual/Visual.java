package Visual;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by David on 7/15/2016.
 */
public class Visual extends JFrame {
    public static final int frameWidth=100,buttonWidth=frameWidth-10;
    public static final int frameHeight=100,buttonHeight=frameHeight-10;

    private java.util.List<JButton> buttons=new ArrayList<>();
    private int rows,columns;
    private JPanel mainPanel;
    private List<JPanel> panels=new ArrayList<>();

    public Visual(int rows,int columns){


        super("Where is the exit?");

        this.rows=rows;
        this.columns=columns;

        setSize(rows*100,columns*100);

        setLayout(new FlowLayout());

        mainPanel=new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        add(mainPanel);

        setVisible(true);
    }



    public void update(ArrayList<String> currentWorld){


        if(currentWorld.size()*currentWorld.get(0).length()>buttons.size()) //check to see if is needed to resize
            setSize(currentWorld.get(0).length()*frameWidth,currentWorld.size()*frameHeight);

        while(currentWorld.size()*currentWorld.get(0).length()>buttons.size()){ // in case we have a bigger world
            JButton button=new GraphicButton();
            button.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
            buttons.add(button);
        }

        for(JPanel panel:panels){
            panel.removeAll();  // we clear evverything
        }

        for( int i=0;i<currentWorld.size();i++){
            JPanel panel;
            if(panels.size()-1>=i) panel=panels.get(i); // if it exists we get it from here
            else panel=new JPanel();  // other weay we delete it

            String view=currentWorld.get(i);

            for(int j=0;j<view.length();j++){
                //buttons.get(view.length()*i+j).setText(String.valueOf(view.charAt(j)));  // set the txt to what we got

                GraphicButton b= (GraphicButton) buttons.get(view.length()*i+j);
                b.setImage(view.charAt(j));
                panel.add(buttons.get(view.length()*i+j));

            }
            if(panels.size()-1<i){   // in case we didn't had the panel , we add it now
                panels.add(panel);
                mainPanel.add(panel);
                FlowLayout layout= (FlowLayout) panel.getLayout();
                layout.setHgap(0);
                layout.setVgap(0);
            }
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

}
