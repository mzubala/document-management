package pl.com.bottega.documentmanagement.application.marsstatepattern;

/**
 * Created by Dell on 2016-08-28.
 */
public class EastState extends MarsRoverState {

    public EastState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void moveRight() {
        marsRover.setState(new SouthEastState(marsRover));
    }

    @Override
    public void moveLeft() {
        marsRover.setState(new NorthEastState(marsRover));
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.getX() + 1, position.getY()));
    }

    @Override
    public String direction() {
        return "EAST";
    }
}
