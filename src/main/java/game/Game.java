package game;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Igor Ivankov
 */
public class Game {
    public static void main(String[] args) {
        try {
            String line = readRandomLine();
            System.out.println("Word length: " + line.length());
            try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
                int bulls = 0;
                List<Character> unused = new ArrayList<>();
                List<Character> unusedInput = new ArrayList<>();
                while (bulls < line.length()) {
                    bulls = 0;
                    System.out.println("Enter a word!");
                    String input = bf.readLine();
                    if (input.length() != line.length()) {
                        System.out.println("Incorrect word length");
                        continue;
                    }
                    for (int i = 0; i < line.length(); i++) {
                        if (input.charAt(i) == line.charAt(i)) {
                            bulls++;
                        } else {
                            unused.add(line.charAt(i));
                            unusedInput.add(input.charAt(i));
                        }
                    }
                    int cows = 0;
                    for (Character anUnused : unused) {
                        int index = unusedInput.indexOf(anUnused);
                        if (index > -1) {
                            cows++;
                            unusedInput.remove(index);
                        }
                    }
                    System.out.println("Bulls: " + bulls + ", cows: " + cows);
                }
            }
            System.out.println("Congratulations, you won!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readRandomLine() throws IOException {
        ClassLoader classLoader = Game.class.getClassLoader();
        URL url = classLoader.getResource("dictionary.txt");
        String result = null;
        try (RandomAccessFile raf = new RandomAccessFile(Objects.requireNonNull(url).getPath(), "r")) {
            long randomLong = (long) (Math.random() * raf.length());
            raf.seek(randomLong);
            result = raf.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

}
