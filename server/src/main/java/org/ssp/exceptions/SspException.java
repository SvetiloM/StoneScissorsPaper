package org.ssp.exceptions;

public enum SspException {

    Ssp_1("Игра с id %s не обнаружена."),
    Ssp_2("Невозможно найти начатую игру для пользователя %s"),
    Ssp_3("Невозможно создать пользователя с именем %s"),
    Ssp_4("Невозможно найти пользователя с именем %s"),
    Ssp_5("Ошибка валидации токена"),
    Ssp_6("Ошибка парсинга токена");

    private String message;

    private SspException(String message) {
        this.message = name() + ": " + message;
    }

    public String getMessage() {
        return message;
    }
}
