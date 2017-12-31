package ull.alu0100892833.cc.sat3sat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try {
            ArrayList<SAT> problems = readFile(args[0]);
            for (SAT temp : problems) {
                System.out.println("\n\n\n\n==========================");
                System.out.println("==========================\n");
                System.out.println("Converting " + temp + " to 3SAT.\n");
                System.out.println("==========================\n");
                SAT3 adapted = new SAT3(temp);
                System.out.println("\n==========================\n");
                System.out.println(temp + "\n\nTransformed to: ");
                System.out.println("\t" + adapted.toString());
                System.out.println("\t" + adapted.getUSet() + "\n");
                System.out.println("==========================");
                System.out.println("==========================\nPress ENTER to continue.");
                System.in.read();
            }
        } catch (Exception e) {
            System.err.println("Usage: java [executable-name] [file-with-SAT-problems]");
        }
    }

    /**
     * Reads a file containing some examples of SAT problems. There should be one on each line, with the format:
     *      A = {x, y, z} ^ {x, y} ^ ...
     * @param filename
     * @return An ArrayList with all the SAT objects.
     */
    private static ArrayList<SAT> readFile(String filename) {
        ArrayList<SAT> problems = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                SAT instance = new SAT(line.trim());
                problems.add(instance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
                return problems;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
