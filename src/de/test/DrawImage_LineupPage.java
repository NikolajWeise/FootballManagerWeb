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

public class DrawImage_LineupPage {

    public static InputStream drawImage() {
        BufferedImage image = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_ARGB);
        Graphics drawable = image.getGraphics();
        drawable.setColor(new Color(0, 0, 0, 0));
        drawable.fillRect(0, 0, 1000, 500);

        drawable.setColor(new Color(30, 30, 30, 255));
        drawable.setFont(new Font("Segoe Print", Font.PLAIN, 16));
//        drawable.setFont(new Font("Segoe Script", Font.PLAIN, 16));
        drawable.drawString("select the position type at the top which you want to line up", 10, 200);
        drawable.drawString("drag&drop the players in the table to order them", 10, 220);
        drawable.drawString("click on a player to show his detailed stats", 10, 240);

        try {
            /* Write the image to a buffer. */
            ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imagebuffer);
            /* Return a stream from the buffer. */
            imagebuffer.writeTo(new FileOutputStream(new File("WebContent/VAADIN/themes/fm/img/help/lineup.png")));
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
