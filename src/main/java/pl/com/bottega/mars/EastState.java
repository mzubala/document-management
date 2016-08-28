package pl.com.bottega.mars;

import javafx.geometry.Pos;

/**
 * Created by maciuch on 28.08.16.
 */
public class EastState extends MarsRoverState {
    public EastState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.x() - 1, position.y()));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new NorthEastState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new SouthEastState(marsRover));
    }

    @Override
    public String direction() {
        return "EAST";
    }
}
