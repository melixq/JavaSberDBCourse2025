package urfu.db.lab02.entity;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Я знаю какие типы у меня будут, поэтому
@Document(collection = "shapes")
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Circle.class, name = "circle"),
        @JsonSubTypes.Type(value = Rectangle.class, name = "rectangle"),
        @JsonSubTypes.Type(value = Square.class, name = "square")
})
public abstract class Shape {

    @Id
    private String id;
    private String color;
    private String name;

    // Поле для определения типа при полиморфной десериализации
    private String type;

    public Shape() {}

    public Shape(String color, String name, String type) {
        this.color = color;
        this.name = name;
        this.type = type;
    }

    public abstract double getArea();
    public abstract double getPerimeter();
}