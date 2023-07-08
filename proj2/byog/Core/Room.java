package byog.Core;

public class Room {
    public Position position;
    public int width;
    public int height;

    public Room() {}

    public Room(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }
}

