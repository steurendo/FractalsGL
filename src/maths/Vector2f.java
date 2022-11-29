package maths;

public class Vector2f
{
	public float x;
	public float y;

	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	public Vector2f() { this(0, 0); }

	public float getX() { return x; }
	public float getY() { return y; }

	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float mod() { return MathF.sqrt(MathF.pow(x, 2) + MathF.pow(y, 2)); }
	public void add(Vector2f vector)
	{
		x += vector.x;
		y += vector.y;
	}
	public void subtract(Vector2f vector)
	{
		x -= vector.x;
		y -= vector.y;
	}
	public void multiply(Vector2f vector)
	{
		x *= vector.x;
		y *= vector.y;
	}
	public void divide(Vector2f vector)
	{
		x /= vector.x;
		y /= vector.y;
	}
	public void scale(float value)
	{
		x *= value;
		y *= value;
	}
	public void normalize() { scale(1f / mod()); }
	public float dot(Vector2f vector) { return x * vector.x + y * vector.y; }
	public Vector2f clone() { return new Vector2f(x, y); }
	public String toString()
	{
		return " [" + x + "; " + y +  "]";
	}

	//STATICS
	public static float mod(Vector2f vector)
	{ return MathF.sqrt(MathF.pow(vector.x, 2) + MathF.pow(vector.y, 2)); }
	public static Vector2f add(Vector2f v1, Vector2f v2)
	{ return new Vector2f(v1.x + v2.x, v1.y + v2.y); }
	public static Vector2f subtract(Vector2f v1, Vector2f v2)
	{ return new Vector2f(v1.x - v2.x, v1.y - v2.y); }
	public static Vector2f multiply(Vector2f v1, Vector2f v2)
	{ return new Vector2f(v1.x * v2.x, v1.y * v2.y); }
	public static Vector2f divide(Vector2f v1, Vector2f v2)
	{ return new Vector2f(v1.x / v2.x, v1.y / v2.y); }
	public static Vector2f scale(Vector2f vector, float value)
	{ return new Vector2f(vector.x * value, vector.y * value); }
	public Vector2f normalize(Vector2f vector)
	{ return scale(vector, 1f / vector.mod()); }
	public float dot(Vector2f v1, Vector2f v2)
	{ return v1.x * v2.x + v1.y * v2.y; }
}