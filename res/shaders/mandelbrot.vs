#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;

out vec3 passColor;

uniform int swFractal;
uniform int nIter;
uniform float power;
uniform vec2 location;
uniform float zoom;

#define PI 3.1415926538
#define LIM 4

vec3 rgb2hsb(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz),
                vec4(c.gb, K.xy),
                step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r),
                vec4(c.r, p.yzx),
                step(p.x, c.r));
    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)),
                d / (q.x + e),
                q.x);
}
vec3 hsb2rgb(vec3 c)
{
    vec3 rgb = clamp(abs(mod(c.x*6.0+vec3(0.0,4.0,2.0),
                    6.0)-3.0)-1.0,
                    0.0,
                    1.0 );
    rgb = rgb*rgb*(3.0-2.0*rgb);
    return c.z * mix(vec3(1.0), rgb, c.y);
}

vec2 complex_scale(vec2 z, float factor) { return vec2(factor * z.x, factor * z.y); }
vec2 complex_add(vec2 z, vec2 w) { return vec2(z.x + w.x, z.y + w.y); }
vec2 complex_multiply(vec2 z, vec2 w) { return vec2(z.x * w.x - z.y * w.y, z.y * w.x + z.x * w.y); }
vec2 complex_divide(vec2 z, vec2 w)
{
    return vec2((z.x * w.x + z.y * w.y) / (w.x * w.x + w.y * w.y), (z.y * w.x - z.x * w.y) / (w.x * w.x + w.y * w.y));
}
vec2 complex_pow(vec2 z, float e)
{
    float rh, th;

    rh = sqrt(z.x * z.x + z.y * z.y);
    th = atan(z.y / z.x);
    if (z.x < 0)
        th = th + PI;

    return vec2(pow(rh, e) * cos(e * th), pow(rh, e) * sin(e * th));
}
vec2 complex_pow2(vec2 z, float e)
{
    float rh, th;

    rh = sqrt(z.x * z.x + z.y * z.y);
    th = atan(z.y / z.x);

    return vec2(pow(rh, e) * cos(e * th), pow(rh, e) * sin(e * th));
}
vec2 complex_sqr(vec2 z, float e)
{
    float rh, th, t;

    rh = sqrt(z.x * z.x + z.y * z.y);
    th = atan(z.y / z.x);
    t = 1;
    rh = pow(rh, t / e);
    th = th / e;
    if (z.x < 0)
        th = th + PI;

    return vec2(pow(rh, e) * cos(e * th), pow(rh, e) * sin(e * th));
}
vec2 complex_sqr2(vec2 z, float e)
{
    float rh, th, t;

    rh = sqrt(z.x * z.x + z.y * z.y);
    th = atan(z.y / z.x);
    t = 1;
    rh = pow(rh, t / e);
    th = th / e;

    return vec2(pow(rh, e) * cos(e * th), pow(rh, e) * sin(e * th));
}
vec2 complex_exp(vec2 z)
{
    return vec2(exp(z.x) * cos(z.y), exp(z.x) * sin(z.y));
}
vec2 complex_cos(vec2 z)
{
    return vec2(0.5 * cos(z.x) * (exp(z.y) + exp(-z.y)),
                0.5 * sin(z.x) * (exp(-z.y) - exp(z.y)));
}
vec2 complex_sin(vec2 z)
{
    return vec2(0.5 * sin(z.x) * (exp(z.y) + exp(-z.y)),
                0.5 * cos(z.x) * (exp(z.y) - exp(-z.y)));
}
vec2 complex_cosh(vec2 z)
{
    return vec2(0.5 * cos(z.y) * (exp(z.x) + exp(-z.x)),
                0.5 * sin(z.y) * (exp(z.x) - exp(-z.x)));
}
vec2 complex_sinh(vec2 z)
{
    return vec2(0.5 * cos(z.y) * (exp(z.x) - exp(-z.x)),
                0.5 * sin(z.y) * (exp(z.x) + exp(-z.x)));
}

