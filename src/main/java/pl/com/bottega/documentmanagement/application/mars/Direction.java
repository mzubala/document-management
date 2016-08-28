package pl.com.bottega.documentmanagement.application.mars;

/**
 * Created by Dell on 2016-08-28.
 */
enum Direction {

    NORTH(1), NORTHEAST(2), EAST(3), SOUTHEAST(4), SOUTH(5), SOUTHWEST(6), WEST(7), NORTHWEST(8);

    private int direction;

    private Direction(int x) {
        this.direction = x;
    }

    public int getDirection() {
        return direction;
    }

    public static Direction changeDirection(int direction) throws IllegalArgumentException {
        for (Direction d : Direction.values())
            if (direction == d.getDirection()) {
                return d;
            }
        throw new IllegalArgumentException();
    }
}
