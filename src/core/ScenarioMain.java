package core;

import drawing.Mesh;
import drawing.MeshMaker;
import drawing.Renderer;
import enums.ShadersAvailable;
import input.Input;
import maths.Vector2f;
import maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class ScenarioMain extends Scenario
{
    private static final int ITER_DEFAULT = 100;
    private static final float ZOOM_FACTOR = 0.9f;
    private Mesh image;
    private int swFractal;
    private int power;
    private Vector2f location;
    private float zoom;
    private Vector2f cursorPivot;
    private int nIter;

    public ScenarioMain(Renderer renderer)
    {
        super(renderer);

        swFractal = 0;
        power = 2;
        location = new Vector2f(0, 0);
        zoom = 1;
        nIter = ITER_DEFAULT;
        resize();
    }

    public void switchRight()
    {
        reinit();
        swFractal++;
    }
    public void switchLeft()
    {
        if (swFractal == 0)
            return;

        reinit();
        swFractal--;
    }
    public void resize()
    {
        image = MeshMaker.generateSquare(Canvas.w, Canvas.h);
        image.create();
    }
    public void reinit()
    {
        zoom = 1;
        location = new Vector2f(0, 0);
        cursorPivot = null;
    }

    @Override
    public void render()
    {
        renderer.renderObjectMandelbrot(image, ShadersAvailable.MAIN, swFractal, nIter, power, location, zoom);
    }
    @Override
    public void processCommands()
    {
        if (Input.consumeKeyTrigger(GLFW.GLFW_KEY_KP_ADD) && swFractal < 2)
            power++;
        if (Input.consumeKeyTrigger(GLFW.GLFW_KEY_KP_SUBTRACT) && swFractal < 2)
            power--;

        if (Input.consumeKeyTrigger(GLFW.GLFW_KEY_UP))
            nIter += ITER_DEFAULT / 10;
        if (Input.consumeKeyTrigger(GLFW.GLFW_KEY_DOWN))
            nIter -= ITER_DEFAULT / 10;
        if (Input.consumeKeyTrigger(GLFW.GLFW_KEY_RIGHT) &&
                Input.consumeKeyTrigger(GLFW.GLFW_KEY_LEFT_CONTROL) &&
                Input.consumeKeyTrigger(GLFW.GLFW_KEY_LEFT_SHIFT))
            switchRight();
        if (Input.consumeKeyTrigger(GLFW.GLFW_KEY_LEFT) &&
                Input.consumeKeyTrigger(GLFW.GLFW_KEY_LEFT_CONTROL) &&
                Input.consumeKeyTrigger(GLFW.GLFW_KEY_LEFT_SHIFT))
            switchLeft();

        if (Input.isMouseLeftButtonDown())
        {
            if (cursorPivot == null)
                cursorPivot = Input.getCursorPos();
            else
            {
                Vector2f cursorPos;

                cursorPos = Input.getCursorPos();
                location.x += zoom * (cursorPivot.x - cursorPos.x) * (2f / Canvas.w);
                location.y += zoom * (cursorPos.y - cursorPivot.y) * (2f / Canvas.h);
                cursorPivot = cursorPos;
            }
        }
        else
            cursorPivot = null;
        double scroll;

        scroll = Input.getScrollAmountVertical();
        if (scroll > 0)
            zoom *= ZOOM_FACTOR;
        if (scroll < 0)
            zoom /= ZOOM_FACTOR;
    }
    @Override
    public void update() { }
    @Override
    public void destroy() { image.destroy(); }
}
