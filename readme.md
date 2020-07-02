## Запуск проекта в IntelliJ IDEA

### Способ 1

1. Выберите __Run | Edit configuration__.

2. Добавьте новую конфигурацию __Gradle__.

3. Укажите текущий проект в поле __Gradle project__.

4. Укажите __run__ в поле __Tasks__.

Используйте эту конфигурацию для запуска проекта.

### Способ 2 (не рекоммендуется)

Для запуска проекта при помощи IntelliJ IDEA требуется 
настроить конфигурацию проекта. 

1. Скачайте [__JavaFX SDK__](https://gluonhq.com/products/javafx/).

2. Выберите __Run | Edit configuration__.

3. В поле __VM options__ добавьте

```
--module-path
%PATH_TO_FX%
--add-modules
javafx.controls,javafx.fxml
```

Где `%PATH_TO_FX%` - путь до директории __lib__ скачанного SDK, 
например `/Users/jetbrains/Desktop/javafx-sdk-12/lib`.

Если вы используете JavaFX SDK 14 вместе с OpenJDK 14, добавьте 
еще одну опцию:

```
--add-exports
javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED
```

Более подробную информацию можно найти на [сайте](https://www.jetbrains.com/help/idea/javafx.html#vm-options).