package drawing;

import enums.ShadersAvailable;
import input.Window;
import maths.Vector2f;
import maths.Vector3f;
import org.lwjgl.opengl.*;

public class Renderer
{
	private Window window;
	private ShaderCollection shaders;

	public Renderer(Window window, ShaderCollection shaders)
	{
		this.window = window;
		this.shaders = shaders;
	}

	public void renderObjectMandelbrot(Mesh mesh, ShadersAvailable shader, int swFractal, int nIter, float power, Vector2f location, float zoom)
	{
		Shader shaderUsed;

		shaderUsed = shaders.getShader(shader);
		GL30.glBindVertexArray(mesh.getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
		shaderUsed.bind();
		shaderUsed.setUniform("swFractal", swFractal);
		shaderUsed.setUniform("nIter", nIter);
		shaderUsed.setUniform("power", power);
		shaderUsed.setUniform("location", location);
		shaderUsed.setUniform("zoom", zoom);
		GL11.glDrawElements(GL11.GL_POINTS, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shaderUsed.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}