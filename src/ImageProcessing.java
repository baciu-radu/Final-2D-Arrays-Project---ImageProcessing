import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
public class ImageProcessing {
    public static void main(String[] args) {
        // The provided images are apple.jpg, flower.jpg, and kitten.jpg
        int[][] imageData = imgToTwoD("./apple.jpg");
        // Or load your own image using a URL!
        //int[][] imageData = imgToTwoD("https://content.codecademy.com/projects/project_thumbnails/phaser/bug-dodger.png");
        // viewImageData(imageData);
        int[][] trimmed = trimBorders(imageData, 60);
        twoDToImage(trimmed, "./trimmed_apple.jpg");
        // int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 50)), 200, 20, 40)));
        // Painting with pixels

        int[][] negativeImageArray = negativeColor(imageData);
        twoDToImage(negativeImageArray, "./negative_apple.jpg");

        int[][] stretchedImage = stretchHorizontally(imageData);
        twoDToImage(stretchedImage, "./stretched_apple.jpg");

        int[][] shrinkedImage = shrinkVertically(imageData);
        twoDToImage(shrinkedImage, "./shrinked_apple.jpg");

        int[][] invertedImage = invertImage(imageData);
        twoDToImage(invertedImage, "./inverted_apple.jpg");

        int[][] filteredImage = colorFilter(imageData,80,15,100);
        twoDToImage(filteredImage, "./filtered_apple.jpg");

        int[][] paintedImage = new int[500][500];
        twoDToImage(paintRandomImage(paintedImage), "./random_painted_image.jpg");

        int[][] squareOnImage = paintRectangle(imageData,100,100,15,50,100);
        twoDToImage(squareOnImage, "./square_painted_image.jpg");

        int[][] rectanglesOnImage = generateRectangles(imageData, 4);
        twoDToImage(rectanglesOnImage, "./squares_painted_image.jpg");

    }
    // Image Processing Methods
    public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
        // Example Method
        if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
            int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
            for (int i = 0; i < trimmedImg.length; i++) {
                for (int j = 0; j < trimmedImg[i].length; j++) {
                    trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
                }
            }
            return trimmedImg;
        } else {
            System.out.println("Cannot trim that many pixels from the given image.");
            return imageTwoD;
        }
    }
    public static int[][] negativeColor(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int [][] negativeImage = new int[imageTwoD.length][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD.length; i++) {
            for (int j = 0; j < imageTwoD[i].length; j++) {
                int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
                // System.out.println("OLD RED = "+ rgba[0]);
                rgba[0] = 255- rgba[0];
                // System.out.println("Neg RED = "+ rgba[0]);
                // System.out.println("OLD GREEN = "+ rgba[1]);
                rgba[1] = 255- rgba[1];
                // System.out.println("Neg GREEN = "+ rgba[1]);
                // System.out.println("OLD BLUE = "+ rgba[2]);
                rgba[2] = 255- rgba[2];
                // System.out.println("Neg BLUE = "+ rgba[2]);
                negativeImage[i][j] = getColorIntValFromRGBA(rgba);
                // System.out.println("New Hex = "+ negativeImage[i][j]);
            }
        }

        return negativeImage;
    }
    public static int[][] stretchHorizontally(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int [][] modifiedImage =  new int[imageTwoD.length][imageTwoD[0].length*2];
        for (int i = 0; i < imageTwoD.length; i++) {
            for (int j = 0; j < imageTwoD[i].length; j++) {
                int it = j*2;
                modifiedImage[i][it] = imageTwoD[i][j];
                modifiedImage[i][it+1] = imageTwoD[i][j];

            }
        }
        return modifiedImage;
    }
    public static int[][] shrinkVertically(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int[][] modifiedImage = new int[imageTwoD.length/2][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD[0].length; i++) {
            for (int j = 0; j < imageTwoD.length-1; j+=2){
                modifiedImage[j/2][i] = imageTwoD[j][i];
            }
        }
        return modifiedImage;
    }
    public static int[][] invertImage(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int[][] invertedImage = new int[imageTwoD.length][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD.length; i++){
            for (int j = 0; j < imageTwoD[i].length; j++){
                invertedImage[i][j] = imageTwoD[(imageTwoD.length-1)-i][(imageTwoD[i].length-1)-j];
            }
        }

        return invertedImage;
    }
    public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
        // TODO: Fill in the code for this method
        int[][] filtredImage = new int[imageTwoD.length][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD.length; i++){
            for (int j = 0; j < imageTwoD[i].length; j++){
                int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
                rgba[0] += redChangeValue;
                if(rgba[0]<0){
                    rgba[0]=0;
                } else if(rgba[0]>255){rgba[0]=255;}
                rgba[1] += greenChangeValue;
                if(rgba[1]<0){
                    rgba[1]=0;
                }else if(rgba[1]>255){rgba[1]=255;}
                rgba[2] += blueChangeValue;
                if(rgba[2]<0){
                    rgba[2]=0;
                }else if(rgba[2]>255){rgba[2]=255;}
                filtredImage[i][j] = getColorIntValFromRGBA(rgba);
            }
        }
        return filtredImage;
    }
    // Painting Methods
    public static int[][] paintRandomImage(int[][] canvas) {
        // TODO: Fill in the code for this method

        Random rand = new Random();
        for (int i = 0; i < canvas.length; i++){
            for (int j = 0; j < canvas[i].length; j++){
                int random1 = rand.nextInt(256);
                int random2 = rand.nextInt(256);
                int random3 = rand.nextInt(256);
                int[] randomPixel = {random1,random2,random3, 255};
                canvas[i][j] = getColorIntValFromRGBA(randomPixel);
            }
        }

        return canvas;
    }
    public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
        // TODO: Fill in the code for this method
        for (int i = 0; i < canvas.length; i++){
            for (int j = 0; j < canvas[i].length; j++){
                if((i>=rowPosition) && (i<=rowPosition + width)){
                    if((j>=colPosition) && (j<=colPosition + height)){
                        canvas[i][j] = color;
                    }
                }
            }

        }

        return canvas;
    }
    public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
        // TODO: Fill in the code for this method
        Random rand = new Random();
        for(int n = 0 ; n<numRectangles; n++){
            int width = rand.nextInt(canvas[0].length);
            int height =  rand.nextInt(canvas.length);
            int rowPosition = rand.nextInt(canvas.length-height);
            int columnPosition = rand.nextInt(canvas[0].length-width);
            int random1 = rand.nextInt(256);
            int random2 = rand.nextInt(256);
            int random3 = rand.nextInt(256);
            int[] randomPixel = {random1,random2,random3, 255};
            int color = getColorIntValFromRGBA(randomPixel);
            canvas = paintRectangle(canvas, width, height, rowPosition, columnPosition, color);
        }

        return canvas;
    }
    // Utility Methods
    public static int[][] imgToTwoD(String inputFileOrLink) {
        try {
            BufferedImage image = null;
            if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
                URL imageUrl = new URL(inputFileOrLink);
                image = ImageIO.read(imageUrl);
                if (image == null) {
                    System.out.println("Failed to get image from provided URL.");
                }
            } else {
                image = ImageIO.read(new File(inputFileOrLink));
            }
            int imgRows = image.getHeight();
            int imgCols = image.getWidth();
            int[][] pixelData = new int[imgRows][imgCols];
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    pixelData[i][j] = image.getRGB(j, i);
                }
            }
            return pixelData;
        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getLocalizedMessage());
            return null;
        }
    }
    public static void twoDToImage(int[][] imgData, String fileName) {
        try {
            int imgRows = imgData.length;
            int imgCols = imgData[0].length;
            BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    result.setRGB(j, i, imgData[i][j]);
                }
            }
            File output = new File(fileName);
            ImageIO.write(result, "jpg", output);
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e.getLocalizedMessage());
        }
    }
    public static int[] getRGBAFromPixel(int pixelColorValue) {
        Color pixelColor = new Color(pixelColorValue);
        return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
    }
    public static int getColorIntValFromRGBA(int[] colorData) {
        if (colorData.length == 4) {
            Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
            return color.getRGB();
        } else {
            System.out.println("Incorrect number of elements in RGBA array.");
            return -1;
        }
    }
    public static void viewImageData(int[][] imageTwoD) {
        if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
            int[][] rawPixels = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rawPixels[i][j] = imageTwoD[i][j];
                }
            }
            System.out.println("Raw pixel data from the top left corner.");
            System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
            int[][][] rgbPixels = new int[3][3][4];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
                }
            }
            System.out.println();
            System.out.println("Extracted RGBA pixel data from top the left corner.");
            for (int[][] row : rgbPixels) {
                System.out.print(Arrays.deepToString(row) + System.lineSeparator());
            }
        } else {
            System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
        }
    }
}