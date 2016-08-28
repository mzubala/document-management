package pl.com.bottega.mars;

/**
 * Created by maciuch on 28.08.16.
 */
public class SouthState extends MarsRoverState {
    public SouthState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.x(), position.y() - 1));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new SouthEastState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new SouthWestState(marsRover));
    }

    @Override
    public String direction() {
        return "SOUTH_STATE";
    }
}
