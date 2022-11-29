package core;

import drawing.*;
import input.Input;
import input.Window;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable
{
	private Thread application;
	private Window window;
	private ShaderCollection shaders;
	private Renderer renderer;
	private boolean active;

	private ScenarioMain scenarioMain;

	public void start()
	{
		application = new Thread(this, "Fractals!");
		application.start();
	}

	@Override
	public void run()
	{
		init();
		loop();
		close();
	}
	private void init()
	{
		active = true;
		//OPENGL
		window = new Window(Constants.WND_W, Constants.WND_H, Constants.WND_TITLE);
		try { shaders = new ShaderCollection(); } catch (Exception e) { e.printStackTrace(); }
		renderer = new Renderer(window, shaders);
		try { window.create(); } catch (Exception e) { e.printStackTrace(); }
		shaders.createAll();

		//OTHER
		scenarioMain = new ScenarioMain(renderer);
	}
	private void loop()
	{
		while (active)
		{
			window.update();
			scenarioMain.processCommands();
			scenarioMain.update();
			scenarioMain.render();
			window.swapBuffers();
			if (window.shouldClose() || Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
				active = false;
			if (window.getResizedStatus())
				scenarioMain.resize();
		}
	}
	private void close()
	{
		window.destroy();
		scenarioMain.destroy();
		shaders.destroyAll();
	}
	
	//MAIN
	public static void main(String[] args) { new Main().start(); }
}