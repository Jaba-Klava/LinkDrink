package main.java.com.linkshortener;

class ShortLink {
    private String originalUrl; // Оригинальный URL
    private String shortUrl; // Сокращенный URL
    private int limit; // Лимит переходов
    private int remainingClicks; // Оставшиеся переходы
    private long expirationTime; // Время жизни ссылки

    // Конструктор сокращенной ссылки
    public ShortLink(String originalUrl, String shortUrl, int limit, long expirationTime) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.limit = limit;
        this.remainingClicks = limit; // Изначально оставшиеся переходы равны лимиту
        this.expirationTime = expirationTime; // Установка времени жизни ссылки
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public int getRemainingClicks() {
        return remainingClicks;
    }

    public void decrementClicks() {
        if (remainingClicks > 0) {
            remainingClicks--; // Уменьшение оставшихся переходов
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime; // Проверка на истечение времени жизни
    }

    public void setLimit(int limit) {
        this.limit = limit;
        this.remainingClicks = limit; // Обновляем оставшиеся переходы при изменении лимита
    }
}