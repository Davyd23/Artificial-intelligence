package HelpClasses;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

import static HelpClasses.MoveType.*;

/**
 * Created by David on 7/24/2016.
 */
public class SearchClearPath extends Search {
    private int checkValue=10000;//interger.max get's rolled back(becomes -)
    //private int x,y;  //y is line, x is column
    private char path;
    //private char player;
    private char exit='e'; // used as a beacon for a* search

    private LinkedList<String> moves;

    public SearchClearPath(char path, char player) {
        this.path = path;
        this.player = player;

        moves=new LinkedList<>();
    }


    public String nextMove(ArrayList<String> world){ // not finished
        if(!moves.isEmpty()) return moves.pop();

        findPlayer(world);

        String move=null;
        move=movesVertical(world);
        if(move!=null) return move;

        move=movesHorizontal(world);
        return move;
    }

    private String movesVertical(ArrayList<String> world){
        String lastRow=world.get(world.size()-1);

        for(int i=0;i<lastRow.length();i++){
            if(lastRow.charAt(i)==path){
                String move=AStarSearch(world,i,world.size()-1);
                if(move!=null)return move;
            }
        }

        String firstRow=world.get(0);

        for(int i=0;i<firstRow.length();i++){
            if(firstRow.charAt(i)==path){
                String move=AStarSearch(world,i,0);
                if(move!=null)return move;
            }
        }

        return null;
    }

    private String movesHorizontal(ArrayList<String> world){
        for(int i=0;i<world.size();i++){
            String row=world.get(i);
            String move=null;

            if(row.charAt(0)==path) move=AStarSearch(world,0,i);
            if(move!=null) return move;

            if(row.charAt(row.length()-1)==path) move=AStarSearch(world,row.length()-1,i);
            if(move!=null) return move;
        }

        return null;
    }

    private String AStarSearch(ArrayList<String> world,int destinationX, int destinationY){ //only 4 primordial directions

        if(!moves.isEmpty()) return moves.pop();

        findPlayer(world);

        boolean[][] adjentMatrix=createAdjentMatrix(world);

        int[][] costs=calculateAStarCosts(world,destinationX,destinationY);


        while(true) { // while we have't reached '-'

            if(x==0 || y==0 || x==world.get(0).length()-1 || y==world.size()-1) break;

            double movePrice = checkValue;
            MoveType move = null;
            adjentMatrix[y][x]=true; // visited here

            if(costs[y][x+1] <movePrice && !adjentMatrix[y][x+1]){
                movePrice=costs[y][x+1];
                move=RIGHT;
            }
            if(costs[y-1][x]<movePrice && !adjentMatrix[y-1][x]){
                movePrice=costs[y-1][x];
                move= UP;
            }
            if(costs[y][x-1]<movePrice && !adjentMatrix[y][x-1]){
                movePrice=costs[y][x-1];
                move=LEFT;
            }
            if(costs[y+1][x]<movePrice && !adjentMatrix[y+1][x]){
                movePrice=costs[y+1][x];
                move=DOWN;
            }

            if(move==null) break;

            switch (move){
                case UP: y-=1;break;
                case DOWN:y+=1;break;
                case LEFT:x-=1;break;
                case RIGHT:x+=1;break;
            }

            moves.add(move.toString());
        }

        if(moves.isEmpty())return null;  // null in case we can't reach

        return moves.pop();

    }

    private int[][] calculateAStarCosts(ArrayList<String> world,int destinationX,int destinationY){

        int[][] costs=new int[world.size()][world.get(0).length()];

        for(int j=0;j<world.size();j++){
            String row=world.get(j);

            for(int i=0;i<row.length();i++){
                if(row.charAt(i)==path){
                    costs[j][i]=Math.abs(destinationY-j)+Math.abs(destinationX-i);
                }else{
                    costs[j][i]=checkValue;
                }
            }
        }

        return costs;
    }

    public void clearMoves(){
        if(!moves.isEmpty())moves.clear();
    }

    private int checkLine(String line){//functie pentru a determina daca exista drum acolo
        for(int i=0;i<line.length();i++){
            if(line.charAt(i)==path){
                return Math.abs(i);
            }
        }
        return checkValue;
    }
}
