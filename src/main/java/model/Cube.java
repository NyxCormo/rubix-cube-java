package model;

import enums.Axis;
import enums.Color;
import enums.Direction;
import enums.Face;

import java.util.Map;

public class Cube {
    private final int size;
    Cubie[][][] cube;

    public Cube(int size){
        this.size = size;
        this.cube = new Cubie[size][size][size];
        initCube();
    }

    private void initCube(){
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                for(int z = 0; z < size; z++){
                    Cubie c = new Cubie();
                    if (x == 0) c.setFaceColor(Face.LEFT, Color.ORANGE);
                    if (x == (size - 1)) c.setFaceColor(Face.RIGHT, Color.RED);
                    if (y == 0) c.setFaceColor(Face.DOWN, Color.YELLOW);
                    if (y == (size - 1)) c.setFaceColor(Face.UP, Color.WHITE);
                    if (z == 0) c.setFaceColor(Face.BACK, Color.BLUE);
                    if (z == (size - 1)) c.setFaceColor(Face.FRONT, Color.GREEN);
                    cube[x][y][z] = c;
                }
            }
        }
    }

    public void rotateFace(Face face, Direction direction){
        switch (face){
            case UP:
                rotateLayer(Axis.Y, size - 1, direction);
                break;
            case DOWN:
                rotateLayer(Axis.Y, 0, invert(direction));
                break;
            case LEFT:
                rotateLayer(Axis.X, 0, direction);
                break;
            case RIGHT:
                rotateLayer(Axis.X, size - 1, direction);
                break;
            case FRONT:
                rotateLayer(Axis.Z, size - 1, direction);
                break;
            case BACK:
                rotateLayer(Axis.Z, 0, invert(direction));
                break;
        }
    }

    private Direction invert(Direction direction) {
        return direction == Direction.CLOCKWISE
                ? Direction.COUNTER_CLOCKWISE
                : Direction.CLOCKWISE;
    }

    public void rotateLayer(Axis axis, int index, Direction direction) {
        Cubie[][] temp = new Cubie[size][size];
        switch (axis) {
            case X:
                // Rotation autour de l'axe X (plan YZ)
                for (int y = 0; y < size; y++) {
                    for (int z = 0; z < size; z++) {
                        temp[y][z] = cube[index][y][z];
                        temp[y][z].rotate(axis, direction);
                    }
                }
                for (int y = 0; y < size; y++) {
                    for (int z = 0; z < size; z++) {
                        if (direction == Direction.CLOCKWISE) {
                            // (y, z) -> ((size - 1) - z, y)
                            cube[index][(size - 1) - z][y] = temp[y][z];
                        } else {
                            // (y, z) -> (z, (size - 1) - y)
                            cube[index][z][(size - 1) - y] = temp[y][z];
                        }
                    }
                }
                break;

            case Y:
                // Rotation autour de l'axe Y (plan XZ)
                for (int x = 0; x < size; x++) {
                    for (int z = 0; z < size; z++) {
                        temp[x][z] = cube[x][index][z];
                        temp[x][z].rotate(axis, direction);
                    }
                }
                for (int x = 0; x < size; x++) {
                    for (int z = 0; z < size; z++) {
                        if (direction == Direction.CLOCKWISE) {
                            // (x, z) -> (z, (size - 1) - x)
                            cube[z][index][(size - 1) - x] = temp[x][z];
                        } else {
                            // (x, z) -> ((size - 1) - z, x)
                            cube[(size - 1) - z][index][x] = temp[x][z];
                        }
                    }
                }
                break;

            case Z:
                // Rotation autour de l'axe Z (plan XY)
                for (int x = 0; x < size; x++) {
                    for (int y = 0; y < size; y++) {
                        temp[x][y] = cube[x][y][index];
                        temp[x][y].rotate(axis, direction);
                    }
                }
                for (int x = 0; x < size; x++) {
                    for (int y = 0; y < size; y++) {
                        if (direction == Direction.CLOCKWISE) {
                            // (x, y) -> ((size - 1) - y, x)
                            cube[(size - 1) - y][x][index] = temp[x][y];
                        } else {
                            // (x, y) -> (y, (size - 1) - x)
                            cube[y][(size - 1) - x][index] = temp[x][y];
                        }
                    }
                }
                break;
        }
    }

    public void display() {
        Map<Color, String> colorMap = Map.of(
                Color.WHITE, "\u001B[47m  ",
                Color.YELLOW, "\u001B[43m  ",
                Color.RED, "\u001B[41m  ",
                Color.ORANGE, "\u001B[48;5;208m  ",
                Color.BLUE, "\u001B[44m  ",
                Color.GREEN, "\u001B[42m  "
        );

        String reset = "\u001B[0m";

        Face[] faces = {Face.UP, Face.DOWN, Face.FRONT, Face.BACK, Face.LEFT, Face.RIGHT};

        for (Face f : faces) {
            System.out.println("\n" + f + " face:");

            for (int y = (size - 1); y >= 0; y--) {
                for (int x = 0; x < size; x++) {

                    Cubie c = switch (f) {
                        case UP -> cube[x][(size - 1)][(size - 1) - y];
                        case DOWN -> cube[x][0][y];
                        case FRONT -> cube[x][y][(size - 1)];
                        case BACK -> cube[(size - 1) - x][y][0];
                        case LEFT -> cube[0][y][(size - 1) - x];
                        case RIGHT -> cube[(size - 1)][y][x];
                    };

                    Color col = (c != null) ? c.getFaceColor(f) : null;

                    if (col != null) {
                        System.out.print(colorMap.get(col));
                    } else {
                        System.out.print("  ");
                    }

                    System.out.print(reset);
                }
                System.out.println();
            }
        }
    }
}
