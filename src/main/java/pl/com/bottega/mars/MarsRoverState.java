package pl.com.bottega.mars;

/**
 * Created by maciuch on 28.08.16.
 */
public abstract class MarsRoverState {

    protected MarsRover marsRover;

    public MarsRoverState(MarsRover marsRover) {
        this.marsRover = marsRover;
    }

    public abstract void move();

    public abstract void rotateRight();

    public abstract void rotateLeft();

    public abstract String direction();
}
