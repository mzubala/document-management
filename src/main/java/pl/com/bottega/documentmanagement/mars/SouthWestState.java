package pl.com.bottega.documentmanagement.mars;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class SouthWestState extends MarsRoverState {

    public SouthWestState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.getX()-1,position.getY()-1));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new WestState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new SouthState(marsRover));
    }

    @Override
    public String direction() {
        return "South-West";
    }
}
