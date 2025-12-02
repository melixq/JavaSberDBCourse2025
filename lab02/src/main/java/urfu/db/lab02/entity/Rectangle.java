package urfu.db.lab02.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shapes")
@Getter
@Setter
public class Rectangle extends Shape {

    private double width;
    private double height;

    public Rectangle() {
        super(null, null, "rectangle");
    }

    public Rectangle(String color, String name, double width, double height) {
        super(color, name, "rectangle");
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }
}
