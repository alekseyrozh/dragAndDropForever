package com.roomfurniture.problem;

import java.util.List;
import java.util.Optional;

public class Problem {
   public final Room room;
    private final List<Shape> shapes;

    public Problem(Room room, List<Shape> shapes) {
        this.room = room;
        this.shapes = shapes;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "room=" + room +
                ", shapes=" + shapes +
                '}';
    }

    public Optional<Double> score(Solution solution) {
        return Optional.empty();
    }


}
