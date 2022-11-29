package input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;

import java.nio.*;

import core.Canvas;
import core.Constants;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

public class Window
{
	private long id;
	private final String title;
	private long time;
	private long frames;
	private Input input;
	private boolean resized;
	
	public Window(int width, int height, String title)
	{
		core.Canvas.w = width;
		core.Canvas.h = height;
		this.title = title;
	}
	
	public void create() throws Exception
	{
		//GLFW INITIALIZATION
		if (!glfwInit())
			throw new Exception("Unable to start GLFW");

		//WINDOW INITIALIZATION
		core.Canvas.w = Constants.WND_W;
		core.Canvas.h = Constants.WND_H;
		id = GLFW.glfwCreateWindow(core.Canvas.w, core.Canvas.h, title, 0, 0);
		if (id == 0)
			throw new Exception("Unable to create window");
		//COMMANDS
		input = new Input();
		glfwSetKeyCallback(id, input.getKeyboardCallback());
		glfwSetMouseButtonCallback(id, input.getMouseButtonsCallback());
		glfwSetScrollCallback(id, input.getScrollCallback());
		glfwSetCursorEnterCallback(id, input.getCursorEnterCallback());
		glfwSetCursorPosCallback(id, input.getCursorPosCallback());
		//RESIZE CALLBACK
		glfwSetFramebufferSizeCallback(id, (window, width, height) ->
		{
			Canvas.w = width;
			Canvas.h = height;
			glViewport(0, 0, width, height);
			resized = true;
		});
		//CENTERED WINDOW
		try (MemoryStack stack = stackPush())
		{
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(id, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(
				id,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		glfwMakeContextCurrent(id);
		GL.createCapabilities(); //OPENGL-GLFW INTERACTION
		glEnable(GL_BLEND);
		glfwSwapInterval(1); //V-SYNC
		glfwShowWindow(id);
		time = System.currentTimeMillis();
		frames = 0;
	}
	public void update()
	{
		glClearColor(0, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwPollEvents();
		frames++;
		if (System.currentTimeMillis() > time + 1000)
		{
			glfwSetWindowTitle(id, title + " | FPS: " + frames);
			time = System.currentTimeMillis();
			frames = 0;
		}
	}
	public boolean getResizedStatus()
	{
		if (resized)
		{
			resized = false;

			return true;
		}

		return false;
	}
	public void swapBuffers() { glfwSwapBuffers(id); }
	public boolean shouldClose() { return glfwWindowShouldClose(id); }
	public void destroy()
	{
		input.destroy();
		glfwDestroyWindow(id);
		glfwTerminate();
	}
}