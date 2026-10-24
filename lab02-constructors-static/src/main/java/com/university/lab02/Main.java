package com.university.lab02;

/** Демонстрация лабораторной работы 2. */
public class Main {

    /** Показывает четыре конструктора, фабрику и перегрузку методов. */
    public static void main(String[] args) {
        Book empty = new Book();
        Book full = new Book(10, "Война и мир", "Толстой Л.Н.", 1869);
        Book withoutId = new Book("Преступление и наказание", "Достоевский Ф.М.", 1866);
        Book titleAuthor = new Book("Рассказы", "Чехов А.П.");
        Book factory = Book.createBook("Мастер и Маргарита", "Булгаков М.А.", 1967);
        System.out.println("=== Четыре конструктора и фабрика ===");
        Book[] books = {empty, full, withoutId, titleAuthor, factory};
        for (Book book : books) {
            System.out.println("id=" + book.getId() + ": " + book);
        }
        System.out.println("Краткое описание: " + withoutId.getDescription(true));
        System.out.println("Полное описание: " + withoutId.getDescription(false));
        int before = Book.getCounter();
        try {
            new Book(" ", "Автор", 2020);
        } catch (IllegalArgumentException exception) {
            System.out.println("Ожидаемая ошибка: " + exception.getMessage());
        }
        System.out.println("Всего создано книг: " + Book.getCounter());
        System.out.println("Счётчик после ошибки не изменился: "
                + (before == Book.getCounter()));
    }
}
