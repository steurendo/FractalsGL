package input;

import maths.Vector2f;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Input
{
    private static boolean[] keys = new boolean[65536];
    private static boolean[] keyTriggers = new boolean[65536];
    private static boolean[] buttonDown = new boolean[3];
    private static double amountHorizontal;
    private static double amountVertical;
    private static boolean entered;
    private static double x;
    private static double y;

    //CALLBACKS
    private GLFWKeyCallback keyboardCallback;
    private GLFWMouseButtonCallback mouseButtonsCallback;
    private GLFWScrollCallback scrollCallback;
    private GLFWCursorEnterCallback cursorEnterCallback;
    private GLFWCursorPosCallback cursorPosCallback;

    public Input()
    {
        keyboardCallback = new GLFWKeyCallback()
        {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods)
            {
                if (key >= 0)
                {
                    keys[key] = action != GLFW_RELEASE;
                    keyTriggers[key] = keys[key];
                }
            }
        };
        mouseButtonsCallback = new GLFWMouseButtonCallback()
        {
            @Override
            public void invoke(long window, int button, int action, int mods)
            {
                buttonDown[button] = action == 1;
            }
        };
        scrollCallback = new GLFWScrollCallback()
        {
            public void invoke(long window, double dx, double dy)
            {
                amountHorizontal = dx;
                amountVertical = dy;
            }
        };
        cursorEnterCallback = new GLFWCursorEnterCallback()
        {
            @Override
            public void invoke(long window, boolean entered)
            {
                Input.entered = entered;
            }
        };
        cursorPosCallback = new GLFWCursorPosCallback()
        {
            @Override
            public void invoke(long window, double x, double y)
            {
                Input.x = x;
                Input.y = y;
            }
        };
    }

    public void destroy()
    {
        keyboardCallback.free();
        mouseButtonsCallback.free();
        scrollCallback.free();
        cursorEnterCallback.free();
        cursorPosCallback.free();
    }

    //RETURNS
    public GLFWKeyCallback getKeyboardCallback() { return keyboardCallback; }
    public GLFWMouseButtonCallback getMouseButtonsCallback() { return mouseButtonsCallback; }
    public GLFWScrollCallback getScrollCallback() { return scrollCallback; }
    public GLFWCursorEnterCallback getCursorEnterCallback() { return cursorEnterCallback; }
    public GLFWCursorPosCallback getCursorPosCallback() { return cursorPosCallback; }

    //KEYBOARD
    public static boolean isKeyDown(int keycode) { return keys[keycode]; }
    public static boolean consumeKeyTrigger(int keycode)
    {
        boolean result;

        result = keyTriggers[keycode];
        if (result)
            keyTriggers[keycode] = false;

        return result;
    }
    //MOUSE BUTTONS
    public static boolean isMouseLeftButtonDown() { return buttonDown[0]; }
    public static boolean isMouseRightButtonDown() { return buttonDown[1]; }
    public static boolean isMouseMidButtonDown() { return buttonDown[2]; }
    //MOUSE SCROLL
    public static double getScrollAmountHorizontal()
    {
        double amount;

        amount = Input.amountHorizontal;
        Input.amountHorizontal = 0;

        return amount;
    }
    public static double getScrollAmountVertical()
    {
        double amount;

        amount = Input.amountVertical;
        Input.amountVertical = 0;

        return amount;
    }
    //CURSOR ENTER
    public static boolean isCursorEntered() { return entered; }
    //CURSOR POSITION
    public static double getCursorX() { return x; }
    public static double getCursorY() { return y; }
    public static Vector2f getCursorPos() { return new Vector2f((float)x, (float)y); }
}