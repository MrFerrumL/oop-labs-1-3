package com.university.lab01;

import java.time.Year;

/** Книга с закрытыми полями и проверкой входных данных. */
public final class Book {

    private long id;
    private String title = "Без названия";
    private String author = "Неизвестен";
    private int year;

    /** Принимает неотрицательный id; ноль означает, что id не задан. */
    public void setId(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id не может быть отрицательным");
        }
        this.id = id;
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
