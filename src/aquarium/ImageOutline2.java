package aquarium;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import javax.imageio.ImageIO;

import java.io.File;
import java.util.Date;

import javax.swing.*;

/* Motorcycle image courtesy of ShutterStock
http://www.shutterstock.com/pic-13585165/stock-vector-travel-motorcycle-silhouette.html */
class ImageOutline2 {

    public static Area getOutline(BufferedImage image, Color color, boolean include, int tolerance) {
        Area area = new Area();
        for (int x=0; x<image.getWidth(); x++) {
            for (int y=0; y<image.getHeight(); y++) {
                Color pixel = new Color(image.getRGB(x,y));
                if (include) {
                    if (isIncluded(color, pixel, tolerance)) {
                        Rectangle r = new Rectangle(x,y,1,1);
                        area.add(new Area(r));
                    }
                } else {
                    if (!isIncluded(color, pixel, tolerance)) {
                        Rectangle r = new Rectangle(x,y,1,1);
                        area.add(new Area(r));
                    }
                }
            }
        }
        return area;
    }

    public static boolean isIncluded(Color target, Color pixel, int tolerance) {
        int rT = target.getRed();
        int gT = target.getGreen();
        int bT = target.getBlue();
        int rP = pixel.getRed();
        int gP = pixel.getGreen();
        int bP = pixel.getBlue();
        return(
            (rP-tolerance<=rT) && (rT<=rP+tolerance) &&
            (gP-tolerance<=gT) && (gT<=gP+tolerance) &&
            (bP-tolerance<=bT) && (bT<=bP+tolerance) );
    }

    public static BufferedImage drawOutline(int w, int h, Area area) {
        final BufferedImage result = new BufferedImage(
            w,
            h,
            BufferedImage.TYPE_INT_RGB);
        Graphics2D g = result.createGraphics();

        g.setColor(Color.white);
        g.fillRect(0,0,w,h);

        g.setClip(area);
        g.setColor(Color.red);
        g.fillRect(0,0,w,h);

        g.setClip(null);
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.blue);
        g.draw(area);

        return result;
    }

    public static BufferedImage createAndWrite(
        BufferedImage image,
        Color color,
        boolean include,
        int tolerance,
        String name)
        throws Exception {
        int w = image.getWidth();
        int h = image.getHeight();

        System.out.println("Get Area: " + new Date() + " - " + name);
        Area area = getOutline(image, color, include, tolerance);
        
        ConstructFishesArea.save("fishOutLine", AffineTransform.getTranslateInstance(0,0).createTransformedShape(area));
        
        System.out.println("saved!");
        System.exit(0);
        
        
        System.out.println("Got Area: " + new Date() + " - " + name);

        final BufferedImage result = drawOutline(w,h,area);
        displayAndWriteImage(result, name);
        return result;
    }

    public static void displayAndWriteImage(BufferedImage image, String fileName) throws Exception {
        ImageIO.write(image, "png", new File(fileName));
        JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(image)));
    }

    public static void main(String[] args) throws Exception {
        final BufferedImage outline = ImageIO.read(new File("fish0.jpg"));
        BufferedImage crop = outline;
        displayAndWriteImage(crop, "motorcycle-01.png");

        BufferedImage crude = createAndWrite(crop, Color.white, false, 60, "motorcycle-02.png");

        BufferedImage combo = createAndWrite(crude, Color.red, true, 0, "motorcycle-03.png");
    }
}