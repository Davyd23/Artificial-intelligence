import HelpClasses.SearchUnknown;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by David on 7/22/2016.
 */
public class SearchUnknownTest {

    @Test
    public void nextMoveTest(){
        ArrayList<String> world=new ArrayList<>();
        world.add("######");
        world.add("#---b-");
        world.add("#-####");
        world.add("#-#--#");
        world.add("#--?-#");
        world.add("#----#");
        world.add("#--?-#");

        SearchUnknown searchUnknown =new SearchUnknown('?','b');
        try {
            while(true) {
                System.out.println(searchUnknown.nextMove(world));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
