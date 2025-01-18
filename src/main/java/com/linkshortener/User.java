package main.java.com.linkshortener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class User {
    private String uuid; // Уникальный идентификатор пользователя
    private String name; // Имя пользователя
    private List<ShortLink> shortLinks; // Список сокращенных ссылок

    // Конструктор пользователя
    public User(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString(); // Генерация уникального UUID
        this.shortLinks = new ArrayList<>(); // Инициализация списка ссылок
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public List<ShortLink> getShortLinks() {
        return shortLinks;
    }

    public void addShortLink(ShortLink link) {
        shortLinks.add(link); // Добавление сокращенной ссылки
    }

    public void removeShortLink(ShortLink link) {
        shortLinks.remove(link); // Удаление сокращенной ссылки
    }
}