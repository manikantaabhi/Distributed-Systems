import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SimpleColorImage {

    public static void main(String[] args) {
        // Hardcoded 10x10 RGB pixel values
        int[][][] pixelArray = {
            {{255, 0, 0}, {255, 128, 0}, {255, 255, 0}, {128, 255, 0}, {0, 255, 0}, {0, 255, 128}, {0, 255, 255}, {0, 128, 255}, {0, 0, 255}, {128, 0, 255}},
            {{128, 0, 0}, {128, 64, 0}, {128, 128, 0}, {64, 128, 0}, {0, 128, 0}, {0, 128, 64}, {0, 128, 128}, {0, 64, 128}, {0, 0, 128}, {64, 0, 128}},
            {{255, 0, 255}, {255, 128, 128}, {255, 255, 128}, {128, 255, 128}, {128, 255, 255}, {128, 128, 255}, {255, 128, 255}, {255, 255, 255}, {192, 192, 192}, {0, 0, 0}},
            {{0, 0, 0}, {64, 64, 64}, {128, 128, 128}, {192, 192, 192}, {255, 255, 255}, {0, 128, 0}, {255, 0, 0}, {0, 0, 255}, {128, 0, 128}, {255, 255, 0}},
            {{0, 0, 255}, {0, 255, 0}, {255, 0, 0}, {0, 255, 255}, {255, 255, 0}, {255, 128, 0}, {255, 0, 128}, {128, 0, 0}, {128, 128, 0}, {0, 128, 128}},
            {{255, 192, 203}, {255, 105, 180}, {255, 20, 147}, {255, 105, 180}, {255, 20, 147}, {255, 0, 255}, {0, 255, 0}, {0, 0, 0}, {255, 255, 255}, {128, 128, 128}},
            {{240, 248, 255}, {135, 206, 250}, {0, 191, 255}, {0, 0, 255}, {0, 0, 139}, {0, 0, 128}, {0, 0, 0}, {255, 255, 0}, {255, 165, 0}, {255, 0, 0}},
            {{255, 255, 255}, {0, 0, 0}, {128, 128, 128}, {255, 192, 203}, {255, 105, 180}, {255, 20, 147}, {255, 0, 0}, {255, 255, 0}, {0, 255, 0}, {0, 0, 255}},
            {{240, 248, 255}, {135, 206, 250}, {0, 191, 255}, {0, 0, 255}, {0, 0, 139}, {0, 0, 128}, {0, 0, 0}, {255, 255, 0}, {255, 165, 0}, {255, 0, 0}},
            {{255, 192, 203}, {255, 105, 180}, {255, 20, 147}, {255, 0, 255}, {0, 255, 255}, {0, 255, 0}, {0, 0, 0}, {255, 255, 255}, {128, 128, 128}, {0, 0, 0}}
        };

        // Create a BufferedImage
        int width = pixelArray[0].length;
        int height = pixelArray.length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Set pixel values in the BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = pixelArray[y][x][0];
                int green = pixelArray[y][x][1];
                int blue = pixelArray[y][x][2];
                int rgbValue = (red << 16) | (green << 8) | blue; // Combine RGB into a single integer
                image.setRGB(x, y, rgbValue);
            }
        }

        // Save the image
        try {
            ImageIO.write(image, "png", new File("color_image.png"));
            System.out.println("Image saved as color_image.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
