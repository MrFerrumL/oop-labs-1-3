package com.university.lab03;

import java.util.List;
import java.util.Locale;

/** Демонстрирует полиморфизм базового и интерфейсного типов. */
public class Main {
    /** Выводит премии пяти сотрудников и отчёты четырёх из них. */
    public static void main(String[] args) {
        Manager manager1 = new Manager("Иванов", "IT", 150000, 5);
        Developer developer1 = new Developer("Петров", "IT", 120000, "Java", 3000);
        Manager manager2 = new Manager("Сидорова", "Продажи", 100000, 3);
        Developer developer2 = new Developer("Смирнова", "IT", 140000, "Python", 5000);
        Employee employee = new Employee("Кузнецов", "Поддержка", 80000);
        Employee[] employees = {manager1, developer1, manager2, developer2, employee};
        System.out.println("=== Полиморфизм ===");
        for (Employee item : employees) {
            System.out.printf(Locale.ROOT, "%s — бонус: %.2f руб.%n",
                    item, item.calculateBonus());
        }
        List<Reportable> reporters = List.of(manager1, developer1, manager2, developer2);
        System.out.println("\n=== Интерфейс Reportable ===");
        for (Reportable reporter : reporters) {
            System.out.println(reporter.generateReport());
        }
    }
}
