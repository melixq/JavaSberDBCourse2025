package urfu.db.lab02;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import urfu.db.lab02.entity.Circle;
import urfu.db.lab02.entity.Rectangle;
import urfu.db.lab02.entity.Square;
import urfu.db.lab02.repository.ShapeRepository;

import java.util.List;

@SpringBootApplication
public class Lab02Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab02Application.class, args);
	}

	@Bean
	public CommandLineRunner lab2Demo(ShapeRepository repository) {
		return (args) -> {
			repository.deleteAll();

			System.out.println("=== СОЗДАНИЕ ТЕСТОВЫХ ДАННЫХ ===");

			// Создаем различные фигуры
			var circle3 = new Circle("Green", "Маленький зеленый кругляш", 3.0);
			var circle2 = new Circle("Blue", "Средний синий круг", 8.0);
			var circle1 = new Circle("Red", "Большой красный круг", 15.0);

			var rect1 = new Rectangle("Red", "Широкий красный прямоугольник", 20.0, 10.0);
			var rect2 = new Rectangle("Blue", "Прямоугольник синий с равными сторонами", 8.0, 8.0);
			var rect3 = new Rectangle("Yellow", "Высокий желтый прямоугольник", 5.0, 15.0);

			var square1 = new Square("Red", "Маленький красный квадрат", 4.0);
			var square2 = new Square("Blue", "Большой синий квадрат", 25.0);
			var square3 = new Square("Green", "Средний зеленый квадрат", 10.0);

			repository.saveAll(
					List.of(
							circle1, circle2, circle3,
							rect1, rect2, rect3,
							square1, square2, square3
			));

			System.out.println("Создано " + repository.count() + " фигур\n");
			System.out.println("=== Тестирование запросов ===");

			// Большие красные круги
			System.out.println("\n1. Большие красные круги (радиус > 10):");
			repository.findBigRedCircles(10.0).forEach(shape ->
					System.out.println("   - " + shape.getName())
			);

			// Поиск по части имени
			System.out.println("\n2. Фигуры, содержащие 'кругляш' в имени:");
			repository.findByNameContainingIgnoreCase("кругляш").forEach(shape ->
					System.out.println("   - " + shape.getName() + " (" + shape.getType() + ")")
			);

			// Прямоугольники и квадраты большего размера
			System.out.println("\n3. Прямоугольники и квадраты со стороной > 7:");
			repository.findRectanglesAndSquaresWithSizeGreaterThan(7.0).forEach(shape ->
					System.out.println("   - " + shape.getName())
			);

			// Все НЕ красные фигуры
			System.out.println("\n4. Все фигуры, не красного цвета:");
			repository.findByColorNot("Red").forEach(shape ->
					System.out.println("   - " + shape.getName() + " (" + shape.getColor() + ")")
			);

			// Фигуры определенных цветов
			System.out.println("\n6. Круги и квадраты красного или синего цвета:");
			repository.findByTypeInAndColorIn(
					List.of("circle", "square"),
					List.of("Red", "Blue")
			).forEach(shape ->
					System.out.println("   - " + shape.getName() + " (" +
							shape.getType() + ", " + shape.getColor() + ")")
			);

			// Поиск по периметру
			System.out.println("\n9. Фигуры с условиями для периметра:");
			System.out.println("\nОкружности:");
			repository.findCirclesByPerimeterGreaterThan(10).forEach(shape ->
					System.out.println("   - " + shape.getName() + ": периметр ≈ " +
							String.format("%.2f", shape.getPerimeter()))
			);
			System.out.println("\nКвадраты:");
			repository.findSquaresByPerimeterGreaterThan(50).forEach(shape ->
					System.out.println("   - " + shape.getName() + ": периметр ≈ " +
							String.format("%.2f", shape.getPerimeter()))
			);
			System.out.println("\nПрямоугольники:");
			repository.findRectanglesByPerimeterGreaterThan(5).forEach(shape ->
					System.out.println("   - " + shape.getName() + ": периметр ≈ " +
							String.format("%.2f", shape.getPerimeter()))
			);

			System.out.println("\n\n=== УДАЛЕНИЕ ===");
			System.out.println("\nВсе красные фигуры:");
			repository.findByColor("Red").forEach(shape ->
					System.out.println("   - " + shape.getName())
			);

			System.out.println("\nУдалим все красные окружности и прямоугольники...");
			repository.deleteByTypeAndColor("circle", "Red");
			repository.deleteByTypeAndColor("rectangle", "Red");

			System.out.println("Все красные фигуры снова:");
			repository.findByColor("Red").forEach(shape ->
					System.out.println("   - " + shape.getName())
			);

			System.out.println("\n=== ИЗМЕНЕНИЕ ===");
			System.out.println("\nУвеличим радиус для всех окружностей на 15 ед...");
			repository.incrementRadiusForAllCircles(15);

			System.out.println("Периметры окружностей после изменений:");
			repository.findCirclesByPerimeterGreaterThan(10).forEach(shape ->
					System.out.println("   - " + shape.getName() + ": периметр ≈ " +
							String.format("%.2f", shape.getPerimeter()))
			);
		};
	}
}
