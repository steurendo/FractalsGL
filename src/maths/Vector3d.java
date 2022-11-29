package maths;

public class Vector3d
{
	public double x;
	public double y;
	public double z;

	public Vector3d(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3d() { this(0, 0, 0); }

	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }

	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void setZ(double z) { this.z = z; }
	public void set(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double mod() { return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)); }
	public double distanceFrom(Vector3d vector) { return Math.sqrt(
				 Math.pow(x - vector.x, 2) +
					Math.pow(y - vector.y, 2) +
					Math.pow(z - vector.z, 2)
	); }
	public void add(Vector3d vector)
	{
		x += vector.x;
		y += vector.y;
		z += vector.z;
	}
	public void subtract(Vector3d vector)
	{
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
	}
	public void multiply(Vector3d vector)
	{
		x *= vector.x;
		y *= vector.y;
		z *= vector.z;
	}
	public void divide(Vector3d vector)
	{
		x /= vector.x;
		y /= vector.y;
		z /= vector.z;
	}
	public void scale(double value)
	{
		x *= value;
		y *= value;
		z *= value;
	}
	public void normalize() { scale(1f / mod()); }
	public double dot(Vector3d vector) { return x * vector.x + y * vector.y + z * vector.z; }
	public Vector3d clone() { return new Vector3d(x, y, z); }
	public String toString()
	{
		return " [" + x + "; " + y + "; " + z +  "]";
	}

	//STATICS
	public static double mod(Vector3d vector)
	{ return Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2) + Math.pow(vector.z, 2)); }
	public static Vector3d add(Vector3d v1, Vector3d v2)
	{ return new Vector3d(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z); }
	public static Vector3d subtract(Vector3d v1, Vector3d v2)
	{ return new Vector3d(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z); }
	public static Vector3d multiply(Vector3d v1, Vector3d v2)
	{ return new Vector3d(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z); }
	public static Vector3d divide(Vector3d v1, Vector3d v2)
	{ return new Vector3d(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z); }
	public static Vector3d scale(Vector3d vector, double value)
	{ return new Vector3d(vector.x * value, vector.y * value, vector.z * value); }
	public Vector3d normalize(Vector3d vector)
	{ return scale(vector, 1f / vector.mod()); }
	public double dot(Vector3d v1, Vector3d v2)
	{ return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z; }
}