package pl.com.bottega.documentmanagement.mars;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class NorthState extends MarsRoverState{


    public NorthState(MarsRover marsRover) {
        super(marsRover);
    }

    @Override
    public void move() {
        Position position = marsRover.position();
        marsRover.setPosition(new Position(position.getX(),position.getY()+1));
    }

    @Override
    public void rotateRight() {
        marsRover.setState(new NorthEastState(marsRover));
    }

    @Override
    public void rotateLeft() {
        marsRover.setState(new NorthEastState(marsRover));
    }

    @Override
    public String direction() {
        return "North";
    }
}
