package frame.io;

import java.util.Scanner;

/**
 * Created by Shli on 06/08/2017.
 */
public class IODevice {

    public static String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    public static void write(String text) {
        System.out.println(text);
    }
}
