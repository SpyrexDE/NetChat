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
import java.util.HashMap;
import java.util.Properties;

public class Props {

    public static String FALLBACK_LANG = "en";

    public static String configPrefix = "resources/properties/output_";
    public static String confPath = System.getProperty("java.io.tmpdir") + "/NetChat/config.properties";

    private static Properties defaultConfig = new Properties();
    private static Properties config = new Properties();
    private static Properties fallbackProps = new Properties();
    private static Properties props = new Properties();

    public static boolean init()
    {
        String resourceName = "resources/properties/defaultConfig.properties";

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream resourceStream = loader.getResourceAsStream(resourceName))
        {
            File f = new File(confPath);

            InputStreamReader reader;
            InputStreamReader reader2;

            reader = new InputStreamReader(new FileInputStream(f));
            reader2 = new InputStreamReader(resourceStream, Charset.forName("UTF-8"));
            
            if(reader != null)
                config.load(reader);
            if(reader2 != null)
                defaultConfig.load(reader2);
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

            if(langTag.equals(FALLBACK_LANG))
                props.load(reader);
            else
            {
                String resourceName2 = configPrefix + FALLBACK_LANG + ".properties";
                InputStream resourceStream2 = loader.getResourceAsStream(resourceName2);
                InputStreamReader reader2 = new InputStreamReader(resourceStream2, Charset.forName("UTF-8"));
                fallbackProps.load(reader2);
            }
                
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
        String result = props.getProperty(key);
        if(result == null)
            result = fallbackProps.getProperty(key);
        
        return result != null ? result : "[MESSAGE-NOT-AVAILABLE: " + key + "]";
    }

    public static void setConf(String key, String value) 
    {
        config.setProperty(key, value);
        try {
            File file = new File(confPath);
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
        return config.getProperty(key) != null ? config.getProperty(key) : defaultConfig.getProperty(key);
    }

    public static void setConfVar(String var_name, String var_value) 
    {
        String s = config.getProperty("vars") != null ? config.getProperty("vars") : new HashMap<String, String>().toString();
        HashMap<String, String> vars = strToHashMap(s);
        vars.put(var_name, var_value);

        setConf("vars", vars.toString().replaceAll(" ", ""));
    }

    public static boolean removeConfVar(String var_name) 
    {
        String s = config.getProperty("vars");
        HashMap<String, String> vars = strToHashMap(s);
        String result = vars.remove(var_name);

        setConf("vars", vars.toString());

        return result != null;
    }

    public static HashMap<String, String> getConfVars() 
    {
        String s = config.getProperty("vars");
        HashMap<String, String> vars = strToHashMap(s);

        for(String key : vars.keySet())
        {
            vars.put(key, vars.get(key).replace("%_", " "));
        }

        return vars;
    }

    public static HashMap<String, String> strToHashMap(String s)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        String[] pairs = s.replace("{", "").replace("}", "").split(",");
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split("\\=");
            if(keyValue.length == 2)
                map.put(keyValue[0], keyValue[1]);
            else
            map.put(keyValue[0], "null");
        }
        return map;
    }

}
