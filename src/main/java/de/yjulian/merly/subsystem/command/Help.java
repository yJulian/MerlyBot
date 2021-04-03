package de.yjulian.merly.subsystem.command;

public class Help {

    private final String description;
    private final String scope;

    private Help(String description, String scope) {
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

    @Override
    public String toString() {
        return String.format("Help{description='%s', scope='%s'}", description, scope);
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

        public Help build() {
            return new Help(description, scope);
        }

    }

}
