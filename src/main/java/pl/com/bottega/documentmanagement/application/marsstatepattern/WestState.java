package pl.com.bottega.documentmanagement.application.marsstatepattern;

/**
 * Created by Dell on 2016-08-28.
 */
public class WestState extends MarsRoverState {

    protected WestState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void moveRight() {
        marsRover.setState(new NorthWestState(marsRover));

    }

    @Override
    public void moveLeft() {
        marsRover.setState(new SouthWestState(marsRover));
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.getX() - 1, position.getY()));
    }

    @Override
    public String direction() {
        return "WEST";
    }
}
