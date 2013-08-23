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

public class DrawImage_PlaybookOffPage {

    private static final int HEIGHT = 800;

    public static InputStream drawImage() {
        BufferedImage image = new BufferedImage(1000, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics drawable = image.getGraphics();
        drawable.setColor(new Color(0, 0, 0, 0));
        drawable.fillRect(0, 0, 1000, HEIGHT);

        drawable.setColor(new Color(30, 30, 30, 255));
        drawable.setFont(new Font("Segoe Print", Font.PLAIN, 16));
//        drawable.setFont(new Font("Segoe Script", Font.PLAIN, 16));
        drawable.drawString("select formation and play", 660, 280);
        drawable.drawString("use those buttons for new/save/delete", 540, 365);
        drawable.drawString("left-click a position to change its action", 20, 600);
        drawable.drawString("select a type of action and left-click on the field", 20, 620);
        drawable.drawString("(carrying, receiving and blocking can be set multiple times to define complex routes)", 20, 640);
        drawable.drawString("right-click if the route is defined", 20, 660);
        drawable.drawString("pass plays need a primary receiver to be valid, don't forget to activate the checkbox", 72, 700);
        drawable.drawString("run plays can have exactly one carry route", 72, 720);
        drawable.drawString("invalid plays are marked with a red border around the field - don't try to save it", 80, 740);
        drawable.drawString("(tip: you must have exactly one red colored route)", 80, 760);
        drawable.drawString("do not forget to SAVE your play when you're done", 47, 790);

        try {
            /* Write the image to a buffer. */
            ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imagebuffer);
            /* Return a stream from the buffer. */
            imagebuffer.writeTo(new FileOutputStream(new File("WebContent/VAADIN/themes/fm/img/help/playbook.png")));
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
