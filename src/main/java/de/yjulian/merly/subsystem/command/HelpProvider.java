package de.yjulian.merly.subsystem.command;

public class HelpProvider {

    private final String description;
    private final String scope;

    private HelpProvider(String description, String scope) {
        this.description = description;
        this.scope = scope;
    }

    public String getDescription() {
        return description;
    }

    public String getScope() {
        return scope;
    }

    public static Builder Builder(String description) {
        return new Builder(description);
    }

    public static class Builder {

        private String description = "";
        private String scope = "";

        public Builder() {
        }

        public Builder(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public HelpProvider build() {
            return new HelpProvider(description, scope);
        }

    }

}
