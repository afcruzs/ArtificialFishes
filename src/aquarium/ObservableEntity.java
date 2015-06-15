package aquarium;

import java.awt.Point;
import java.awt.Rectangle;

/*
 * If a object is observable then it belongs to "world"
 * and can be observed by a Fish (Or maybe by someone else...)
 * 
 * 
 */
public interface ObservableEntity {
	Point getUbication();

	Rectangle getBoundingBox();
}
