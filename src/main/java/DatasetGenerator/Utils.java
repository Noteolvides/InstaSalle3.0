package DatasetGenerator;

import java.util.Random;

public class Utils {
    public static String createName() {
        Random rdm = new Random();
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        String name = "";
        for (int i = 0; i < rdm.nextInt(10); i++) {
            name += (char) (rdm.nextInt(26) + 'a');
        }
        if (name.equals("")) {
            name += alphabet[rdm.nextInt(26)];
            name += alphabet[rdm.nextInt(26)];
            name += alphabet[rdm.nextInt(26)];
            name += alphabet[rdm.nextInt(26)];
        }
        return name;
    }
}
