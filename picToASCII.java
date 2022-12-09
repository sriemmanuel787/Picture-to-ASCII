import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

class Main {
    public static final HashMap<Integer, Character> VALUES = new HashMap<Integer, Character>() {
        {
            put(0, ' ');
            put(12, '.');
            put(20, '-');
            put(25, ',');
            put(26, '_');
            put(28, '^');
            put(32, '~');
            put(38, '*');
            put(42, '=');
            put(44, 'L');
            put(45, '!');
            put(48, 'c');
            put(49, '+');
            put(52, 'r');
            put(53, 'J');
            put(54, '7');
            put(56, 'i');
            put(57, 'l');
            put(58, 'u');
            put(59, 'n');
            put(60, 'f');
            put(61, 'T');
            put(62, 's');
            put(63, 'Y');
            put(64, 'j');
            put(68, 'o');
            put(69, 'h');
            put(70, 'a');
            put(71, 'k');
            put(72, 'U');
            put(73, 'e');
            put(74, 'V');
            put(76, 'y');
            put(77, 'S');
            put(79, 'b');
            put(81, 'E');
            put(82, 'd');
            put(83, 'm');
            put(84, 'A');
            put(87, 'G');
            put(91, 'B');
            put(92, '#');
            put(97, 'N');
            put(98, '%');
            put(99, 'W');
            put(100, '8');
            put(102, 'M');
            put(104, 'Q');
            put(107, 'g');
            put(139, '@');
        }
    };

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Please specify picture directory:");
        Scanner scn = new Scanner(System.in);
        String dir = scn.next();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(dir));
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        int height = img.getHeight();
        int width = img.getWidth();
        int[][] picGray = new int[height][width];
        System.out.println("Reading file...");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                picGray[i][j] = (((img.getRGB(j, i) & 0xff) + ((img.getRGB(j, i) & 0xff00) >> 8)
                        + ((img.getRGB(j, i) & 0xff0000) >> 16)) / 3);
            }
        }
        System.out.println("File read successfully!");
        
        System.out.println("Image size:");
        System.out.println("(1): Small");
        System.out.println("(2): Medium");
        System.out.println("(3): Large");
        Scanner scnSize = new Scanner(System.in);
        int size = scnSize.nextInt();
        File file = new File("results.txt");
        PrintStream stream = new PrintStream(file);
        System.out.print("Writing to file...");
        for (int i = 0; i < height; i += 4-size) {
            for (int j = 0; j < width; j+= 4-size) {
                stream.print(findChar(picGray[i][j]));
            }
            if (i % 100 == 0)
                System.out.print(".");
            stream.println();
        }
        System.out.println("Picture successfully converted!");
        stream.close();
        scn.close();
        scnSize.close();
    }

    // Finds the character to be chosen, based on how dark the pixel is.
    public static char findChar(int key) {
        for (int i = 1; i < VALUES.size() - 1; i++) {
            int lower = VALUES.keySet().toArray(new Integer[VALUES.size()])[i - 1];
            int mid = VALUES.keySet().toArray(new Integer[VALUES.size()])[i];
            int upper = VALUES.keySet().toArray(new Integer[VALUES.size()])[i + 1];
            if (key >= (mid - lower) / 2 && key <= (mid + upper) / 2) {
                return VALUES.get(mid);
            }
        }
        if (key <= 6) {
            return ' ';
        } else {
            return VALUES.get(139);
        }
    }
}