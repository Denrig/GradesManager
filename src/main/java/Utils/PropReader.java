package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropReader {

    public static String getPath(String path,String propriety) {
        try (InputStream input = new FileInputStream("Resources/Student.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            input.close();
            return prop.getProperty(propriety);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

