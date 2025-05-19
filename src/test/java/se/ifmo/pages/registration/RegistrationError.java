package se.ifmo.pages.registration;

public enum RegistrationError {
    BUSY_PHONE_NUMBER("Номер телефона уже используется другим пользователем"),
    INCORRECT_PHONE_NUMBER("Некорректный номер телефона"),
    INVALID_PHONE_NUMBER("Некорректный номер"),
    EMPTY_PHONE_AND_EMAIL("Укажите ваш мобильный телефон или адрес электронной почты");

    final String name;
    RegistrationError(String str){
       name = str;
    }

    @Override
    public String toString() {
        return name;
    }
}
