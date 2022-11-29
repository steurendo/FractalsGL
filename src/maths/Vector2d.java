package maths;

public class Vector2d
{
	public double x;
	public double y;

	public Vector2d(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public Vector2d() { this(0, 0); }

	public double getX() { return x; }
	public double getY() { return y; }

	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void set(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double mod() { return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); }
	public void add(Vector2d vector)
	{
		x += vector.x;
		y += vector.y;
	}
	public void subtract(Vector2d vector)
	{
		x -= vector.x;
		y -= vector.y;
	}
	public void multiply(Vector2d vector)
	{
		x *= vector.x;
		y *= vector.y;
	}
	public void divide(Vector2d vector)
	{
		x /= vector.x;
		y /= vector.y;
	}
	public void scale(double value)
	{
		x *= value;
		y *= value;
	}
	public void normalize() { scale(1f / mod()); }
	public double dot(Vector2d vector) { return x * vector.x + y * vector.y; }
	public Vector2d clone() { return new Vector2d(x, y); }
	public String toString()
	{
		return " [" + x + "; " + y +  "]";
	}

	//STATICS
	public static double mod(Vector2d vector)
	{ return Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2)); }
	public static Vector2d add(Vector2d v1, Vector2d v2)
	{ return new Vector2d(v1.x + v2.x, v1.y + v2.y); }
	public static Vector2d subtract(Vector2d v1, Vector2d v2)
	{ return new Vector2d(v1.x - v2.x, v1.y - v2.y); }
	public static Vector2d multiply(Vector2d v1, Vector2d v2)
	{ return new Vector2d(v1.x * v2.x, v1.y * v2.y); }
	public static Vector2d divide(Vector2d v1, Vector2d v2)
	{ return new Vector2d(v1.x / v2.x, v1.y / v2.y); }
	public static Vector2d scale(Vector2d vector, double value)
	{ return new Vector2d(vector.x * value, vector.y * value); }
	public Vector2d normalize(Vector2d vector)
	{ return scale(vector, 1f / vector.mod()); }
	public double dot(Vector2d v1, Vector2d v2)
	{ return v1.x * v2.x + v1.y * v2.y; }
}