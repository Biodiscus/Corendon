package nl.itopia.corendon.utils;

/**
 * © Biodiscus.net 2014, Robin
 */
public class Log {
    
    public static boolean DEBUG = true;

    public static void display(Object ... str) {
        if(!DEBUG) return;

        if(str.length > 1) {
            for (int i = 0; i < str.length; i++) {
                System.out.print(str[i]);
                if(i < str.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        } else {
            System.out.println(str[0]);
        }
    }
}
