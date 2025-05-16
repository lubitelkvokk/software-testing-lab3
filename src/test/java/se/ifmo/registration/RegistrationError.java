package se.ifmo.registration;

public enum RegistrationError {
    BUSY_PHONE_NUMBER("Номер телефона уже используется другим пользователем"),
    INCORRECT_PHONE_NUMBER("Некорректный номер телефона"),
    INVALID_PHONE_NUMBER("Некорректный номер");

    final String name;
    RegistrationError(String str){
       name = str;
    }

    @Override
    public String toString() {
        return name;
    }
}
