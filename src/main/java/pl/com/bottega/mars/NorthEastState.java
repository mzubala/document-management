package pl.com.bottega.mars;

import javafx.geometry.Pos;

/**
 * Created by maciuch on 28.08.16.
 */
public class NorthEastState extends MarsRoverState {

    public NorthEastState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.x() - 1, position.y() + 1));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new NorthState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new EastState(marsRover));
    }

    @Override
    public String direction() {
        return "NORTH_EAST";
    }
}
