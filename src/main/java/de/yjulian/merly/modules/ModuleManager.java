package de.yjulian.merly.modules;

import de.yjulian.merly.ProgramState;
import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.events.EventAdapter;
import de.yjulian.merly.events.EventListener;
import de.yjulian.merly.events.ProgramStateChangedEvent;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager implements EventAdapter {

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
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
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


    private void checkFolder() {
        if (!moduleFolder.exists()) {
            if (!moduleFolder.mkdir()) {
                MerlyBot.getLogger().warn("Could not create module folder.");
            }
        }
    }

}
