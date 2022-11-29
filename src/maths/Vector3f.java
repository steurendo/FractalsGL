package maths;

public class Vector3f
{
	public float x;
	public float y;
	public float z;
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3f() { this(0, 0, 0); }

	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }

	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float z) { this.z = z; }
	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float mod() { return MathF.sqrt(MathF.pow(x, 2) + MathF.pow(y, 2) + MathF.pow(z, 2)); }
	public float distanceFrom(Vector3f vector) { return MathF.sqrt(
				 MathF.pow(x - vector.x, 2) +
					MathF.pow(y - vector.y, 2) +
					MathF.pow(z - vector.z, 2)
	); }
	public void add(Vector3f vector)
	{
		x += vector.x;
		y += vector.y;
		z += vector.z;
	}
	public void subtract(Vector3f vector)
	{
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
	}
	public void multiply(Vector3f vector)
	{
		x *= vector.x;
		y *= vector.y;
		z *= vector.z;
	}
	public void divide(Vector3f vector)
	{
		x /= vector.x;
		y /= vector.y;
		z /= vector.z;
	}
	public void scale(float value)
	{
		x *= value;
		y *= value;
		z *= value;
	}
	public void normalize() { scale(1f / mod()); }
	public float dot(Vector3f vector) { return x * vector.x + y * vector.y + z * vector.z; }
	public Vector3f clone() { return new Vector3f(x, y, z); }
	public String toString()
	{
		return " [" + x + "; " + y + "; " + z +  "]";
	}

	//STATICS
	public static float mod(Vector3f vector)
	{ return MathF.sqrt(MathF.pow(vector.x, 2) + MathF.pow(vector.y, 2) + MathF.pow(vector.z, 2)); }
	public static Vector3f add(Vector3f v1, Vector3f v2)
	{ return new Vector3f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z); }
	public static Vector3f subtract(Vector3f v1, Vector3f v2)
	{ return new Vector3f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z); }
	public static Vector3f multiply(Vector3f v1, Vector3f v2)
	{ return new Vector3f(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z); }
	public static Vector3f divide(Vector3f v1, Vector3f v2)
	{ return new Vector3f(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z); }
	public static Vector3f scale(Vector3f vector, float value)
	{ return new Vector3f(vector.x * value, vector.y * value, vector.z * value); }
	public Vector3f normalize(Vector3f vector)
	{ return scale(vector, 1f / vector.mod()); }
	public float dot(Vector3f v1, Vector3f v2)
	{ return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z; }
}