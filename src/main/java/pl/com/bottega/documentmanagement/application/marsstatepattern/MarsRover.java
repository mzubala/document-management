package pl.com.bottega.documentmanagement.application.marsstatepattern;

/**
 * Created by Dell on 2016-08-28.
 */
public class MarsRover {
    private Position position;
    private String direction;
    private MarsRoverState currentState;

    public MarsRover() {
        this.position = new Position(0, 0);
        this.direction = "NORTH";
        this.currentState = new NorthState(this);
    }

    public void move() {
        currentState.move();
    }

    public void rotateRight() {
        currentState.moveRight();
    }

    public void rotateLeft() {
        currentState.moveLeft();
    }

    public Position position() {
        return position;
    }

    public String getDirection() {
        return currentState.direction();
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setState(MarsRoverState state) {
        this.currentState = state;
    }
}
