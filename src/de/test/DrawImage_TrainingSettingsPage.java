package de.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class DrawImage_TrainingSettingsPage {

    private static final int HEIGHT = 670;

    public static InputStream drawImage() {
        BufferedImage image = new BufferedImage(1000, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics drawable = image.getGraphics();
        drawable.setColor(new Color(0, 0, 0, 0));
        drawable.fillRect(0, 0, 1000, HEIGHT);

        drawable.setColor(new Color(30, 30, 30, 255));
        drawable.setFont(new Font("Segoe Print", Font.PLAIN, 16));
//        drawable.setFont(new Font("Segoe Script", Font.PLAIN, 16));
        drawable.drawString("select individual training for each player (click on a column to order the table)", 220, 15);
        drawable.drawString("select the formation and play you want to train your team", 15, 600);
        drawable.drawString("do this seperately for your offense and defense", 15, 620);
        drawable.drawString("when selecting a new formation to train, " +
        		"you have to select a new play of that formation", 15, 640);
        drawable.drawString("when creating a new formation or play you should train it before using in a game", 15, 660);

        try {
            /* Write the image to a buffer. */
            ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imagebuffer);
            /* Return a stream from the buffer. */
            imagebuffer.writeTo(new FileOutputStream(new File("WebContent/VAADIN/themes/fm/img/help/trainingsettings.png")));
            return new ByteArrayInputStream(imagebuffer.toByteArray());
        } catch(IOException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        drawImage();
        System.out.println("done");
        System.exit(0);
    }
}
