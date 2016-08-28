package pl.com.bottega.documentmanagement.application.marsstatepattern;


/**
 * Created by Dell on 2016-08-28.
 */
public class NorthState extends MarsRoverState {

    public NorthState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void moveRight() {
        marsRover.setState(new NorthEastState(marsRover));
    }

    @Override
    public void moveLeft() {
        marsRover.setState(new NorthWestState(marsRover));

    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.getX(), position.getY() + 1));
    }

    @Override
    public String direction() {
        return "NORTH";
    }
}
