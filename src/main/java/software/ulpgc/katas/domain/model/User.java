package software.ulpgc.katas.domain.model;

public record User(String name, Gender gender, byte[] picture) {
    public enum Gender {
        male, female
    }
}
