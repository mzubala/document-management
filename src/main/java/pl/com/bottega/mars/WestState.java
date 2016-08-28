package pl.com.bottega.mars;

/**
 * Created by maciuch on 28.08.16.
 */
public class WestState extends MarsRoverState {
    public WestState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.x() + 1, position.y()));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new SouthWestState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new NorthWestState(marsRover));
    }

    @Override
    public String direction() {
        return "WEST";
    }

}
