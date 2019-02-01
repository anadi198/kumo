package com.kennycason.kumo.collide.checkers;

import com.kennycason.kumo.abst.PointAbst;
import com.kennycason.kumo.collide.Collidable;
import com.kennycason.kumo.image.CollisionRaster;

/**
 * Created by kenny on 7/1/14.
 */
public class RectanglePixelCollisionChecker implements CollisionChecker {
    private static final RectangleCollisionChecker RECTANGLE_COLLISION_CHECKER = new RectangleCollisionChecker();

    /*
          ax,ay ___________ax + a.width
            |                 |
            |                 |
            |  bx, by_________|__ bx + b.width
            |  |(INTERSECTION)|       |
            |__|______________|       |
            ay + height               |
               |______________________|
             by + height
          */
    @Override
    public boolean collide(final Collidable collidable, final Collidable collidable2) {
        // check if bounding boxes intersect
        if (!RECTANGLE_COLLISION_CHECKER.collide(collidable, collidable2)) {
            return false;
        }

        final PointAbst position = collidable.getPosition();
        final PointAbst position2 = collidable2.getPosition();
        final CollisionRaster collisionRaster = collidable.getCollisionRaster();
        final CollisionRaster collisionRaster2 = collidable2.getCollisionRaster();

        // get the overlapping box
        final int startX = Math.max(position.getX(), position2.getX());
        final int endX = Math.min(position.getX() + collidable.getDimension().getWidth(),
                                  position2.getX() + collidable2.getDimension().getWidth());

        final int startY = Math.max(position.getY(), position2.getY());
        final int endY = Math.min(position.getY() + collidable.getDimension().getHeight(),
                                  position2.getY() + collidable2.getDimension().getHeight());

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                // compute offsets for surface
                if (!collisionRaster2.isTransparent(x - position2.getX(), y - position2.getY())
                        && !collisionRaster.isTransparent(x - position.getX(), y - position.getY())) {
                    return true;
                }
            }
        }
        return false;
    }

}
