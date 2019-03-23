package game1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class SCORES_FROM_FILE {
        static ArrayList<Integer> scorelist = new ArrayList<Integer>();

        private static Scanner input = null;
        public static final File f = new File("scores.txt");


        public static ArrayList<Integer> LISTOSCORE() {


            synchronized (Game.class){
                try {
                    input = new Scanner(f);
                    while (input.hasNext()) {
                        String data = input.nextLine();
                        int sco = parseInt(data);
                        scorelist.add(sco);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("file not found");
                }
                Collections.sort(scorelist);
                return scorelist;
            }
        }
    }

