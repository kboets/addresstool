package be.boets.addresstool.admin;

public record Admin(String mavenVersion) {


    public static final class AdminBuilder {
        private String mavenVersion;

        private AdminBuilder() {
        }

        public static AdminBuilder anAdmin() {
            return new AdminBuilder();
        }

        public AdminBuilder withMavenVersion(String mavenVersion) {
            this.mavenVersion = mavenVersion;
            return this;
        }

        public Admin build() {
            return new Admin(mavenVersion);
        }
    }
}
