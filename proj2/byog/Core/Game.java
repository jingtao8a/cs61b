package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import javafx.geometry.Pos;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int TIMES = 4;// 控制房间重复生成的上限次数
    private Random RANDOM;
    private List<Room> roomList = new LinkedList<>();
    private TETile[][] world;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        input = input.toLowerCase();
        long seed = -1;
        if (input.contains("n") && input.contains("s")) {
            int start = input.indexOf("n") + 1;
            int end = input.indexOf("s");
            try {
                seed = Long.parseLong(input.substring(start, end));
            } catch (Exception e) {
                throw new RuntimeException("seed has to be an integer");
            }
        }
        RANDOM = new Random(seed);

        world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        initializeRandomRooms();
        System.out.println(TETile.toString(world));
        makeRoomsConnected();
        return world;
    }

    private void initializeRandomRooms() {
        for (int i = 0; i < TIMES; ++i) {
            int x = RANDOM.nextInt(WIDTH);
            int y = RANDOM.nextInt(HEIGHT);
            int width = RANDOM.nextInt(WIDTH / 10) + 2;
            int height = RANDOM.nextInt(HEIGHT / 5) + 2;

            // 房间超出地图范围
            if (y + height + 1 >= HEIGHT || x + width + 1 >= WIDTH) {
                continue;
            }
            // 房间重叠
            if (isOverlap(x, y, width, height)) {
                continue;
            }

            for (int w = x; w <= x + width + 1; ++w) {
                for (int z = y; z <= y + height + 1; ++z) {
                    if (w == x || w == x + width + 1 || z == y || z == y + height + 1) {
                        world[w][z] = Tileset.WALL;
                    } else {
                    world[w][z] = Tileset.GRASS;
                    }
                }
            }
            roomList.add(new Room(new Position(x, y), width, height));
         }
    }

    private boolean isOverlap(int x, int y, int width, int height) {
        for (int  i = x; i <= x + width + 1; ++i) {
            for (int j = y; j <= y + height + 1; ++j) {
                if (world[i][j] == Tileset.WALL || world[i][j] == Tileset.GRASS) {
                    return true;
                }
            }
        }
        return false;
    }

    private void makeRoomsConnected() {
        roomList.sort((Room lhs, Room rhs)-> (lhs.position.x * lhs.position.x + lhs.position.y * lhs.position.y) -
                (rhs.position.x * rhs.position.x + rhs.position.y * rhs.position.y));
        System.out.println("size" + roomList.size());
        for (int index = 0; index < roomList.size() - 1; ++index) {
            connectTwoRoom(roomList.get(index), roomList.get(index + 1));
        }

    }

    private void connectTwoRoom(Room one, Room two) {
        while (true) {
            int onex = RANDOM.nextInt(one.width) + one.position.x + 1;
            int oney = RANDOM.nextInt(one.height) + one.position.y + 1;
            int twox = RANDOM.nextInt(two.width) + two.position.x + 1;
            int twoy = RANDOM.nextInt(two.height) + two.position.y + 1;

            if (!connectTwoPoint(new Position(onex, oney), new Position(twox, twoy))) {
                continue;
            }
            break;
        }
    }

    private boolean connectTwoPoint(Position one, Position two) {
        // 一条与y轴平行的路
        if (one.x == two.x) {
            if (one.y < two.y) {
                if (checkCanTwoPointsBeConnectedAlongY(one, two)) {
                    for (int i = one.y - 1; i <= two.y + 1; ++i) {
                        world[one.x - 1][i] = world[one.x + 1][i] = Tileset.WALL;
                        world[one.x][i] = Tileset.FLOOR;
                    }
                    return true;
                }
            } else { // two.y < one.y
                if (checkCanTwoPointsBeConnectedAlongY(two, one)) {
                    for (int i = two.y - 1; i <= one.y + 1; ++i) {
                        world[one.x - 1][i] = world[one.x + 1][i] = Tileset.WALL;
                        world[one.x][i] = Tileset.FLOOR;
                    }
                    return true;
                }
            }
            return false;
        }

        //一条与x轴平行的路
        if (two.y == one.y) {
            if (one.x < two.x) {
                if (checkCanTwoPointsBeConnectedAlongX(one, two)) {
                    for (int i = one.x - 1; i <= two.x + 1; ++i) {
                        world[i][one.y - 1] = world[i][one.y + 1] = Tileset.WALL;
                        world[i][one.y] = Tileset.FLOOR;
                    }
                    return true;
                }
            } else {
                if (checkCanTwoPointsBeConnectedAlongX(two, one)) {
                    for (int i = two.x - 1; i <= two.x +1; ++i) {
                        world[i][one.y - 1] = world[i][one.y + 1] = Tileset.WALL;
                        world[i][one.y] = Tileset.FLOOR;
                    }
                    return true;
                }
            }
            return false;
        }

        // 第一象限
        if (two.x < one.x && two.y > one.y) {
            Position middle = new Position(one.x, two.y);
            if (checkCanTwoPointsBeConnectedAlongY(one, middle) && checkCanTwoPointsBeConnectedAlongX(two, middle)) {
                for (int i = one.y - 1; i <= middle.y + 1; ++i) {
                    world[one.x - 1][i] = world[one.x + 1][i] = Tileset.WALL;
                    world[one.x][i] = Tileset.FLOOR;
                }
                for (int i = two.x - 1; i <= middle.x; ++i) {
                    world[i][two.y - 1] = world[i][two.y + 1] = Tileset.WALL;
                    world[i][two.y] = Tileset.FLOOR;
                }
                world[one.x][two.y - 1] = Tileset.FLOOR;
                return true;
            }
            middle = new Position(two.x, one.y);
            if (checkCanTwoPointsBeConnectedAlongX(middle, one) && !checkCanTwoPointsBeConnectedAlongY(middle, two)) {
                for (int i = middle.x - 1; i <= one.x + 1; ++i) {
                    world[i][one.y - 1] = world[i][one.y + 1] = Tileset.WALL;
                    world[i][one.y] = Tileset.FLOOR;
                }
                for (int i = middle.y; i <= two.y + 1; ++i) {
                    world[middle.x + 1][i] = world[middle.x - 1][i] = Tileset.WALL;
                    world[middle.x][i] = Tileset.FLOOR;
                }
                world[two.x + 1][one.y] = Tileset.FLOOR;
                return true;
            }
            return false;
        }
        // 第二象限
        if (two.x < one.x && two.y < one.y) {
            Position middle = new Position(one.x, two.y);
            if (checkCanTwoPointsBeConnectedAlongY(middle, one) && checkCanTwoPointsBeConnectedAlongX(two, middle)) {
                for (int i = middle.y - 1; i <= one.y + 1; ++i) {
                    world[middle.x - 1][i] = world[middle.x + 1][i] = Tileset.WALL;
                    world[middle.x][i] = Tileset.FLOOR;
                }
                for (int i = two.x - 1; i <= middle.x; ++i) {
                    world[i][two.y - 1] = world[i][two.y + 1] = Tileset.WALL;
                    world[i][two.y] = Tileset.FLOOR;
                }
                world[one.x][two.y + 1] = Tileset.FLOOR;
                return true;
            }
            middle = new Position(two.x, one.y);
            if (checkCanTwoPointsBeConnectedAlongX(middle, one) && checkCanTwoPointsBeConnectedAlongY(two, middle)) {
                for (int i = middle.x - 1; i <= one.x + 1; ++i) {
                    world[i][middle.y - 1] = world[i][middle.y + 1] = Tileset.WALL;
                    world[i][middle.y] = Tileset.FLOOR;
                }
                for (int i = two.y - 1; i <= middle.y; ++i) {
                    world[two.x - 1][i] = world[two.x + 1][i] = Tileset.WALL;
                    world[two.x][i] = Tileset.FLOOR;
                }
                world[two.x + 1][one.y] = Tileset.FLOOR;
                return true;
            }
            return false;
        }
        // 第三象限
        if (two.x > one.x && two.y < one.y) {
            Position middle = new Position(two.x, one.y);
            if (checkCanTwoPointsBeConnectedAlongX(one, middle) && checkCanTwoPointsBeConnectedAlongY(two, middle)) {
                for (int i = one.x - 1; i < middle.x + 1; ++i) {
                    world[i][one.y - 1] = world[i][one.y + 1] = Tileset.WALL;
                    world[i][one.y] = Tileset.FLOOR;
                }
                for (int i = two.y - 1; i <= middle.y; ++i) {
                    world[two.x - 1][i] = world[two.x + 1][i] = Tileset.WALL;
                    world[two.x][i] = Tileset.FLOOR;
                }
                world[two.x - 1][one.y] = Tileset.FLOOR;
                return true;
            }
            middle = new Position(one.x, two.y);
            if (checkCanTwoPointsBeConnectedAlongY(middle, one) && checkCanTwoPointsBeConnectedAlongX(middle, two)) {
                for (int i = middle.y - 1; i <= one.y + 1; ++i) {
                    world[middle.x - 1][i] = world[middle.x + 1][i] = Tileset.WALL;
                    world[middle.x][i] = Tileset.FLOOR;
                }
                for (int i = middle.x; i <= two.x + 1; ++i) {
                    world[i][middle.y + 1] = world[i][middle.y - 1] = Tileset.WALL;
                    world[i][middle.y] = Tileset.FLOOR;
                }
                world[one.x][two.y + 1] = Tileset.FLOOR;
                return true;
            }
            return false;
        }
        // 第四象限
        if (two.x > one.x && two.y > one.y) {
            Position middle = new Position(two.x, one.y);
            if (checkCanTwoPointsBeConnectedAlongX(one, middle) && checkCanTwoPointsBeConnectedAlongY(middle, two)) {
                for (int i = one.x - 1; i <= middle.x + 1; ++i) {
                    world[i][one.y - 1] = world[i][one.y + 1] = Tileset.WALL;
                    world[i][one.y] = Tileset.FLOOR;
                }
                for (int i = middle.y; i <= two.y + 1; ++i) {
                    world[middle.x - 1][i] = world[middle.x + 1][i] = Tileset.WALL;
                    world[middle.x][i] = Tileset.FLOOR;
                }
                world[two.x - 1][one.y] = Tileset.FLOOR;
                return true;
            }
            middle = new Position(one.x, two.y);
            if (checkCanTwoPointsBeConnectedAlongY(one, middle) && checkCanTwoPointsBeConnectedAlongX(middle, two)) {
                for (int i = one.y - 1; i <= middle.y + 1; ++i) {
                    world[one.x - 1][i] = world[one.x + 1][i] = Tileset.WALL;
                    world[one.x][i] = Tileset.FLOOR;
                }
                for (int i = middle.x; i <= two.x + 1; ++i) {
                    world[i][two.y - 1] = world[i][two.y + 1] = Tileset.WALL;
                    world[i][two.y] = Tileset.FLOOR;
                }
                world[one.x][two.y - 1] = Tileset.FLOOR;
                return true;
            }
            return false;
        }
        return false;
    }


    private boolean checkCanTwoPointsBeConnectedAlongX(Position one, Position two) { // assert one.x < two.x; one.y = two.y;
        if (one.x >= two.x) {
            return false;
        }
        if (one.y - 1 < 0 || one.y + 1 >= HEIGHT) {
            return false;
        }
        if (one.x - 1 < 0 || two.x + 1 >= WIDTH) {
            return false;
        }
        for (int i = one.x - 1; i <= two.x + 1; ++i) {
            for (int j = one.y - 1; j <= one.y + 1; ++j) {
                if (Tileset.FLOOR.equals(world[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkCanTwoPointsBeConnectedAlongY(Position one, Position two) { // assert one.y < two.y; one.x = two.x;
        if (one.y >= two.y) {
            return false;
        }
        if (one.x - 1 < 0 || one.x + 1 >= WIDTH) {
            return false;
        }
        if (one.y - 1 < 0 || two.y + 1 >= HEIGHT) {
            return false;
        }
        for (int i = one.x - 1; i <= one.x + 1; ++i) {
            for (int j = one.y - 1; j <= two.y + 1; ++j) {
                if (Tileset.FLOOR.equals(world[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}



























