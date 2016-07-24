import Visual.Visual;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by David on 7/16/2016.
 */
public class VisualTest {

    @Test
    public void updateTest(){
        Visual frame=new Visual(5,4);

        ArrayList<String> forUpdate=new ArrayList<>();
        forUpdate.add("aaaa");
        forUpdate.add("bbbb");
        forUpdate.add("cccc");


        frame.update(forUpdate);


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        forUpdate.add("dddd");
        forUpdate.add("ffff");

        frame.update(forUpdate);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
