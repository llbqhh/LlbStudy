package org.llbqhh.study.design.pattern.decorator;

public abstract class ShapeDecorator implements Shape {
    Shape decoratedShape;
    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }
    @Override
    public void draw() {
        decoratedShape.draw();
    }
}
