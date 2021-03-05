package utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Properties;

public class Props {
    Locale[] supportedLocales = { Locale.ENGLISH, Locale.GERMAN };

    public static String configPrefix = "resources/properties/output_";

    private static Properties props = new Properties();;

    public static boolean init(Locale locale)
    {
        String resourceName = configPrefix + locale.getLanguage() + ".properties";

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream resourceStream = loader.getResourceAsStream(resourceName))
        {
            InputStreamReader reader = new InputStreamReader(resourceStream, Charset.forName("UTF-8"));

            if(reader != null)
                props.load(reader);
        }
        catch(NullPointerException e)
        {
            Console.error("err_lang_not_available", locale.toLanguageTag().toUpperCase());
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public static String get(String key)
    {
            return props.getProperty(key) == null ? "null" : props.getProperty(key);
    }
}
