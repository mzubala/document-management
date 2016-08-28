package pl.com.bottega.mars;

/**
 * Created by maciuch on 28.08.16.
 */
public class SouthWestState extends MarsRoverState {
    public SouthWestState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.x() + 1, position.y() - 1));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new SouthState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new WestState(marsRover));
    }

    @Override
    public String direction() {
        return "SOUTH_WEST";
    }
}
