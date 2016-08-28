package pl.com.bottega.documentmanagement.application.marsstatepattern;

/**
 * Created by Dell on 2016-08-28.
 */
public class NorthEastState extends MarsRoverState {

    protected NorthEastState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void moveRight() {
        marsRover.setState(new EastState(marsRover));
    }

    @Override
    public void moveLeft() {
        marsRover.setState(new NorthState(marsRover));
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.getX() + 1, position.getY() + 1));
    }

    @Override
    public String direction() {
        return "NORTH-EAST";
    }
}
