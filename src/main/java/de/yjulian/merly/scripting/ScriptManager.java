package de.yjulian.merly.scripting;

import de.yjulian.merly.bot.MerlyBot;

import java.io.*;

public class ScriptManager {

    private static final File SCRIPT_FOLDER = new File("scripts/");
    private static final String MERLY_SCRIPT_POSTFIX = ".ms";

    public ScriptManager() {
        initialize();
    }

    private void initialize() {
        if (!SCRIPT_FOLDER.exists()) {
            if (!SCRIPT_FOLDER.mkdir()) {
                MerlyBot.getLogger().warn("Error whilst creating script folder.");
            }
        }
    }

    private void loadScripts() {
        for (File file : SCRIPT_FOLDER.listFiles()) {
            if (file.isFile()) {
                if (file.getName().equals(MERLY_SCRIPT_POSTFIX)) {
                    try {
                        MerlyScriptFile merlyScript = new MerlyScriptFile(
                                new BufferedReader(new InputStreamReader(new FileInputStream(file))));
                    } catch (FileNotFoundException e) {
                        MerlyBot.getLogger().warn("Exception loading merly script file.", e);
                    } catch (IOException e) {
                        MerlyBot.getLogger().warn("Exception parsing merly script file.", e);
                    }
                }
            }
        }
    }

}
