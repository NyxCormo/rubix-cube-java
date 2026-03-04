package model;


import enums.Axis;
import enums.Color;
import enums.Direction;
import enums.Face;

import java.util.EnumMap;
import java.util.Map;

public class Cubie {
    private Map<Face, Color> faces;

    public Cubie() {
        this.faces = new EnumMap<>(Face.class);
    }

    public Cubie(Map<Face, Color> faces) {
        this.faces = faces;
    }

    public void setFaceColor(Face face, Color color){
        faces.put(face, color);
    }

    public Color getFaceColor(Face face) {
        return faces.get(face);
    }

    public void rotate(Axis axis, Direction direction) {
        Color up = faces.get(Face.UP);
        Color down = faces.get(Face.DOWN);
        Color front = faces.get(Face.FRONT);
        Color back = faces.get(Face.BACK);
        Color left = faces.get(Face.LEFT);
        Color right = faces.get(Face.RIGHT);

        switch (axis) {
            case X:
                if (direction == Direction.CLOCKWISE) {
                    // UP -> BACK, BACK -> DOWN, DOWN -> FRONT, FRONT -> UP
                    setFaceOrRemove(Face.UP, front);
                    setFaceOrRemove(Face.BACK, up);
                    setFaceOrRemove(Face.DOWN, back);
                    setFaceOrRemove(Face.FRONT, down);
                } else {
                    // UP -> FRONT, FRONT -> DOWN, DOWN -> BACK, BACK -> UP
                    setFaceOrRemove(Face.UP, back);
                    setFaceOrRemove(Face.FRONT, up);
                    setFaceOrRemove(Face.DOWN, front);
                    setFaceOrRemove(Face.BACK, down);
                }
                break;
            case Y:
                if (direction == Direction.CLOCKWISE) {
                    // FRONT -> RIGHT, RIGHT -> BACK, BACK -> LEFT, LEFT -> FRONT
                    setFaceOrRemove(Face.FRONT, left);
                    setFaceOrRemove(Face.RIGHT, front);
                    setFaceOrRemove(Face.BACK, right);
                    setFaceOrRemove(Face.LEFT, back);
                } else {
                    // FRONT -> LEFT, LEFT -> BACK, BACK -> RIGHT, RIGHT -> FRONT
                    setFaceOrRemove(Face.FRONT, right);
                    setFaceOrRemove(Face.LEFT, front);
                    setFaceOrRemove(Face.BACK, left);
                    setFaceOrRemove(Face.RIGHT, back);
                }
                break;
            case Z:
                if (direction == Direction.CLOCKWISE) {
                    // UP -> LEFT, LEFT -> DOWN, DOWN -> RIGHT, RIGHT -> UP
                    setFaceOrRemove(Face.UP, right);
                    setFaceOrRemove(Face.LEFT, up);
                    setFaceOrRemove(Face.DOWN, left);
                    setFaceOrRemove(Face.RIGHT, down);
                } else {
                    // UP -> RIGHT, RIGHT -> DOWN, DOWN -> LEFT, LEFT -> UP
                    setFaceOrRemove(Face.UP, left);
                    setFaceOrRemove(Face.RIGHT, up);
                    setFaceOrRemove(Face.DOWN, right);
                    setFaceOrRemove(Face.LEFT, down);
                }
                break;
        }
    }

    private void setFaceOrRemove(Face face, Color color) {
        if (color != null) {
            faces.put(face, color);
        } else {
            faces.remove(face);
        }
    }
}