float fractal_default(vec3 position, float power)
{
	vec2 z, c;
	int i;

	c = vec2(2 * (zoom * position.x + location.x), 2 * (zoom * position.y + location.y));
	z = vec2(0, 0);
	for (i = 0; i < nIter; i++)
	{
	    //z = vec2(z.x * z.x - z.y * z.y, 2 * z.x * z.y);
	    z = complex_pow(z, power);
	    z.x = z.x + c.x;
	    z.y = z.y + c.y;
	    if (z.x * z.x + z.y * z.y > LIM) break;
	}

	return i;
}
float fractal_leaf(vec3 position, float power)
{
	vec2 z, c;
	float x, y, u, v;
	int i;

	c = vec2(2 * (zoom * position.x + location.x), 2 * (zoom * position.y + location.y));
	z = vec2(0, 0);
	u = c.x / (c.x * c.x + c.y * c.y);
	v = c.y / (c.x * c.x + c.y * c.y);
	for (i = 0; i < nIter; i++)
	{
	    z = complex_pow(z, power);
	    z.x = z.x + u;
	    z.y = z.y - v;
	    if (z.x * z.x + z.y * z.y > LIM) break;
	}

	return i;
}
float fractal_cactus(vec3 position)
{
	vec2 z, c;
	float x, y;
	int i;

	c = vec2(2 * (zoom * position.x + location.x), 2 * (zoom * position.y + location.y));
	z = vec2(0, 0);
	for (i = 0; i < nIter; i++)
	{
	    z = complex_add(complex_pow(z, 2), complex_pow(c, 6));
	    z.x = z.x - 1;
	    if (z.x * z.x + z.y * z.y > LIM) break;
	}

	return i;
}
float fractal_moonfish(vec3 position)
{
	vec2 z, c;
	float x, y, u, v;
	int i;

	c = vec2(2 * (zoom * position.x + location.x), 2 * (zoom * position.y + location.y));
	z = vec2(0, 0);
	u = c.x / (c.x * c.x + c.y * c.y);
	v = c.y / (c.x * c.x + c.y * c.y);
	for (i = 0; i < nIter; i++)
	{
	    z = complex_cos(z);
	    z.x = z.x + u;
	    z.y = z.y - v;
	    if (z.x * z.x + z.y * z.y > LIM) break;
	}

	return i;
}
float fractal_windmill(vec3 position)
{
	vec2 z, c;
	float x, y;
	int i;

	c = vec2(2 * (zoom * position.x + location.x), 2 * (zoom * position.y + location.y));
	z = vec2(1, 1);
	for (i = 0; i < nIter; i++)
	{
	    z = complex_exp(complex_multiply(complex_add(complex_pow(z, 2), z), complex_sqr(complex_pow(c, -3), 2)));
	    if (z.x * z.x + z.y * z.y > LIM) break;
	}

	return i;
}
float fractal_arrows(vec3 position)
{
	vec2 z, c;
	float x, y;
	int i;

	c = vec2(2 * (zoom * position.x + location.x), 2 * (zoom * position.y + location.y));
	z = vec2(0, 0);
	for (i = 0; i < nIter; i++)
	{
	    z = complex_exp(complex_multiply(complex_add(complex_scale(z, -1.00001), complex_pow(z, 2)), complex_sqr(complex_pow(c, -3), 2)));
	    if (z.x * z.x + z.y * z.y > LIM) break;
	}

	return i;
}
float fractal_ball(vec3 position)
{
	vec2 z, c;
	float x, y;
	int i;

	c = vec2(2 * (zoom * position.x + location.x), 2 * (zoom * position.y + location.y));
	z = vec2(0, 0);
	for (i = 0; i < nIter; i++)
	{
	    z = complex_exp(complex_multiply(complex_add(complex_pow(z, 2), complex_scale(z, -1.00001)), complex_pow(c, -3)));
	    if (z.x * z.x + z.y * z.y > LIM) break;
	}

	return i;
}

void main()
{
    float n;

	gl_Position = vec4(position, 1.0);
	n = nIter;
	switch (swFractal)
	{
	    case 0:
	        n = fractal_default(position, power);
	        break;
	    case 1:
	        n = fractal_leaf(position, power);
	        break;
	    case 2:
	        n = fractal_cactus(position);
	        break;
	    case 3:
	        n = fractal_moonfish(position);
	        break;
	    case 4:
	        n = fractal_windmill(position);
	        break;
	    case 5:
	        n = fractal_arrows(position);
	        break;
	    case 6:
	        n = fractal_ball(position);
	        break;
	}
	if (n < nIter)
	    passColor = hsb2rgb(vec3(n / nIter, 1, 1));
	else
	    passColor = vec3(0, 0, 0);
}