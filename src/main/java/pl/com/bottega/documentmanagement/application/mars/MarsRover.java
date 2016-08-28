package pl.com.bottega.documentmanagement.application.mars;

import static pl.com.bottega.documentmanagement.application.mars.Direction.NORTH;

/**
 * Created by Dell on 2016-08-28.
 */
public class MarsRover {

    private Position position;
    private Direction direction;

    public MarsRover() {
        this.position = new Position(0, 0);
        this.direction = NORTH;
    }

    public void move() {
        switch (direction) {
            case NORTH:
                position = new Position(position.getX(), position.getY() + 1);
            case NORTH_EAST:
                position = new Position(position.getX() + 1, position.getY() + 1);
            case EAST:
                position = new Position(position.getX() + 1, position.getY());
            case SOUTH_EAST:
                position = new Position(position.getX() + 1, position.getY() - 1);
            case SOUTH:
                position = new Position(position.getX(), position.getY() - 1);
            case SOUTH_WEST:
                position = new Position(position.getX() - 1, position.getY() - 1);
            case WEST:
                position = new Position(position.getX() - 1, position.getY());
            case NORTH_WEST:
                position = new Position(position.getX() - 1, position.getY() + 1);
        }

    }

    public void rotateRight() {
        int directionValue = direction.getDirection();
        if (directionValue == 8)
            direction = Direction.changeDirection(1);
        else
            direction = Direction.changeDirection(directionValue + 1);
    }

    public void rotateLeft() {
        int directionValue = direction.getDirection();
        if (directionValue == 1)
            direction = Direction.changeDirection(8);
        else
            direction = Direction.changeDirection(directionValue - 1);
    }

    public Position position() {
        return position;
    }

    public String getDirection() {
        return direction.toString();
    }
}
