package drawing;

import maths.Vector3f;

public class MeshMaker
{
    public static Mesh generateSquare(int w, int h)
    {
        Vector3f[] vertices;
        int[] indices;

        vertices = new Vector3f[w * h];
        indices = new int[w * h];
        for (int i = 0; i < vertices.length; i++)
        {
            vertices[i] = new Vector3f((float)(i % w) / w * 2 - 1,
                                    ((float)(i / w) / h) * 2 - 1, 0);
            indices[i] = i;
        }

        return new Mesh(vertices, indices);
    }
}
