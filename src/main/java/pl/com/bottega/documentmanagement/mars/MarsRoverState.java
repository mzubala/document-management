package pl.com.bottega.documentmanagement.mars;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public abstract class MarsRoverState {
    protected MarsRover marsRover;

    public MarsRoverState(MarsRover marsRover){
        this.marsRover = marsRover;
    }

    public abstract void move();
    public abstract void rotateRight();
    public abstract void rotateLeft();
    public abstract String direction();

}
