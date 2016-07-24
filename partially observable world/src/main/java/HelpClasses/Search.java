package HelpClasses;

import java.util.ArrayList;

/**
 * Created by David on 7/24/2016.
 */
public class Search {
    protected int x,y;
    protected char player;

    protected boolean[][] createAdjentMatrix(ArrayList<String> world){
        boolean adjent[][]=new boolean[world.size()][world.get(0).length()];

        for(boolean[] row:adjent){
            for(boolean element:row){
                element=false;
            }
        }

        return adjent;
    }

    protected void findPlayer(ArrayList<String> world){
        for(int i=0;i<world.size();i++){
            if(world.get(i).contains(String.valueOf(player))){
                y=i;
                x=world.get(i).indexOf(player);
            }
        }
    }
}
