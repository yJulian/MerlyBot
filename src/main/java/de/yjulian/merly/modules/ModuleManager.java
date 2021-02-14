package de.yjulian.merly.modules;

import de.yjulian.merly.util.ProgramState;
import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.events.EventAdapter;
import de.yjulian.merly.events.EventListener;
import de.yjulian.merly.events.ProgramStateChangedEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleManager implements EventAdapter {

    private static final String README_FILE_NAME = "README.txt";
    private static final String README_RESOURCE = "ModuleReadme.txt";
    private static final String JAVA_SUFFIX = ".jar";
    private final File moduleFolder = new File("modules/");
    private final List<InternalModule> modules = new ArrayList<>();

    public ModuleManager() {
        checkFolder();
        loadModules();
    }

    private void loadModules() {
        for (File file : moduleFolder.listFiles()) {
            if (file.getName().endsWith(JAVA_SUFFIX)) {
                try {
                    modules.add(new InternalModule(file));
                } catch (MalformedURLException e) {
                    MerlyBot.getLogger().warn(String.format("The File %s could not be read.", file.getName()), e);
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    MerlyBot.getLogger().warn("Could not find or load main class", e);
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    MerlyBot.getLogger().warn("Could not initiate main class. Is it private?", e);
                }
            }
        }
    }

    @EventListener
    public void onProgramStateChanged(ProgramStateChangedEvent event) {
        ProgramState programState = event.getProgramState();

        switch (programState) {
            case INIT: modules.forEach(internalModule -> internalModule.getModule().onInit()); break;
            case POST_INIT: modules.forEach(internalModule -> internalModule.getModule().onPostInit()); break;
            case RUNNING: modules.forEach(internalModule -> internalModule.getModule().onStartupComplete()); break;
            case SHUTDOWN: modules.forEach(internalModule -> internalModule.getModule().onShutdown()); break;
        }
    }

    public List<InternalModule> getModules() {
        return Collections.unmodifiableList(modules);
    }

    private void checkFolder() {
        if (!moduleFolder.exists()) {
            if (!moduleFolder.mkdir()) {
                MerlyBot.getLogger().warn("Could not create module folder.");
            } else {
                try {
                    File file = new File(moduleFolder, README_FILE_NAME);
                    if (file.createNewFile()) {
                        FileOutputStream fos = new FileOutputStream(file);
                        InputStream ras = getClass().getClassLoader().getResourceAsStream(README_RESOURCE);
                        if (ras == null) {
                            MerlyBot.getLogger().warn("Readme resource null.");
                        } else {
                            ras.transferTo(fos);
                        }
                    }
                } catch (IOException e) {
                    MerlyBot.getLogger().warn(String.format("Error creating %s", README_FILE_NAME), e);
                }
            }
        }
    }

}
