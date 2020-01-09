package org.llbqhh.study.design.pattern.facade;

import org.llbqhh.study.design.pattern.decorator.Circle;
import org.llbqhh.study.design.pattern.decorator.Rectangle;

public class ShapeMaker {
    Circle circle;
    Rectangle rectangle;
    public ShapeMaker() {
        this.circle = new Circle();
        this.rectangle = new Rectangle();
    }

    public void circleDraw() {
        this.circle.draw();
    }

    public void rectangleDraw() {
        this.rectangle.draw();
    }
}
