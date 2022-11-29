package input;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileUtils
{
    public static String readFullResource(String path)
    {
        StringBuilder result;

        result = new StringBuilder();
        try
        {
            BufferedReader reader;
            String line;

            reader = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(path)));
            while ((line = reader.readLine()) != null)
                result.append(line).append("\n");
        } catch (Exception e) { e.printStackTrace(); }

        return result.toString();
    }
}
