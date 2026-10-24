package com.university.lab03;

/** Базовый сотрудник; также используется как тип массива. */
public class Employee {
    private static final double BASE_BONUS_RATE = 0.05;
    private final String name;
    private final String department;
    private final double salary;

    /** Проверяет и инициализирует общую часть сотрудника. */
    public Employee(String name, String department, double salary) {
        this.name = requireText(name, "Имя");
        this.department = requireText(department, "Отдел");
        if (!Double.isFinite(salary) || salary <= 0) {
            throw new IllegalArgumentException("Оклад должен быть положительным и конечным");
        }
        this.salary = salary;
    }

    /** Возвращает имя сотрудника. */
    public String getName() { return name; }

    /** Возвращает отдел сотрудника. */
    public String getDepartment() { return department; }

    /** Возвращает оклад сотрудника. */
    public double getSalary() { return salary; }

    /** Вычисляет базовую премию в размере пяти процентов. */
    public double calculateBonus() { return salary * BASE_BONUS_RATE; }

    /** Возвращает общую часть описания сотрудника. */
    @Override
    public String toString() { return name + " (" + department + ")"; }

    /** Проверяет обязательную строку для этого класса и подклассов. */
    protected static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " не может быть пустым");
        }
        return value.strip();
    }
}
