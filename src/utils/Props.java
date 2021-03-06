package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class Props {

    public static String configPrefix = "resources/properties/output_";


    private static Properties config = new Properties();
    private static Properties props = new Properties();

    public static boolean init()
    {
        String resourceName = "resources/properties/defaultConfig.properties";

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream resourceStream = loader.getResourceAsStream(resourceName))
        {
            File f = new File(System.getProperty("java.io.tmpdir") + "/NetChat/config.properties");

            InputStreamReader reader;

            if(f.exists())
                reader = new InputStreamReader(new FileInputStream(f));
            else
                reader = new InputStreamReader(resourceStream, Charset.forName("UTF-8"));

            if(reader != null)
                config.load(reader);
        }
        catch(NullPointerException e)
        {
            Console.print("Error while loading save data");
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return setLang();
    }

    public static boolean setLang()
    {
        return setLang(getConf("lang").toLowerCase());
    }

    public static boolean setLang(String langTag)
    {
        
        String resourceName = configPrefix + langTag.toLowerCase() + ".properties";

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream resourceStream = loader.getResourceAsStream(resourceName))
        {
            InputStreamReader reader = new InputStreamReader(resourceStream, Charset.forName("UTF-8"));

            //if(reader.read() != -1)
                props.load(reader);
                
        }
        catch(NullPointerException e)
        {
            Console.error("err_lang_not_available", langTag.toUpperCase());
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        setConf("lang", langTag.toLowerCase());
        return true;
    }

    public static String get(String key)
    {
            return props.getProperty(key);// == null ? "null" : props.getProperty(key);
    }

    public static void setConf(String key, String value) 
    {
        config.setProperty(key, value);
        try {
            String fName = System.getProperty("java.io.tmpdir") + "/NetChat/config.properties";
            File file = new File(fName);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);

            config.store(new FileOutputStream(file), null);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String getConf(String key) 
    {
        return config.getProperty(key);
    }
}
