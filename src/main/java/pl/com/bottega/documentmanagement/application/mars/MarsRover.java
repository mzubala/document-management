package pl.com.bottega.documentmanagement.application.mars;

import static pl.com.bottega.documentmanagement.application.mars.Direction.NORTH;

/**
 * Created by Dell on 2016-08-28.
 */
class MarsRover {

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
                break;
            case NORTHEAST:
                position = new Position(position.getX() + 1, position.getY() + 1);
                break;
            case EAST:
                position = new Position(position.getX() + 1, position.getY());
                break;
            case SOUTHEAST:
                position = new Position(position.getX() + 1, position.getY() - 1);
                break;
            case SOUTH:
                position = new Position(position.getX(), position.getY() - 1);
                break;
            case SOUTHWEST:
                position = new Position(position.getX() - 1, position.getY() - 1);
                break;
            case WEST:
                position = new Position(position.getX() - 1, position.getY());
                break;
            case NORTHWEST:
                position = new Position(position.getX() - 1, position.getY() + 1);
                break;
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
