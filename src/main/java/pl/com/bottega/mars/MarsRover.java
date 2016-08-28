package pl.com.bottega.mars;

/**
 * Created by Dell on 2016-08-28.
 */
public class MarsRover {

    private Position position;
    private MarsRoverState currentState;

    public MarsRover() {
        this.position = new Position(0, 0);
        this.currentState = new NorthState(this);
    }

    public void move() {
        currentState.move();
    }

    public void rotateRight() {
        currentState.rotateRight();
    }

    public void rotateLeft() {
        currentState.rotateLeft();
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