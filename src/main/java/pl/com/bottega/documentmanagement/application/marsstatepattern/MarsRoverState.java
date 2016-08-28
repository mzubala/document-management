package pl.com.bottega.documentmanagement.application.marsstatepattern;

/**
 * Created by Dell on 2016-08-28.
 */
public abstract class MarsRoverState {

    protected MarsRover marsRover;

    protected MarsRoverState(MarsRover marsRover) {
        this.marsRover = marsRover;
    }

    public abstract void moveRight();
    public abstract void moveLeft();
    public abstract void move();
    public abstract String direction();

}
