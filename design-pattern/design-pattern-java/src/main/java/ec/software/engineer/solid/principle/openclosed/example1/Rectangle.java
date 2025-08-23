package ec.software.engineer.solid.principle.openclosed.example1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Rectangle implements Shape {

	private double height;
	private double wight;

	public double area() {

		return getHeight() * getWight();
	}

}
