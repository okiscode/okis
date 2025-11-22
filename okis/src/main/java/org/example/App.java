package org.example;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class App {
    private double balance = 0.0;
    private final List<String> history = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
    public void run() {
        boolean running = true;
        System.out.println("=== Консольный Банк ===");
        while (running) {
            printMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1 -> handleDeposit();
                case 2 -> handleWithdraw();
                case 3 -> handleShowBalance();
                case 4 -> handleShowHistory();
                case 5 -> handleCalculateInterest();
                case 0 -> {
                    System.out.println("До свидания");
                    running = false;
                }
                default -> System.out.println("Ошибка: неверный выбор, попробуйте снова");
            }
        }
        scanner.close();
    }
    private void printMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("1 - Положить деньги");
        System.out.println("2 - Снять деньги");
        System.out.println("3 - Проверить баланс");
        System.out.println("4 - История операций");
        System.out.println("5 - Рассчитать проценты по вкладу");
        System.out.println("0 - Выход");
        System.out.print("Ваш выбор: ");
    }
    private int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка ввода. Введите число:");
            scanner.next();
        }
        return scanner.nextInt();
    }
    private void handleDeposit() {
        System.out.print("Введите сумму для пополнения: ");
        double amount = scanner.nextDouble();
        if (deposit(amount)) {
            System.out.println("Счёт успешно пополнен на " + amount);
        } else {
            System.out.println("Ошибка: сумма должна быть больше 0");
        }
    }
    private void handleWithdraw() {
        System.out.print("Введите сумму для снятия: ");
        double amount = scanner.nextDouble();
        if (withdraw(amount)) {
            System.out.println("Вы сняли " + amount);
        } else {
            System.out.println("Ошибка: недостаточно средств или некорректная сумма");
        }
    }
    private void handleShowBalance() {
        System.out.printf("Текущий баланс: %.2f%n", getBalance());
    }
    private void handleShowHistory() {
        List<String> operations = getHistory();
        if (operations.isEmpty()) {
            System.out.println("История пуста");
        } else {
            System.out.println("\nИстория операций:");
            operations.forEach(System.out::println);
        }
    }
    private void handleCalculateInterest() {
        System.out.print("Введите процентную ставку: ");
        double rate = scanner.nextDouble();
        System.out.print("Введите срок (мес.): ");
        int months = scanner.nextInt();
        double result = calculateFutureValue(balance, rate, months);
        System.out.printf("Через %d мес. при ставке %.2f%% сумма составит: %.2f%n",months, rate, result);
    }
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addToHistory("Пополнение: +" + amount);
            return true;
        }
        return false;
    }
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addToHistory("Снятие: -" + amount);
            return true;
        }
        return false;
    }
    public double getBalance() {
        return balance;
    }
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
    private void addToHistory(String record) {
        history.add(LocalDateTime.now() + " — " + record);
    }
    public double calculateFutureValue(double amount, double rate, int months) {
        if (amount < 0 || rate < 0 || months < 0) return 0;
        double monthlyRate = rate / 100 / 12;
        return amount * Math.pow(1 + monthlyRate, months);
    }
    public void depositWithException(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }
        balance += amount;
        addToHistory("Пополнение: +" + amount);
    }
    public void withdrawWithException(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма для снятия должна быть положительной");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }
        balance -= amount;
        addToHistory("Снятие: -" + amount);
    }
}