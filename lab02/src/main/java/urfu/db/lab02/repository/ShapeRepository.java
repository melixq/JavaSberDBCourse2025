package urfu.db.lab02.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.transaction.annotation.Transactional;
import urfu.db.lab02.entity.Shape;

import java.util.List;

public interface ShapeRepository extends MongoRepository<Shape, String> {
    // Базовые мтоды поиска
    List<Shape> findByName(String name);
    List<Shape> findByColor(String color);
    List<Shape> findByType(String type);
    List<Shape> findByNameAndColor(String name, String color);

    void deleteByColor(String color);
    void deleteByTypeAndColor(String type, String color);

    @Transactional
    @Query("{ 'type' : 'circle' }")
    @Update("{ '$inc' : { 'radius' : ?0 } }")
    void incrementRadiusForAllCircles(double incrementValue);

    // Сложный запрос 1: Найти все Круги, красного цвета, с радиусом больше заданного
    @Query("{'type': 'circle', 'color': 'Red', 'radius': {$gt: ?0}}")
    List<Shape> findBigRedCircles(double minRadius);

    // Сложный запрос 2: Найти все фигуры, у которых имя содержит подстроку и отсортировать по убыванию периметра
    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<Shape> findByNameContainingIgnoreCase(String namePart);

    // Сложный запрос 3: Найти прямоугольники и квадраты с шириной/стороной больше минимального значения
    @Query("{$or: ["
            + "  {$and: [{'type': 'rectangle'}, {'width': {$gt: ?0}}]},"
            + "  {$and: [{'type': 'square'}, {'side': {$gt: ?0}}]}"
            + "]}")
    List<Shape> findRectanglesAndSquaresWithSizeGreaterThan(double minSize);

    // Сложный запрос 4: Найти все фигуры, кроме указанного цвета
    @Query("{'color': {$ne: ?0}}")
    List<Shape> findByColorNot(String excludedColor);

    // Сложный запрос 5: Найти все фигуры, определенных типов и цветов
    @Query("{'type': {$in: ?0}, 'color': {$in: ?1}}")
    List<Shape> findByTypeInAndColorIn(List<String> types, List<String> colors);

    // Сложный запрос 6: Найти фигуры с отсутствующими полями
    @Query("{$or: ["
            + "  {'color': {$exists: false}},"
            + "  {'color': null},"
            + "  {'name': {$exists: false}},"
            + "  {'name': null}"
            + "]}")
    List<Shape> findShapesWithMissingFields();

    // Сложный запрос 7: Найти фигуры с периметром больше заданного значения
    @Query("{'$expr': {'$gt': [{'$multiply': [2, 3.14159, '$radius']}, ?0]}, 'type': 'circle'}")
    List<Shape> findCirclesByPerimeterGreaterThan(double minPerimeter);

    @Query("{'$expr': {'$gt': [{'$add': [{'$multiply': [2, '$width']}, {'$multiply': [2, '$height']}]}, ?0]}, 'type': 'rectangle'}")
    List<Shape> findRectanglesByPerimeterGreaterThan(double minPerimeter);

    @Query("{'$expr': {'$gt': [{'$multiply': [4, '$side']}, ?0]}, 'type': 'square'}")
    List<Shape> findSquaresByPerimeterGreaterThan(double minPerimeter);
}
