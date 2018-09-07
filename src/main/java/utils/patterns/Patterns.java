package utils.patterns;

public interface Patterns {
    String LOGIN = "^[_A-Za-zа-яА-ЯёЁ0-9-\\+]+([.][_A-Za-zа-яА-ЯёЁ0-9-]+)*@[A-Za-zа-яА-ЯёЁ0-9-]+([.][A-Za-zа-яА-ЯёЁ0-9]+)*([.][A-Za-zа-яА-ЯёЁ]{2,})$";
    String PASSWORD = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    String NAME_FIELDS = "^[а-яА-ЯёЁa-zA-Z\\`]+$";
}
