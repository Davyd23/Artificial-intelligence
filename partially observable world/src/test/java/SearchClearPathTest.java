import HelpClasses.SearchClearPath;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by David on 7/24/2016.
 */
public class SearchClearPathTest {

    @Test
    public void nextMoveTestDown(){
        ArrayList<String> world=new ArrayList<>();
        world.add("######");
        world.add("#---b#");
        world.add("#-####");
        world.add("#-#--#");
        world.add("#--?-#");
        world.add("#----#");
        world.add("#--?-#");

        SearchClearPath search=new SearchClearPath('-','b');

        while (true) {
            String move=search.nextMove(world);
            if(move==null) break;
            System.out.println(move);
        }
    }

    @Test
    public void nextMoveTestUp(){
        ArrayList<String> world=new ArrayList<>();
        world.add("#-####");
        world.add("#----#");
        world.add("#-####");
        world.add("#-#--#");
        world.add("#--?-#");
        world.add("#---b#");
        world.add("######");

        SearchClearPath search=new SearchClearPath('-','b');

        while (true) {
            String move=search.nextMove(world);
            if(move==null) break;
            System.out.println(move);
        }
    }

    @Test
    public void nextMoveTestLeft(){
        ArrayList<String> world=new ArrayList<>();
        world.add("######");
        world.add("#----#");
        world.add("#-####");
        world.add("#-#--#");
        world.add("#--?-#");
        world.add("----b#");
        world.add("######");

        SearchClearPath search=new SearchClearPath('-','b');

        while (true) {
            String move=search.nextMove(world);
            if(move==null) break;
            System.out.println(move);
        }
    }
}
