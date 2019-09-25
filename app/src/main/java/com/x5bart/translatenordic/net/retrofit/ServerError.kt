package com.x5bart.translatenordic.net.retrofit

 public enum class ServerError private constructor(//Заданное направление перевода не поддерживается

    private val code: Int
) {
    UNKNOWN_ERROR(0), //Неизвестная ошибка
    NO_CONNECTION(0), //Нет Интернета
    NO_ERROR(200), //Операция выполнена успешно
    BAD_REQUEST(400),
    WRONG_API_KEY(401), //Неправильный API-ключ
    BLOCKED_API_KEY(402), //API-ключ заблокирован
    DAILY_LIMIT_EXCEEDED(404), //Превышено суточное ограничение на объем переведенного текста
    TEXT_SIZE_TO_BIG(413), //Превышен максимально допустимый размер текста
    CANT_TRANSLATE_TEXT(422), //Текст не может быть переведен
    WRONG_TRANSLATE_DIRECTION(501);


    companion object {

        fun fromCode(code: Int): ServerError {
            for (item in values()) {
                if (item.code == code) {
                    return item
                }
            }
            return UNKNOWN_ERROR
        }
    }
}
