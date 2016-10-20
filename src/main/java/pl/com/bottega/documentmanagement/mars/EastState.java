package pl.com.bottega.documentmanagement.mars;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class EastState extends MarsRoverState {

    public EastState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.getX()+1,position.getY()));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new SouthEastState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new NorthEastState(marsRover));
    }

    @Override
    public String direction() {
        return "East";
    }
}
