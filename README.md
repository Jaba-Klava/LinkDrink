# Сервис сокращения ссылок

Описание проекта

Данный проект представляет собой сервис для сокращения URL-адресов. 
Пользователи могут регистрироваться, входить в систему и создавать короткие ссылки на длинные URL-адреса. 
Сервис автоматически генерирует уникальные короткие ссылки длиной 6 символов. 
Приложение было создано для компании Promo IT.

Приложение использует конфигурационный файл **config.properties**, в котором хранятся конфигурации для указания предельных значений максимальное количества переходов (max.click.limit) и максимальное время жизни ссылки в секундах (max.link.lifetime.seconds). 

Сервис НЕ использует БД, поэтому созданные пользователи и все ссылки и параметры хранятся в памяти в момент работы приложения. После закрытия приложения все данные исчезнут (!).

## Установка и запуск

1. Склонируйте репозиторий на свой компьютер:
   
    ```bash
    git clone https://github.com/Jaba-Klava/LinkDrink.git
   ```

2. Перейдите в директорию проекта:

```bash
    cd LinkDrink
```

3. Скомпилируйте и запустите проект:

```bash
    javac ShortLinkService.java
   java ShortLinkService
   ```

## Функциональность

**Регистрация пользователя:** Пользователь может зарегистрироваться, указав свое имя. 
После регистрации ему будет присвоен уникальный идентификатор (UUID).

**Вход в систему:** Зарегистрированные пользователи могут войти в систему, введя свой UUID.

**Создание короткой ссылки:** После входа в систему пользователь может создать короткую ссылку, 
вводя оригинальный URL, лимит переходов и время действия ссылки. Сервис автоматически генерирует уникальную короткую ссылку.

**Проверка уникальности:** При создании короткой ссылки сервис проверяет, существует ли уже такая ссылка у пользователя. 
Если ссылка уже существует, генерируется новая.

## Использование и команды

После запуска следуйте инструкции. 
В консоль следует вводить цифру, соответствующую выбранному действию.

### Для неавторизованных пользователей:
1. `1 - Создать нового пользователя`
  Создает нового пользователя с уникальным UUID. Вводятся любые символы, отражающие в совокупности уникально имя пользователя.
2. `2 - Выбрать существующего пользователя`  
  Позволяет выбрать пользователя из списка для дальнейших операций.
3. `3 - Выйти`
  Выходит из программы  

### Для авторизованных пользователей:
1. `1 - Создать сокращенную ссылку`  
  Позволяет создать короткую ссылку на основе длинного URL. Вы можете указать лимит переходов.
2. `2 - Перейти по сокращенной ссылке`   
  Переход по созданной короткой ссылке (открывает оригинальный URL в браузере).
3. `3 - Показать мои ссылки`  
  Отображает все созданные пользователем ссылки.
4. `4 - Удалить ссылку`
5. `5 - Изменить лимит переходов`  
  Позволяет изменить лимит переходов для выбранной ссылки.
6. `6 - Сменить пользователя`  
  Выход из текущего аккаунта и возврат к выбору пользователя.
7. `7 - Выйти` 
  Завершает работу приложения.

### Создание сокращенной ссылки

После выбора функции создания сокращенной ссылки пользователю будет предложено 
выбрать лимит по количеству переходов по ссылке, а также лимит действия ссылки в часах.

## Контакты

Проект был реализован Леваковой Кристиной в рамках учебного задания МИФИ для Promo IT.
