import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import javax.imageio.ImageIO;

public class App {

    public static int getPenality(int val1, int val2) {
        int dif = Math.abs(val1-val2);
        int pen = dif - dif*2;
        pen = pen/8;
        pen += Math.max(val1, val2)/8;
        return pen;
    }
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("please give a image file path");
            return;
        }
        // Read the input image
        System.out.println("Reading file : " + args[0]);
        File input = new File(args[0]);
        BufferedImage buf;
        try {
            buf = ImageIO.read(input);
        } catch (IOException e) {
            System.err.println("can't read file");
            e.printStackTrace();
            return;
        }

        // Create the greyScale matrix
        System.out.println("Reading grey scale values...");
        Raster raster = buf.getData();
        int[][] greyScalePixels = new int [raster.getHeight()][raster.getWidth()];
        for (int i = 0; i < raster.getHeight(); i++) {
            for (int j = 0; j < raster.getWidth(); j++) {
                int[] samples = raster.getPixel(j, i, (int[]) null);
                int mean = (samples[0]+samples[1]+samples[2])/3;
                greyScalePixels[i][j] = mean;
                // System.out.print(mean + " \t ");
            }
            // System.out.println();
        }

        // Create the output file
        System.out.println("Writing data to the output file...");
        File outputFile = new File("data/" + input.getName().replaceFirst("[.][^.]+$", "") + ".txt");
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Error while creating file");
            e.printStackTrace();
            return;
        }
        // Write the data to the file
        try {
            FileWriter writer = new FileWriter(outputFile);
            
            // résoltion de l'image
            writer.write(raster.getHeight() + " " + raster.getWidth() + "\n\n");

            // valeurs a
            for (int i = 0; i < greyScalePixels.length; i++) {
                for (int j = 0; j < greyScalePixels[i].length; j++) {
                    writer.write(greyScalePixels[i][j]/8 + " ");
                }
                writer.write("\n");
            }
            writer.write("\n");

            // valeurs b
            for (int i = 0; i < greyScalePixels.length; i++) {
                for (int j = 0; j < greyScalePixels[i].length; j++) {
                    writer.write(Math.abs(greyScalePixels[i][j]-255)/8 + " ");
                }
                writer.write("\n");
            }
            writer.write("\n");

            // Pénalites horizontales
            for (int i = 0; i < greyScalePixels.length; i++) {
                for (int j = 0; j < greyScalePixels[i].length-1; j++) {
                    writer.write(getPenality(greyScalePixels[i][j], greyScalePixels[i][j+1]) + " ");
                    // System.out.print(getPenality(greyScalePixels[i][j], greyScalePixels[i][j+1]) + "\t");
                }
                writer.write("\n");
                // System.out.println();
            }
            writer.write("\n");
            // System.out.println();

            // Penalites verticales
            for (int i = 0; i < greyScalePixels.length-1; i++) {
                for (int j = 0; j < greyScalePixels[i].length; j++) {
                    writer.write(getPenality(greyScalePixels[i][j], greyScalePixels[i+1][j]) + " ");
                    // System.out.print(getPenality(greyScalePixels[i][j], greyScalePixels[i+1][j]) + "\t");
                }
                writer.write("\n");
                // System.out.println();
            }

            System.out.println("finished writing, closing file");
            writer.close();
        } catch (IOException e) {
            System.err.println("error while writing to file");
            e.printStackTrace();
            return;
        }
    }
}
