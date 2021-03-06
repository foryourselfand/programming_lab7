package Input;

import java.io.Serializable;

public class Coordinates implements Serializable {
	private float x; //Значение поля должно быть больше -292
	private Double y; //Значение поля должно быть больше -747, Поле не может быть null
	
	public Coordinates() {
	}
	
	public Coordinates(float x, Double y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public Double getY() {
		return y;
	}
	
	public void setY(Double y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Coordinates{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
