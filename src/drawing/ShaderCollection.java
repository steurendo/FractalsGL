package drawing;

import enums.ShadersAvailable;

import java.util.HashMap;

public class ShaderCollection
{
    private HashMap<String, Shader> shaders;

    public ShaderCollection() throws Exception
    {
        shaders = new HashMap<>();
        initAllShaders();
    }

    private void initAllShaders() throws Exception
    {
        for (ShadersAvailable shader : ShadersAvailable.values())
            addShader(shader.getName());
    }
    public Shader getShader(ShadersAvailable shader)
    {
        return shaders.get(shader.getName());
    }
    private void addShader(String name) throws Exception
    {
        if (shaders.containsKey(name))
            throw new Exception("Unable to load Shader: '" + name + "'");

        shaders.put(name, new Shader(name));
    }
    private void removeShader(String name) throws Exception
    {
        if (!shaders.containsKey(name))
            throw new Exception("Shader not available: '" + name + "'");

        shaders.remove(name);
    }
    public void createAll()
    {
        shaders.values().forEach(Shader::create);
    }
    public void destroyAll()
    {
        shaders.values().forEach(Shader::destroy);
    }
}
