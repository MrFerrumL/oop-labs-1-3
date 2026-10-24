package com.university.lab03;

/** Менеджер является сотрудником и умеет формировать отчёт. */
public class Manager extends Employee implements Reportable {
    private static final double BONUS_RATE = 0.15;
    private final int subordinatesCount;

    /** Инициализирует базовую часть и число подчинённых. */
    public Manager(String name, String department, double salary,
                   int subordinatesCount) {
        super(name, department, salary);
        if (subordinatesCount < 0) {
            throw new IllegalArgumentException("Число подчинённых не может быть отрицательным");
        }
        this.subordinatesCount = subordinatesCount;
    }

    /** Возвращает количество подчинённых. */
    public int getSubordinatesCount() { return subordinatesCount; }

    /** Вычисляет премию менеджера в размере пятнадцати процентов. */
    @Override
    public double calculateBonus() { return getSalary() * BONUS_RATE; }

    /** Расширяет базовое описание. */
    @Override
    public String toString() {
        return super.toString() + " — менеджер, подчинённых: " + subordinatesCount;
    }

    /** Возвращает заглушку отчёта согласно условию работы. */
    @Override
    public String generateReport() {
        return "Отчёт менеджера " + getName() + ": " + subordinatesCount
                + " подчинённых (список — заглушка)";
    }
}
