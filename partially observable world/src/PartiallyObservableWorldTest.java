import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Test;

public class PartiallyObservableWorldTest {

	@Test
	public void addRowTestAddsUp() {
		ArrayList<String> data=new ArrayList<>();
		data.add("#######");
		data.add("#--#-b#");
		data.add("#--#--#");
		
		char array[][]=new char[][] {{'#','z','#'},{'#','#','#'},{'#','-','#'}};
		
		System.out.println("");
		
		ArrayList<String>cc=new PartiallyObservableWorld().addRow(data, array,1, 0,0);
		for(String s :cc)
			System.out.println( s);
	}
	@Test
	public void addRowTestAddsDown(){
		System.out.println("");
		ArrayList<String> data=new ArrayList<>();
		data.add("#######");
		data.add("#--#-b#");
		data.add("#--#--#");
		
		char array[][]=new char[][] {{'#','z','#'},{'#','#','#'},{'#','-','#'}};
		
		ArrayList<String>cc=new PartiallyObservableWorld().addRow(data, array,1, 3,2);
		
		for(String s :cc)
			System.out.println( s);
	}
	
	@Test
	public void moveRowTestUp(){
		System.out.println("");
		ArrayList<String> data=new ArrayList<>();
		data.add("#######");
		data.add("#--#--#");
		data.add("#--#--#");
		data.add("#--#-b#");
		char array[][]=new char[][] {{'#','z','#'},{'#','#','#'},{'#','-','#'}};
		ArrayList<String>cc=new PartiallyObservableWorld().rowMove(data,array,3, true);
		
		for(String s :cc)
			System.out.println( s);
	}
	
	@Test
	public void moveRowTestDown(){
		System.out.println("");
		ArrayList<String> data=new ArrayList<>();
		data.add("#######");
		data.add("#--#-b#");
		data.add("#--#--#");
		data.add("#--#--#");
		char array[][]=new char[][] {{'#','z','#'},{'#','#','#'},{'#','-','#'}};
		ArrayList<String>cc=new PartiallyObservableWorld().rowMove(data,array,1, false);
		
		for(String s :cc)
			System.out.println( s);
	}
	
	@Test
	public void moveColumTestLeft(){
		System.out.println("");
		ArrayList<String> data=new ArrayList<>();
		data.add("#######");
		data.add("#--#-b#");
		data.add("#--#--#");
		data.add("#--#--#");
		char currentView[][]=new char[][] {{'#','z','#'},{'#','b','#'},{'#','-','#'}};
		ArrayList<String>cc=new PartiallyObservableWorld().columnMove(data, currentView, 1, true);
		
		for(String s :cc)
			System.out.println( s);
	}
	
	@Test
	public void moveColumTestRight(){
		System.out.println("");
		ArrayList<String> data=new ArrayList<>();
		data.add("#######");
		data.add("#--#b-#");
		data.add("#--#--#");
		data.add("#--#--#");
		char currentView[][]=new char[][] {{'#','z','#'},{'#','b','#'},{'#','-','#'}};
		ArrayList<String>cc=new PartiallyObservableWorld().columnMove(data, currentView, 1, false);
		
		for(String s :cc)
			System.out.println( s);
	}
	
	@Test
	public void addColumTestLeft(){
		System.out.println("");
		ArrayList<String> data=new ArrayList<>();
		data.add("#######");
		data.add("#b-#--#");
		data.add("#--#--#");
		data.add("#--#--#");
		char currentView[][]=new char[][] {{'#','z','#'},{'#','b','#'},{'#','-','#'}};
		ArrayList<String>cc=new PartiallyObservableWorld().addColumn(data, currentView, 1, true);
		
		for(String s :cc)
			System.out.println( s);
	}
	
	@Test
	public void addColumTestRight(){
		System.out.println("");
		ArrayList<String> data=new ArrayList<>();
		data.add("#######");
		data.add("#--#-b#");
		data.add("#--#--#");
		data.add("#--#--#");
		char currentView[][]=new char[][] {{'#','z','#'},{'#','b','#'},{'#','-','#'}};
		ArrayList<String>cc=new PartiallyObservableWorld().addColumn(data, currentView, 1, false);
		
		for(String s :cc)
			System.out.println( s);
	}
	
	@Test
	public void moveDecisionTest(){
		System.out.println("");
		ArrayList<String> data=new ArrayList<>();
		data.add("#######");
		data.add("#--#-b#");
		data.add("#--#--#");
		data.add("#--#--#");
		
		PartiallyObservableWorld p=new PartiallyObservableWorld();
		
		for(int i=0;i<10;i++){
			System.out.println(p.moveDecision(data));
		}
	}
	
	@Test
	public void nextMoveTest(){
		
		try(PrintWriter writer = new PrintWriter("maze.txt","UTF-8")){//so we have the file clean
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		char world[][]= new char[][]{"#######".toCharArray(),
			"#--#--#".toCharArray(), // it was #--#-b# but hacked around so I don't have to move b, it is inserted in createCurrentView
			"#--#--#".toCharArray(),
			"#--#--#".toCharArray(),
			"e-----#".toCharArray(),
			"#-----#".toCharArray(),
			"#######".toCharArray()
			};
			
		int playerX=5,playerY=1;
			
		char[][] currentView=createCurrentView(world, playerX, playerY);	
		
		PartiallyObservableWorld p=new PartiallyObservableWorld();
		
		while(true){
			String move=p.nextMove(currentView);
			
			if(playerX==1 && playerY==4) break;
			
			if(move.equals(MoveType.UP.toString()))playerY--;
			else if(move.equals(MoveType.DOWN.toString()))playerY++;
			else if(move.equals(MoveType.LEFT.toString()))playerX--;
			else if(move.equals(MoveType.RIGHT.toString()))playerX++;
			
			try{
				Thread.sleep(300);
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
			
			
			
			currentView=createCurrentView(world, playerX, playerY);	
		}
		
	}
	
	public char[][] createCurrentView(char[][] world,int playerX,int playerY){
		char[][] view=new char[3][3];
		
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				view[i+1][j+1]=world[playerY+i][playerX+j];
			}
		}
		view[1][1]='b';//player is always in the center
		return view;
	}
	
	public void afisare(char[][] view){
		for(int i=0;i<view.length;i++){
			for(int j=0;j<view[i].length;j++){
				System.out.print(view[i][j]);
			}
			System.out.println("");
		}
	}
}
