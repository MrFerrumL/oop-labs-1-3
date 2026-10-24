package com.university.lab02;

import java.time.Year;

/** Книга с закрытыми полями и проверкой входных данных. */
public final class Book {

    private long id;
    private String title = "Без названия";
    private String author = "Неизвестен";
    private int year;
    private static int counter;
    private static long lastAssignedId;

    /** Создаёт книгу с безопасными значениями по умолчанию. */
    public Book() { this(0, "Без названия", "Неизвестен", 0); }

    /** Основной конструктор; учитывает только успешно созданные книги. */
    public Book(long id, String title, String author, int year) {
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setId(id);
        counter++;
    }

    /** Создаёт книгу с незаданным идентификатором. */
    public Book(String title, String author, int year) {
        this(0, title, author, year);
    }

    /** Создаёт книгу с незаданными идентификатором и годом. */
    public Book(String title, String author) {
        this(0, title, author, 0);
    }

    /** Возвращает число успешно созданных объектов в текущем запуске. */
    public static int getCounter() { return counter; }

    /** Выдаёт следующий id выше всех ранее назначенных положительных id. */
    public static Book createBook(String title, String author, int year) {
        long nextId = Math.incrementExact(lastAssignedId);
        return new Book(nextId, title, author, year);
    }

    /** Выбирает краткий или полный формат описания. */
    public String getDescription(boolean shortFormat) {
        return shortFormat ? title + " (" + year + ")" : getDescription();
    }

    /** Принимает неотрицательный id; ноль означает, что id не задан. */
    public void setId(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id не может быть отрицательным");
        }
        this.id = id;
        lastAssignedId = Math.max(lastAssignedId, id);
    }

    /** Возвращает идентификатор книги. */
    public long getId() { return id; }

    /** Возвращает название книги. */
    public String getTitle() { return title; }

    /** Проверяет и сохраняет непустое название. */
    public void setTitle(String title) {
        this.title = requireText(title, "Название");
    }

    /** Возвращает автора книги. */
    public String getAuthor() { return author; }

    /** Проверяет и сохраняет непустое имя автора. */
    public void setAuthor(String author) {
        this.author = requireText(author, "Автор");
    }

    /** Возвращает год издания. */
    public int getYear() { return year; }

    /** Принимает год от нуля до текущего включительно. */
    public void setYear(int year) {
        int currentYear = Year.now().getValue();
        if (year < 0 || year > currentYear) {
            throw new IllegalArgumentException("Год: от 0 до " + currentYear);
        }
        this.year = year;
    }

    /** Возвращает полное описание книги. */
    public String getDescription() {
        return "\"" + title + "\" — " + author + " (" + year + ")";
    }

    /** Позволяет печатать книгу через println. */
    @Override
    public String toString() { return getDescription(); }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " не может быть пустым");
        }
        return value.strip();
    }
}
