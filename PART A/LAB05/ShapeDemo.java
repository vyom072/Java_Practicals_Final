abstract class Shape { abstract double area(); }
class Circle extends Shape {
    double radius;
    Circle(double radius) { this.radius = radius; }
    double area() { return Math.PI * radius * radius; }
}
class Rectangle extends Shape {
    double length, width;
    Rectangle(double length, double width) { this.length = length; this.width = width; }
    double area() { return length * width; }
}
class Triangle extends Shape {
    double base, height;
    Triangle(double base, double height) { this.base = base; this.height = height; }
    double area() { return base * height / 2; }
}
public class ShapeDemo {
    public static void main(String[] args) {
        Shape[] shapes = {new Circle(2), new Rectangle(4,5), new Triangle(3,6)};
        double total = 0, largest = -1;
        String name = "";
        for (Shape s : shapes) {
            double area = s.area(); total += area;
            System.out.println(s.getClass().getSimpleName() + ": " + area
                + " Running total: " + total);
            if (area > largest) { largest = area; name = s.getClass().getSimpleName(); }
        }
        System.out.println("Largest: " + name + " " + largest);
    }
}
