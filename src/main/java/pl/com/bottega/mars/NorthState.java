package pl.com.bottega.mars;

/**
 * Created by maciuch on 28.08.16.
 */
public class NorthState extends MarsRoverState {

    public NorthState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.x(), position.y() + 1));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new NorthEastState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new NorthWestState(marsRover));
    }

    @Override
    public String direction() {
        return "NORTH";
    }
}
