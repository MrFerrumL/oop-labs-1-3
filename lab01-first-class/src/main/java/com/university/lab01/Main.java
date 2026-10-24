package com.university.lab01;

/** Демонстрация лабораторной работы 1. */
public class Main {

    /** Создаёт и выводит три независимых объекта. */
    public static void main(String[] args) {
        Book first = makeBook(1, "Война и мир", "Толстой Л.Н.", 1869);
        Book second = makeBook(2, "Мастер и Маргарита", "Булгаков М.А.", 1967);
        Book third = makeBook(3, "Преступление и наказание", "Достоевский Ф.М.", 1866);
        Book[] books = {first, second, third};
        System.out.println("=== Список книг ===");
        for (int i = 0; i < books.length; i++) {
            System.out.println((i + 1) + ". " + books[i].getDescription());
        }
        try {
            first.setYear(-1);
        } catch (IllegalArgumentException exception) {
            System.out.println("Ожидаемая ошибка: " + exception.getMessage());
        }
        System.out.println("Год после ошибки: " + first.getYear());
    }

    private static Book makeBook(long id, String title, String author, int year) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
        return book;
    }
}
