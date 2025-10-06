package Usuarios.Config;

public record JWTUserData(Long id, String email) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String email;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public JWTUserData build() {
            return new JWTUserData(id, email);
        }
    }
}
