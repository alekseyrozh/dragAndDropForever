package com.roomfurniture.problem;

import com.badlogic.gdx.math.Vector2;

public class Vertex {
    public double x, y;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vertex(Vector2 vector) {
        x = vector.x;
        y = vector.y;
    }
    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    public Vector2 toVector2() {
        return new Vector2((float) x, (float) y);
    }

    public Vertex copy() {
        return new Vertex(x, y);
    }

    public void set(Vertex vertex) {
        x = vertex.x;
        y = vertex.y;
    }

    public static boolean isCollinear(Vertex p1,Vertex p2,Vertex p3) {
        return Math.abs((p1.y-p2.y)*(p1.x-p3.x) - (p1.y-p3.y)*(p1.x-p2.x)) < 0.0000001;
    }
}
