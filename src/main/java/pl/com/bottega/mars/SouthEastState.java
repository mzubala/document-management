package pl.com.bottega.mars;

import javafx.geometry.Pos;

/**
 * Created by maciuch on 28.08.16.
 */
public class SouthEastState extends MarsRoverState {
    public SouthEastState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.x() - 1, position.y() - 1));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new EastState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new SouthState(marsRover));
    }

    @Override
    public String direction() {
        return "SOUTH_EAST";
    }
}
