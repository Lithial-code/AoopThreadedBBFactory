package com.lithial.entities;

import com.lithial.pathfinding.Node;

import java.util.List;

//todo not sure i like this so maybe we'll tweak how this works shortly?
public interface IMoveable {
    void move(List<Node> path);
    //void moveTo(Vector2 pointToMoveTo);
}
