package drawing;

import input.FileUtils;
import maths.Vector2f;
import maths.Vector3f;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL20.*;

public class Shader
{
	private int programID;
	private int vertexID;
	private int fragmentID;
	private String filenameVertex;
	private String filenameFragment;

	public Shader(String filename)
	{
		filenameVertex = FileUtils.readFullResource("/shaders/" + filename + ".vs");
		filenameFragment = FileUtils.readFullResource("/shaders/" + filename + ".fs");
	}
	public void create()
	{
		programID = glCreateProgram();

		vertexID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexID, filenameVertex);
		glCompileShader(vertexID);
		if (glGetShaderi(vertexID, GL_COMPILE_STATUS) != 1)
		{
			System.err.println(glGetShaderInfoLog(vertexID));
			System.exit(1);
		}

		fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentID, filenameFragment);
		glCompileShader(fragmentID);
		if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) != 1)
		{
			System.err.println(glGetShaderInfoLog(vertexID));
			System.exit(1);
		}

		glAttachShader(programID, vertexID);
		glAttachShader(programID, fragmentID);

		glLinkProgram(programID);
		if (glGetProgrami(programID, GL_LINK_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(programID));
			System.exit(1);
		}
		glValidateProgram(programID);
		if (glGetProgrami(programID, GL_VALIDATE_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(programID));
			System.exit(1);
		}
	}
	public void bind() { glUseProgram(programID); }
	public void unbind() { glUseProgram(0); }
	public void destroy()
	{
		glDetachShader(programID, vertexID);
		glDetachShader(programID, fragmentID);
		glDeleteShader(vertexID);
		glDeleteShader(fragmentID);
		glDeleteProgram(programID);
	}

	public int getUniformLocation(String name) { return GL20.glGetUniformLocation(programID, name); }
	public void setUniform(String name, float value) { GL20.glUniform1f(getUniformLocation(name), value); }
	public void setUniform(String name, int value) { GL20.glUniform1i(getUniformLocation(name), value); }
	public void setUniform(String name, boolean value) { setUniform(name, value ? 1 : 0); }
	public void setUniform(String name, Vector2f value) { GL20.glUniform2f(getUniformLocation(name),
			value.x,
			value.y); }
	public void setUniform(String name, Vector3f value) { GL20.glUniform3f(getUniformLocation(name),
			value.x,
			value.y,
			value.z); }
}