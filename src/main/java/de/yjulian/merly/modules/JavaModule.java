package de.yjulian.merly.modules;

public class JavaModule {

    public void onLoad() {}

    public void onInit() {}
    public void onPostInit() {}
    public void onStartupComplete() {}

    public void onShutdown() {}

    public  <T extends JavaModule> T getModule(Class<T> aClass) {
        return (T) this;
    }

}
