import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/*
 * Maze Escape is a popular 1 player game where a bot is trapped in a maze and is expected to find its way out.
 * Possible Moves: UP, DOWN, RIGHT,LEFT 
 */
public class PartiallyObservableWorld {
	
	public String nextMove(char currentView[][]){ // return a string which indicates direction
		
		ArrayList<String> data=null;
		
		try(BufferedReader br=new BufferedReader(new FileReader("maze.txt"))){
			
			String line=br.readLine();
			
			if(line!=null)data=new ArrayList<String>();
			
			while(line!=null){
				data.add(line);
				line=br.readLine();
			}
		} catch ( IOException e) {
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
		
		ArrayList<String> world=createWorld(data, currentView);
		
		String move="";
		
		if(containsExit(currentView)){
			move=moveToExit(currentView);
		}else{
			move=moveDecision(world);
		}
		
		world.set(world.size()-1,move);
		
		try(PrintWriter writer = new PrintWriter("maze.txt","UTF-8")){
			for(String s:world){
				writer.println(s);
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		afisare(world);
		
		return move;
		
	}
	
	public ArrayList<String> createWorld(ArrayList<String> data, char[][] currentView){//last element of data is the way it went in the previous move
		if(data==null){
			ArrayList<String> view=new ArrayList<>();
			for(char[] c:currentView){
				view.add(String.valueOf(c));
			}
			view.add("so it fallows what I implemented"); //will be rewriten
			return view; // if data is null => first iteration, nothing to check
		}
		
		for(int i=0;i<data.size()-1;i++){ // last element is the move
			
			String s=data.get(i);
			if(s.contains("b")){
				
				if(MoveType.UP.toString().equals(data.get(data.size()-1))){
					
					if(i<2) data=addRow(data,currentView,i,0,i-1);
					else data=rowMove(data, currentView, i, true);
					
				}else if(MoveType.DOWN.toString().equals(data.get(data.size()-1))){
					
					if(i>=data.size()-1-2) {
						data=addRow(data,currentView,i,i+2,i+1);
					}
					else {
						data=rowMove(data, currentView, i, false);
					}
					
				}else if(MoveType.RIGHT.toString().equals(data.get(data.size()-1))){
					
					int index=s.indexOf('b');
					if(index>s.length()-3) data=addColumn(data, currentView, i, false);
					else data=columnMove(data, currentView, i, false);
					
					
				}else if(MoveType.LEFT.toString().equals(data.get(data.size()-1))){
					
					int index=s.indexOf('b');
					if(index<2) data=addColumn(data, currentView, i, true);
					else data=columnMove(data, currentView, i, true);
				}
				
				break;
			}
		}
			
		
		return data;
		
	}
	
	public ArrayList<String> addRow(ArrayList<String> data,char[][] currentView, int index, int whereToAdd,int between){
		
		int playerLocation=data.get(index).indexOf('b');
		
		StringBuilder sb=new StringBuilder();//removes the player last position
		sb.append(data.get(index));
		sb.replace(playerLocation, playerLocation+1,((char)45)+""); 
		data.set(index, sb.toString());
		
		sb=new StringBuilder();  //adds b to the array
		sb.append(data.get(between));
		sb.replace(playerLocation, playerLocation+1,"b");
		data.set(between, sb.toString());
		
		sb=new StringBuilder();
		for(int j=0;j<data.get(0).length();j++){
			sb.append("?");
		}
		
		if(index>whereToAdd)  //decide if we add top row or bottom row
			sb.replace(playerLocation-1, playerLocation+2, String.valueOf(currentView[0]));
		else
			sb.replace(playerLocation-1, playerLocation+2, String.valueOf(currentView[2]));
		
		data.add(whereToAdd,sb.toString()); // we added the extra row
		
		
		
		
	
		return data;
	}
	
	public ArrayList<String> rowMove(ArrayList<String> data,char[][] currentView, int index, boolean UP){
		StringBuilder sb=new StringBuilder();
		
		int playerLocation=data.get(index).indexOf('b');
		
		sb.append(data.get(index));
		if(UP){
			sb.replace(playerLocation-1, playerLocation+2, String.valueOf(currentView[2]));
			data.set(index, sb.toString());
			
			sb=new StringBuilder();
			sb.append(data.get(index-1));
			sb.replace(playerLocation-1, playerLocation+2, String.valueOf(currentView[1]));
			data.set(index-1, sb.toString());
			
			sb=new StringBuilder();
			sb.append(data.get(index-2));
			sb.replace(playerLocation-1, playerLocation+2, String.valueOf(currentView[0]));
			data.set(index-2, sb.toString());
		}else{
			sb.replace(playerLocation-1, playerLocation+2, String.valueOf(currentView[0]));
			data.set(index, sb.toString());
			
			sb=new StringBuilder();
			sb.append(data.get(index+1));
			sb.replace(playerLocation-1, playerLocation+2, String.valueOf(currentView[1]));
			data.set(index+1, sb.toString());
			
			sb=new StringBuilder();
			sb.append(data.get(index+2));
			sb.replace(playerLocation-1, playerLocation+2, String.valueOf(currentView[2]));
			data.set(index+2, sb.toString());
		}
		
		return data;
	}
	
	public ArrayList<String> addColumn(ArrayList<String> data,char[][] currentView, int index, boolean left){
		
		for(int i=0;i<data.size()-1;i++){
			if(left)data.set(i,"?"+ data.get(i));
			else data.set(i, data.get(i)+"?");
		}
		
		return columnMove(data, currentView, index, left);
	}
	public ArrayList<String> columnMove(ArrayList<String> data,char[][] currentView, int index, boolean left){
		
		int playerLocation=data.get(index).indexOf('b');
		
		int offset=(left==true)? -2:0;
		
		StringBuilder sb=new StringBuilder();
		
		
		sb.append(data.get(index-1));  //replace the level above the player b
		sb.replace(playerLocation+offset, playerLocation+offset+3, String.valueOf(currentView[0]));
		data.set(index-1, sb.toString());
		
		sb=new StringBuilder();  
		sb.append(data.get(index));
		sb.replace(playerLocation+offset, playerLocation+offset+3, String.valueOf(currentView[1]));
		data.set(index, sb.toString());
		
		sb=new StringBuilder();  
		sb.append(data.get(index+1));
		sb.replace(playerLocation+offset, playerLocation+offset+3, String.valueOf(currentView[2]));
		data.set(index+1, sb.toString());
		
		return data;
	}
	
	public String moveDecision(ArrayList<String> world){  //implement search algorithm
		int playerRow=0;
		for(int i=0;i<world.size()-1;i++){
			if(world.get(i).contains("b")){
				playerRow=i;
				break;
			}
		}
		
		int playerLocation=world.get(playerRow).indexOf("b");
		String move="sdfbsdf";
		
		boolean searchMove=true;
		while(searchMove){
			int random=(int)Math.floor(Math.random()*4);
			
			switch(random){
			case 0:String s=world.get(playerRow-1);
				if(s.charAt(playerLocation)!='#') {
					searchMove=false;
					move=MoveType.UP.toString();
				}
				break;
			case 1:s=world.get(playerRow);
				if(s.charAt(playerLocation-1)!='#') {
					searchMove=false;
					move=MoveType.LEFT.toString();
				}
				break;
			case 2:s=world.get(playerRow+1);
				if(s.charAt(playerLocation)!='#') {
					searchMove=false;
					move=MoveType.DOWN.toString();
				}
				break;
			case 3:s=world.get(playerRow);
				if(s.charAt(playerLocation+1)!='#') {
					searchMove=false;
					move=MoveType.RIGHT.toString();
				}
				break;
			}
		}
		
		return move;
	}
	
	public boolean containsExit(char[][] view){
		for(int i=0;i<view.length;i++){
			if(String.valueOf(view[i]).contains("e"))return true;
		}
		return false;
	}
	
	public String moveToExit(char[][] view){
		
		for(int i=0;i<view.length;i++){
			for(int j=0;j<view[i].length;j++){
				if(view[i][j]=='e'){
					
					int movementX=j-1;// 1 because the player is in the center and the view is of size 3,3
					int movementY=i-1;
					
					if(movementX!=0 && view[1][1+movementX]!='#' ){
						
						if(movementX<0) return MoveType.LEFT.toString();
						else return MoveType.RIGHT.toString();
					}
					
					if(movementY!=0 && view[1+movementY][1]!='#'){
						
						if(movementY<0) return MoveType.UP.toString();
						else return MoveType.DOWN.toString();
					}
				}
			}
		}
		
		return "";

	}
	
	public void afisare(ArrayList<String> list){
		for(String s:list)
			System.out.println(s);
	}
}
