package com.university.lab03;

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

        Employee employee = new Employee("Сотрудник", "IT", 80000);
        Manager manager = new Manager("Менеджер", "IT", 150000, 5);
        Developer developer = new Developer("Разработчик", "IT", 120000, "Java", 3000);
        Employee[] staff = {employee, manager, developer};
        double[] expected = {4000, 22500, 15000};
        for (int i = 0; i < staff.length; i++) {
            check(Math.abs(staff[i].calculateBonus() - expected[i]) < 0.000001,
                    "Полиморфное вычисление премии " + i);
        }
        check(manager.toString().contains("подчинённых: 5"), "Описание менеджера");
        check(developer.toString().contains("Java"), "Описание разработчика");
        check(employee.getSalary() == 80000, "Оклад");
        check(employee.getDepartment().equals("IT"), "Отдел");
        check(manager.getSubordinatesCount() == 5, "Подчинённые");
        check(developer.getProgrammingLanguage().equals("Java"), "Язык");
        check(developer.getTechBonus() == 3000, "Техническая премия");
        java.util.List<Reportable> reporters = java.util.List.of(manager, developer);
        for (Reportable reporter : reporters) {
            check(reporter.generateReport().contains("заглушка"), "Интерфейсный вызов");
        }
        rejects(() -> new Employee(null, "IT", 1));
        rejects(() -> new Employee(" ", "IT", 1));
        rejects(() -> new Employee("A", "", 1));
        for (double invalid : new double[] {0, -1, Double.NaN, Double.POSITIVE_INFINITY}) {
            rejects(() -> new Employee("A", "B", invalid));
        }
        rejects(() -> new Manager("A", "B", 100, -1));
        rejects(() -> new Developer("A", "B", 100, " ", 0));
        for (double invalid : new double[] {-1, Double.NaN, Double.POSITIVE_INFINITY}) {
            rejects(() -> new Developer("A", "B", 100, "Java", invalid));
        }
        check(new Manager("A", "B", 100, 0).getSubordinatesCount() == 0, "Ноль подчинённых");
        check(new Developer("A", "B", 100, "Java", 0).calculateBonus() == 10,
                "Нулевая техническая премия");

        System.out.println("PASS: " + checks + " checks");
    }
}
