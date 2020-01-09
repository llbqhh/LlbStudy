package org.llbqhh.study.design.pattern.decorator;

/**
 * 装饰器模式（Decorator Pattern）允许向一个现有的对象添加新的功能，同时又不改变其结构。这种类型的设计模式属于结构型模式，它是作为现有的类的一个包装。
 */
public class DecoratorMain {
    public static void main(String[] args) {
        Circle circle = new Circle();
        ShapeDecorator redCircle = new RedShapeDecorator(circle);
        ShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());

        System.out.println("normal circle:");
        circle.draw();

        System.out.println("\r\nred circle:");
        redCircle.draw();

        System.out.println("\r\nred rectangle");
        redRectangle.draw();
    }
}
