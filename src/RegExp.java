import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegExp {
    public static void main(String[] args) {
        String st = "12123434";
//        String spt = "(\\d\\d)\\1";
        String spt = "\\b([bB])";
        Pattern pt = Pattern.compile(spt);
//        while (mt.find()) {
//            System.out.println(mt.group());
//        }
        Scanner s = null;
        File f = new File("dictionary.txt");
        try {
            s = new Scanner(f);
        } catch (Exception e) {
        }
        int count = 0;
        while (s.hasNextLine()) {
            st = s.nextLine();
            Matcher mt = pt.matcher(st);
            while (mt.find()) {
                count += mt.groupCount();
            }
        }
        System.out.println(count);
//        s.forEachRemaining(System.out::println);

    }
}
