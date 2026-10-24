package com.university.lab02;

/** Автономные проверки без внешних библиотек. */
public class LabTest {

    private static int checks;

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) { throw new AssertionError(message); }
    }

    private static void rejects(Runnable action) {
        checks++;
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Ожидалось IllegalArgumentException");
    }

    /** Запускает проверки и завершает процесс ошибкой при нарушении. */
    public static void main(String[] args) {

        Book book = new Book();
        book.setId(7);
        book.setTitle("  Книга  ");
        book.setAuthor("  Автор  ");
        book.setYear(2020);
        check(book.getId() == 7, "Id");
        check(book.getTitle().equals("Книга"), "Нормализация названия");
        check(book.getAuthor().equals("Автор"), "Нормализация автора");
        check(book.getDescription().equals("\"Книга\" — Автор (2020)"), "Описание");
        check(book.toString().equals(book.getDescription()), "toString");
        for (String invalid : new String[] {null, "", "  ", "\t\n"}) {
            rejects(() -> book.setTitle(invalid));
            rejects(() -> book.setAuthor(invalid));
        }
        rejects(() -> book.setId(-1));
        rejects(() -> book.setYear(-1));
        rejects(() -> book.setYear(java.time.Year.now().getValue() + 1));
        check(book.getYear() == 2020, "Ошибка не меняет год");
        check(book.getTitle().equals("Книга"), "Ошибка не меняет название");
        check(book.getAuthor().equals("Автор"), "Ошибка не меняет автора");
        book.setYear(0);
        check(book.getYear() == 0, "Нижняя граница года");
        book.setYear(java.time.Year.now().getValue());
        check(book.getYear() == java.time.Year.now().getValue(), "Верхняя граница года");
        Book another = new Book();
        another.setTitle("Другая книга");
        check(!book.getTitle().equals(another.getTitle()), "Независимость объектов");

        int start = Book.getCounter();
        Book empty = new Book();
        Book full = new Book(100, "A", "B", 2020);
        Book noId = new Book("A", "B", 2020);
        Book minimal = new Book("A", "B");
        Book factory = Book.createBook("A", "B", 2020);
        Book next = Book.createBook("C", "D", 2021);
        check(Book.getCounter() == start + 6, "Каждый конструктор считается один раз");
        check(empty.getId() == 0 && empty.getYear() == 0, "Значения по умолчанию");
        check(empty.getTitle().equals("Без названия"), "Название по умолчанию");
        check(empty.getAuthor().equals("Неизвестен"), "Автор по умолчанию");
        check(full.getId() == 100 && noId.getId() == 0, "Явный и незаданный id");
        check(minimal.getYear() == 0, "Конструктор из двух строк");
        check(factory.getId() == 101 && next.getId() == 102, "Автоинкремент фабрики");
        check(noId.getDescription(true).equals("A (2020)"), "Краткое описание");
        check(noId.getDescription(false).equals(noId.getDescription()), "Полное описание");
        int beforeFailure = Book.getCounter();
        rejects(() -> new Book("", "Автор", 2020));
        rejects(() -> new Book("Название", null, 2020));
        rejects(() -> new Book("Название", "Автор", -1));
        rejects(() -> new Book(-1, "Название", "Автор", 2020));
        rejects(() -> Book.createBook(null, "Автор", 2020));
        check(Book.getCounter() == beforeFailure, "Ошибки не увеличивают счётчик");
        Book afterFailure = Book.createBook("E", "F", 2020);
        check(afterFailure.getId() == 103, "Ошибка не расходует id");
        noId.setId(500);
        check(Book.createBook("G", "H", 2020).getId() == 501, "Фабрика учитывает setter id");

        System.out.println("PASS: " + checks + " checks");
    }
}
