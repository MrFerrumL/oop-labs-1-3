package com.university.lab01;

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

        System.out.println("PASS: " + checks + " checks");
    }
}
