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

public class DrawImage_FormationOffPage {

    public static InputStream drawImage() {
        BufferedImage image = new BufferedImage(1000, 550, BufferedImage.TYPE_INT_ARGB);
        Graphics drawable = image.getGraphics();
        drawable.setColor(new Color(0, 0, 0, 0));
        drawable.fillRect(0, 0, 1000, 550);

        drawable.setColor(new Color(30, 30, 30, 255));
        drawable.setFont(new Font("Segoe Print", Font.PLAIN, 16));
//        drawable.setFont(new Font("Segoe Script", Font.PLAIN, 16));
        drawable.drawString("select formation", 750, 100);
        drawable.drawString("those buttons below", 750, 150);
        drawable.drawString("are self-explaining :)", 750, 170);
        drawable.drawString("click a position to change its position type", 75, 385);
        drawable.drawString("drag and drop a position whereever you want to create your own formation", 75, 405);
        drawable.drawString("you cannot drag the offensive line nor change their type (they are red-colored)", 75, 425);
        drawable.drawString("do not forget to have exactly seven players at the line of scrimmage", 75, 445);

        drawable.setColor(new Color(255, 30, 30, 255));
        drawable.drawString("Attention: Every edited position losts his actions in each play of this formation!", 69, 480);
        drawable.drawString("(so be careful with editing pre-defined formations)", 100, 500);

        try {
            /* Write the image to a buffer. */
            ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imagebuffer);
            /* Return a stream from the buffer. */
            imagebuffer.writeTo(new FileOutputStream(new File("WebContent/VAADIN/themes/fm/img/help/formation.png")));
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
