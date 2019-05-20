package DatasetGenerator;

import java.util.Random;

public class Utils {
    public static String createName() {
        Random rdm = new Random();
        String name = "";
        for (int i = 0; i < rdm.nextInt(10); i++) {
            name += (char) (rdm.nextInt(26) + 'a');
        }
        return name;
    }
}
