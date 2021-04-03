package de.yjulian.merly.scripting;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class MerlyScriptFile {

    private static final String VARIABLE_DECLARE_SYMBOL = "<-";
    private static final String EVENT_START_SYMBOL = "{";
    private static final String EVENT_END_SYMBOL = "}";
    private static final String SEND_INST = "send";

    private final HashMap<String, Object> variables = new HashMap<>();
    private boolean inEvent = false;

    public MerlyScriptFile(BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.endsWith(EVENT_START_SYMBOL)) {
                inEvent = true;
                parseEvent(line, reader);
                inEvent = false;
            } else {
                parseInstruction(line);
            }
        }
    }

    private void parseVariable(String line) {
        String[] data = line.split(VARIABLE_DECLARE_SYMBOL);

        String key = data[0];
        Object value = parseValue(data[1]);

        addVariable(key, value);
    }

    private Object parseValue(String value) {
        if (value.matches("^[0-9]+$")) {
            return Long.parseLong(value);
        } else if (value.matches("^[0-9]*\\.[0-9]+$")) {
            return Double.parseDouble(value);
        } else if (value.equals("true") || value.equals("false")) {
            return Boolean.valueOf(value);
        } else {
            return value;
        }
    }

    private void parseEvent(String currentLine, BufferedReader reader) throws IOException {
        String line = currentLine;
        do {
            if (line.equals(EVENT_END_SYMBOL)) {
                return;
            } else {
                parseInstruction(line);
            }
        } while ((line = reader.readLine()) != null);
    }

    private void parseInstruction(String inst) {
        if (inst.contains(VARIABLE_DECLARE_SYMBOL)) {
            parseVariable(inst);
        } else if (inst.startsWith(SEND_INST)) {
            if (!inEvent) {
                throw new IllegalArgumentException("Send only supported in event.");
            }
        }
    }

    private void addVariable(String key, Object value) {
        variables.put(key, value);
    }

    private Object getVariable(String key) {
        return variables.get(key);
    }

}
