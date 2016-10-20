package pl.com.bottega.documentmanagement.mars;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class WestState extends MarsRoverState {

    public WestState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.getX()-1,position.getY()));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new NorthWestState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new SouthWestState(marsRover));
    }

    @Override
    public String direction() {
        return "West";
    }
}
