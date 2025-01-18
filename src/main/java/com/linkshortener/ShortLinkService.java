package main.java.com.linkshortener;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShortLinkService {
    private static Map<String, User> users = new HashMap<>(); // Хранение пользователей
    private static User currentUser; // Текущий пользователь

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в сервис сокращения ссылок!");
        while (true) {
            System.out.println("Текущий пользователь: " + (currentUser != null ? currentUser.getUuid() : "Пользователь не выбран или не создан"));
            System.out.println("Выберите действие (например, 1):");
            System.out.println("1 - Создать нового пользователя");
            System.out.println("2 - Выбрать существующего пользователя");
            System.out.println("3 - Выйти");
            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Чтение новой строки

            switch (choice) {
                case 1:
                    System.out.print("Введите имя пользователя: ");
                    String name = scanner.nextLine();
                    User newUser = new User(name);
                    users.put(newUser.getUuid(), newUser); // Сохранение нового пользователя
                    System.out.println("Создан новый пользователь с ID: " + newUser.getUuid());
                    break;
                case 2:
                    System.out.print("Введите UUID пользователя: ");
                    String uuid = scanner.nextLine();
                    if (users.containsKey(uuid)) {
                        currentUser = users.get(uuid); // Установка текущего пользователя
                        System.out.println("Выбран пользователь: " + currentUser.getUuid());
                        userMenu(scanner); // Переход к меню пользователя
                    } else {
                        System.out.println("Пользователь не найден.");
                    }
                    break;
                case 3:
                    System.out.println("Выход из приложения.");
                    return; // Завершение работы приложения
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    // Меню для авторизованного пользователя
    private static void userMenu(Scanner scanner) {
        while (true) {
            System.out.println("Текущий пользователь: " + currentUser.getUuid());
            System.out.println("Выберите действие:");
            System.out.println("1 - Создать сокращенную ссылку");
            System.out.println("2 - Перейти по сокращенной ссылке");
            System.out.println("3 - Показать мои ссылки");
            System.out.println("4 - Удалить ссылку");
            System.out.println("5 - Изменить лимит переходов");
            System.out.println("6 - Сменить пользователя");
            System.out.println("7 - Выйти");
            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Чтение новой строки

            switch (choice) {
                case 1:
                    createShortLink(scanner);
                    break;
                case 2:
                    visitShortLink(scanner);
                    break;
                case 3:
                    showUserLinks();
                    break;
                case 4:
                    deleteShortLink(scanner);
                    break;
                case 5:
                    modifyClickLimit(scanner);
                    break;
                case 6:
                    currentUser = null; // Смена пользователя
                    System.out.println("Вы вышли из текущего аккаунта.");
                    return;
                case 7:
                    System.out.println("Выход из приложения.");
                    System.exit(0);
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    // Метод на основе заданных символов выводит рандомное значение для сокращенной ссылки
    private static String generateShortUrl(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder shortUrl = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            shortUrl.append(characters.charAt(randomIndex));
        }

        return shortUrl.toString();
    }

    //метод создания короткой ссылки, длиной 6 символов
    private static void createShortLink(Scanner scanner) {
        System.out.print("Введите оригинальный URL: ");
        String originalUrl = scanner.nextLine();
        String shortUrl = generateShortUrl(6); // Генерация короткой ссылки длиной 6 символов
        System.out.print("Введите лимит переходов: ");
        int limit = scanner.nextInt();
        System.out.print("Введите время действия ссылки в часах: ");
        int hours = scanner.nextInt();
        long expirationTime = System.currentTimeMillis() + (hours * 3600000); // Преобразование часов в миллисекунды
        ShortLink newLink = new ShortLink(originalUrl, shortUrl, limit, expirationTime);
        currentUser.addShortLink(newLink);
        System.out.println("Сокращенная ссылка создана: " + newLink.getShortUrl());
    }

    //Метод взаимодействия с ссылкой, а также проверка ссылки на лимиты
    private static void visitShortLink(Scanner scanner) {
        System.out.print("Введите сокращенный URL: ");
        String shortUrl = scanner.nextLine();
        for (ShortLink link : currentUser.getShortLinks()) {
            if (link.getShortUrl().equals(shortUrl)) {
                if (link.isExpired()) {
                    System.out.println("Ссылка недоступна (истекло время действия).");
                } else if (link.getRemainingClicks() > 0) {
                    link.decrementClicks();
                    System.out.println("Переход по ссылке: " + link.getOriginalUrl());
                    // Здесь можно открыть оригинальный URL в браузере, например:
                    // Desktop.getDesktop().browse(new URI(link.getOriginalUrl()));
                } else {
                    System.out.println("Ссылка недоступна (достигнут лимит переходов).");
                }
                return;
            }
        }
        System.out.println("Ссылка не найдена.");
    }

    //Метод работы с пользователем
    private static void showUserLinks() {
        System.out.println("Ваши сокращенные ссылки:");
        for (ShortLink link : currentUser.getShortLinks()) {
            String status = link.isExpired() ? "Истекло" : (link.getRemainingClicks() > 0 ? "Доступно" : "Недоступно");
            System.out.println("Оригинальный URL: " + link.getOriginalUrl() +
                    ", Сокращенный URL: " + link.getShortUrl() +
                    ", Оставшиеся переходы: " + link.getRemainingClicks() +
                    ", Статус: " + status);
        }
    }

    private static void deleteShortLink(Scanner scanner) {
        System.out.print("Введите сокращенный URL для удаления: ");
        String shortUrl = scanner.nextLine();
        for (ShortLink link : currentUser.getShortLinks()) {
            if (link.getShortUrl().equals(shortUrl)) {
                currentUser.removeShortLink(link);
                System.out.println("Ссылка удалена.");
                return;
            }
        }
        System.out.println("Ссылка не найдена.");
    }

    private static void modifyClickLimit(Scanner scanner) {
        System.out.print("Введите сокращенный URL для изменения лимита: ");
        String shortUrl = scanner.nextLine();
        for (ShortLink link : currentUser.getShortLinks()) {
            if (link.getShortUrl().equals(shortUrl)) {
                System.out.print("Введите новый лимит переходов: ");
                int newLimit = scanner.nextInt();
                // Изменение лимита
                link.setLimit(newLimit);
                System.out.println("Лимит переходов изменен.");
                return;
            }
        }
        System.out.println("Ссылка не найдена.");
    }
}