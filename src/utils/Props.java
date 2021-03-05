package utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Properties;

public class Props {
    Locale[] supportedLocales = { Locale.ENGLISH, Locale.GERMAN };

    public static String configPrefix = "resources/properties/output_";

    private static Properties props;

    public static void init(Locale locale)
    {
        String resourceName = configPrefix + locale.getLanguage() + ".properties";

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        props = new Properties();

        try (InputStream resourceStream = loader.getResourceAsStream(resourceName))
        {
            props.load(new InputStreamReader(resourceStream, Charset.forName("UTF-8")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String get(String key)
    {
            return props.getProperty(key);
    }
}
