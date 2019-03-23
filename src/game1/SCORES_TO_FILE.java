package game1;

import java.io.*;

public class SCORES_TO_FILE {


        public static void added(){
            Integer p = Game.score;
            BufferedWriter input = null;
            final File f = new File("scores.txt");

            try{
                input = new BufferedWriter(new FileWriter(f,true));
                PrintWriter out = new PrintWriter(input);
                out.println(p);
                out.close();
            }catch(FileNotFoundException e){
                System.out.println("file not found");
            }catch (IOException r){
                System.out.println("Could not write");
            }

    }
}
