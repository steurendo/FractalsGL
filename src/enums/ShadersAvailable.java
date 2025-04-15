package enums;

public enum ShadersAvailable {
    MAIN("mandelbrot");

    private final String name;

    ShadersAvailable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
