package urfu.db.lab02.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shapes")
@Getter
@Setter
public class Square extends Shape {

    private double side;

    public Square() {
        super(null, null, "square");
    }

    public Square(String color, String name, double side) {
        super(color, name, "square");
        this.side = side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getPerimeter() {
        return 4 * side;
    }
}