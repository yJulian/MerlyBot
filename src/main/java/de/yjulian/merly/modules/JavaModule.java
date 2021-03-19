package de.yjulian.merly.modules;

public class JavaModule {

    public void onLoad() {}

    public void onInit() {}
    public void onPostInit() {}
    public void onStartupComplete() {}

    public void onShutdown() {}

    @SuppressWarnings("unchecked")
    public final <T extends JavaModule> T getModule(Class<T> aClass) {
        return (T) this;
    }

}
