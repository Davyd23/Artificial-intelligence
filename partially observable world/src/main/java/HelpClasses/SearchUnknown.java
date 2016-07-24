package HelpClasses;


import HelpClasses.MoveType;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by David on 7/22/2016.
 */
public class SearchUnknown extends Search {
    private int checkValue=10000;//interger.max get's rolled back(becomes -)
    private char toFind;

    private LinkedList<String> moves;

    public SearchUnknown(char toFind, char player) {
        this.toFind = toFind;
        this.player = player;
    }

    public String nextMove(ArrayList<String> world){
        try{ // sa efectueze toate miscarile gasite anterior, sau sa treaca mai departe daca nu mai avem/ nu e initializat
            return moves.pop();
        }catch (NoSuchElementException | NullPointerException ex){
            //let it go further
        }

        findPlayer(world);

        int distMin=checkValue; //pe ce coloana pe afla  + pe ce linie se afla
        int linie=-1;   // pe ce linie se afla

        Point destination= new Point();

        for(int i=0;i<world.size();i++){
            int distCur=checkLine(world.get(i));

            if(distCur+Math.abs(y-i)<distMin){  //cate casute trebuiesc mutate in total

                destination.setLocation(world.get(i).indexOf("?"),i);

                distMin=distCur+Math.abs(y-i);
                linie=i;
            }
        }

        return AStarSearch(world,destination);
    }

    private String AStarSearch(ArrayList<String> world,Point destination){ //only 4 primordial directions

        moves=new LinkedList();

        boolean[][] adjentMatrix=createAdjentMatrix(world);

        findPlayer(world);
        while(true) { // while we have't reached '?'
            if(checkSuroundings(world)) break;

            double movePrice = Double.MAX_VALUE;
            String move = null;


            String row =(y>0)? world.get(y - 1):null;
            double movePriceUp = Math.abs(y - 1 - destination.getY()) + Math.abs(x - destination.getX());
            if (row!=null && movePrice > movePriceUp &&
                    row.charAt(x) != '#' && row.charAt(x) != '?'
                    && !adjentMatrix[y-1][x]) {
                movePrice = movePriceUp;
                move = MoveType.UP.toString();
            }

            row = world.get(y);
            double movePriceLeft = Math.abs(y - destination.getY()) + Math.abs(x - 1 - destination.getX());
            if (x!=0 && movePrice > movePriceLeft && row.charAt(x - 1) != '#' &&
                    row.charAt(x -1) != '?'
                    && !adjentMatrix[y][x-1]) {
                movePrice = movePriceLeft;
                move = MoveType.LEFT.toString();
            }

            row =(world.size()>y+1)? world.get(y + 1):null;
            double movePriceDown = Math.abs(y + 1 - destination.getY()) + Math.abs(x - destination.getX());
            if (row!=null && movePrice > movePriceDown &&
                    row.charAt(x) != '#' && row.charAt(x) != '?'
                    && !adjentMatrix[y+1][x]) {
                movePrice = movePriceDown;
                move = MoveType.DOWN.toString();
            }

            row = world.get(y);
            double movePriceRight = Math.abs(y - destination.getY()) + Math.abs(x + 1 - destination.getX());
            if (movePrice > movePriceRight && row.charAt(x + 1) != '#' && row.charAt(x + 1) != '?'
                    && !adjentMatrix[y][x+1]) {
                movePrice = movePriceRight;
                move = MoveType.RIGHT.toString();
            }

            if(move==null) break;

            moves.add(move);

            adjentMatrix[y][x]=true; // we passed here
            if(move.equals(MoveType.UP.toString())){
                y-=1;
            }else if(move.equals( MoveType.LEFT.toString() ) ){
                x-=1;
            }else if(move.equals(MoveType.DOWN.toString() ) ){
                y+=1;
            }else if(move.equals(MoveType.RIGHT.toString() ) ){
                x+=1;
            }
        }

        return moves.pop();

    }



    private boolean checkSuroundings(ArrayList<String> world){ //true if next to searched element
        for(int i=-1;i<=1;i++){
            if(y+i>0 && world.size()>y+i) {
                String row = world.get(y + i);
                for (int j = -1; j <= 1; j++) {
                    if(x+j>-1 && row.length()>x+j) {
                        if (row.charAt(x + j) == toFind) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private int checkLine(String line){//functie pentru determinarea celui mai apropiat ?
        for(int i=0;i<line.length();i++){
            if(line.charAt(i)==toFind){
                return Math.abs(x-i);  // coloana player-coloana ?
            }
        }
        return checkValue;
    }

    public void clearMoves(){
        if(moves!=null)
            moves.clear();
    }


}
