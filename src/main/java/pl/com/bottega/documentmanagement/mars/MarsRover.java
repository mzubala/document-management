package pl.com.bottega.documentmanagement.mars;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class MarsRover {

    private Position position;
    private Direction direction;
    private MarsRoverState currentState;


    public MarsRover() {
        this.position = new Position(0,0);
        this.direction = Direction.N;
        this.currentState = new NorthState(this);
    }


    public void move(){
        currentState.move();
    }
    public void rotateRight(){
        currentState.rotateRight();
    }
    public void rotateLeft(){
        currentState.rotateLeft();
    }

    public Position position(){
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setState(MarsRoverState state) {
        this.currentState = state;
    }
}
