
package aquarium;

import java.awt.Rectangle;

public class GeometryUtils {
	public static Rectangle[] difference(Rectangle rectA, Rectangle rectB) {
        if (rectB == null || !rectA.intersects(rectB) || rectB.contains(rectA))
            return new Rectangle[0];
 
        Rectangle result[] = null;
        Rectangle top = null, bottom = null, left = null, right = null;
        int rectCount = 0;
 
        //compute the top rectangle
        int raHeight = rectB.y - rectA.y;
        if(raHeight>0) {
            top = new Rectangle(rectA.x, rectA.y, rectA.width, raHeight);
            rectCount++;
        }
 
        //compute the bottom rectangle
        int rbY = rectB.y + rectB.height;
        int rbHeight = rectA.height - (rbY - rectA.y);
        if ( rbHeight > 0 && rbY < rectA.y + rectA.height ) {
            bottom = new Rectangle(rectA.x, rbY, rectA.width, rbHeight);
            rectCount++;
        }
 
        int rectAYH = rectA.y+rectA.height;
        int y1 = rectB.y > rectA.y ? rectB.y : rectA.y;
        int y2 = rbY < rectAYH ? rbY : rectAYH;
        int rcHeight = y2 - y1;
 
        //compute the left rectangle
        int rcWidth = rectB.x - rectA.x;
        if ( rcWidth > 0 && rcHeight > 0 ) {
            left = new Rectangle(rectA.x, y1, rcWidth, rcHeight);
            rectCount++;
        }
 
        //compute the right rectangle
        int rbX = rectB.x + rectB.width;
        int rdWidth = rectA.width - (rbX - rectA.x);
        if ( rdWidth > 0 ) {
            right = new Rectangle(rbX, y1, rdWidth, rcHeight);
            rectCount++;
        }
 
        result = new Rectangle[rectCount];
        rectCount = 0;
        if(top != null)
            result[rectCount++] = top;
        if(bottom != null)
            result[rectCount++] = bottom;
        if(left != null)
            result[rectCount++] = left;
        if(right != null)
            result[rectCount++] = right;
        return result;
    }
}
