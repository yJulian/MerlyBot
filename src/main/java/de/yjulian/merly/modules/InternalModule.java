package de.yjulian.merly.modules;

import de.yjulian.merly.exceptions.ModuleException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Map;

public class InternalModule {

    private static final String MODULE_YML_NAME = "module.yml";
    private static final String MAIN_KEY = "main";
    private static final Yaml YAML = new Yaml();

    private final URLClassLoader classLoader;
    private final JavaModule module;
    private final Map<?, ?> data;

    InternalModule(File file) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        this.classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
        InputStream information = this.classLoader.getResourceAsStream(MODULE_YML_NAME);
        this.data = YAML.loadAs(information, Map.class);
        if (!data.containsKey(MAIN_KEY)) {
            throw new ModuleException("Could not find or load main class.");
        }
        String main = (String) data.get(MAIN_KEY);

        Class<?> mainClassRaw = classLoader.loadClass(main);
        if (!mainClassRaw.getSuperclass().equals(JavaModule.class)) {
            throw new ModuleException(
                    String.format(
                            "Main class is not a child class from %s",
                            JavaModule.class.getSimpleName()
                    )
            );
        }

        this.module = (JavaModule) mainClassRaw.getConstructor().newInstance();
        this.module.onLoad();
    }

    public JavaModule getModule() {
        return module;
    }

    public URLClassLoader getClassLoader() {
        return classLoader;
    }

    public Map<?, ?> getData() {
        return Collections.unmodifiableMap(data);
    }

}
