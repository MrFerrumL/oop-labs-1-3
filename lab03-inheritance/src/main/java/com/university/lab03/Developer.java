package com.university.lab03;

/** Разработчик с дополнительной технической премией. */
public class Developer extends Employee implements Reportable {
    private static final double BONUS_RATE = 0.10;
    private final String programmingLanguage;
    private final double techBonus;

    /** Инициализирует сотрудника, язык и техническую премию. */
    public Developer(String name, String department, double salary,
                     String programmingLanguage, double techBonus) {
        super(name, department, salary);
        this.programmingLanguage = requireText(programmingLanguage, "Язык");
        if (!Double.isFinite(techBonus) || techBonus < 0) {
            throw new IllegalArgumentException("Техническая премия должна быть конечной и >= 0");
        }
        this.techBonus = techBonus;
    }

    /** Возвращает язык программирования. */
    public String getProgrammingLanguage() { return programmingLanguage; }

    /** Возвращает техническую премию. */
    public double getTechBonus() { return techBonus; }

    /** Вычисляет десять процентов оклада плюс техническую премию. */
    @Override
    public double calculateBonus() { return getSalary() * BONUS_RATE + techBonus; }

    /** Расширяет базовое описание. */
    @Override
    public String toString() {
        return super.toString() + " — разработчик " + programmingLanguage;
    }

    /** Возвращает заглушку отчёта без вымышленных выполненных задач. */
    @Override
    public String generateReport() {
        return "Отчёт разработчика " + getName()
                + ": список завершённых задач — заглушка";
    }
}
