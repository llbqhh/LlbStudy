package org.llbqhh.study.design.pattern.decorator;

public class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }
    @Override
    public void draw() {
        decoratedShape.draw();
        drawRedBolder();
    }
    private void drawRedBolder() {
        System.out.println("red bolder...");
    }
}
