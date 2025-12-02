package urfu.db.lab02.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shapes")
@Getter
@Setter
public class Circle extends Shape {

    private double radius;

    public Circle() {
        super(null, null, "circle");
    }

    public Circle(String color, String name, double radius) {
        super(color, name, "circle");
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}
