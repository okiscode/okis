package org.example;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
public class MainTest {
    @Test(groups = {"positive", "deposit"})
    public void testDepositPositiveAmount() {
        App app = new App();
        final double depositAmount = 100.0;
        final boolean expectedResult = true;
        final double expectedBalance = 100.0;
        boolean actualResult;
        double actualBalance;
        actualResult = app.deposit(depositAmount);
        actualBalance = app.getBalance();
        Assert.assertEquals(actualResult, expectedResult);
        Assert.assertEquals(actualBalance, expectedBalance, 0.001);
    }
    @Test(groups = {"positive", "withdraw"})
    public void testWithdrawSufficientFunds() {
        App app = new App();
        final double initialDeposit = 200.0;
        final double withdrawAmount = 50.0;
        final boolean expectedResult = true;
        final double expectedBalance = 150.0;
        boolean actualResult;
        double actualBalance;
        app.deposit(initialDeposit);
        actualResult = app.withdraw(withdrawAmount);
        actualBalance = app.getBalance();
        Assert.assertEquals(actualResult, expectedResult);
        Assert.assertEquals(actualBalance, expectedBalance, 0.001);
    }
    @Test(groups = {"positive", "balance"})
    public void testGetBalance() {
        App app = new App();
        final double expectedInitialBalance = 0.0;
        final double depositAmount = 75.5;
        final double expectedBalanceAfterDeposit = 75.5;
        double actualBalance;
        actualBalance = app.getBalance();
        Assert.assertEquals(actualBalance, expectedInitialBalance, 0.001);
        app.deposit(depositAmount);
        actualBalance = app.getBalance();
        Assert.assertEquals(actualBalance, expectedBalanceAfterDeposit, 0.001);
    }
    @Test(groups = {"positive", "history"})
    public void testGetHistory() {
        App app = new App();
        final boolean expectedInitialHistory = true;
        final double depositAmount = 100.0;
        final int expectedHistorySize = 1;
        boolean actualInitialHistory;
        int actualHistorySize;
        actualInitialHistory = app.getHistory().isEmpty();
        Assert.assertEquals(actualInitialHistory, expectedInitialHistory);
        app.deposit(depositAmount);
        actualHistorySize = app.getHistory().size();
        Assert.assertEquals(actualHistorySize, expectedHistorySize);
    }
    @Test(groups = {"positive", "calculation"})
    public void testCalculateFutureValue() {
        App app = new App();
        final double amount = 1000.0;
        final double rate = 12.0;
        final int months = 12;
        final boolean expectedResult = true;
        double actualResult;
        boolean comparisonResult;
        actualResult = app.calculateFutureValue(amount, rate, months);
        comparisonResult = actualResult > amount;
        Assert.assertEquals(comparisonResult, expectedResult);
    }
    @Test(groups = {"negative", "deposit"})
    public void testDepositNegativeAmount() {
        App app = new App();
        final double depositAmount = -50.0;
        final boolean expectedResult = false;
        final double expectedBalance = 0.0;
        boolean actualResult;
        double actualBalance;
        actualResult = app.deposit(depositAmount);
        actualBalance = app.getBalance();
        Assert.assertEquals(actualResult, expectedResult);
        Assert.assertEquals(actualBalance, expectedBalance, 0.001);
    }
    @Test(groups = {"negative", "withdraw"})
    public void testWithdrawInsufficientFunds() {
        App app = new App();
        final double initialDeposit = 30.0;
        final double withdrawAmount = 50.0;
        final boolean expectedResult = false;
        final double expectedBalance = 30.0;
        boolean actualResult;
        double actualBalance;
        app.deposit(initialDeposit);
        actualResult = app.withdraw(withdrawAmount);
        actualBalance = app.getBalance();
        Assert.assertEquals(actualResult, expectedResult);
        Assert.assertEquals(actualBalance, expectedBalance, 0.001);
    }
    @Test(groups = {"negative", "withdraw"})
    public void testWithdrawZeroAmount() {
        App app = new App();
        final double initialDeposit = 100.0;
        final double withdrawAmount = 0.0;
        final boolean expectedResult = false;
        final double expectedBalance = 100.0;
        boolean actualResult;
        double actualBalance;
        app.deposit(initialDeposit);
        actualResult = app.withdraw(withdrawAmount);
        actualBalance = app.getBalance();
        Assert.assertEquals(actualResult, expectedResult);
        Assert.assertEquals(actualBalance, expectedBalance, 0.001);
    }
    @Test(groups = {"exceptions", "deposit"}, expectedExceptions = IllegalArgumentException.class)
    public void testDepositWithException() {
        App app = new App();
        final double depositAmount = -50.0;
        app.depositWithException(depositAmount);
    }
    @Test(groups = {"exceptions", "withdraw"}, expectedExceptions = IllegalArgumentException.class)
    public void testWithdrawWithException() {
        App app = new App();
        final double initialDeposit = 30.0;
        final double withdrawAmount = 50.0;
        app.deposit(initialDeposit);
        app.withdrawWithException(withdrawAmount);
    }
    @DataProvider(name = "depositData")
    public Object[][] depositDataProvider() {
        return new Object[][]{
                {100.0, true, 100.0},
                {50.5, true, 50.5},
                {-50.0, false, 0.0},
                {0.0, false, 0.0},
                {1000000.0, true, 1000000.0}
        };
    }
    @Test(groups = {"dataprovider", "deposit"}, dataProvider = "depositData")
    public void testDepositWithDataProvider(double amount, boolean expectedResult, double expectedBalance) {
        App app = new App();
        boolean actualResult;
        double actualBalance;
        actualResult = app.deposit(amount);
        actualBalance = app.getBalance();
        Assert.assertEquals(actualResult, expectedResult);
        Assert.assertEquals(actualBalance, expectedBalance, 0.001);
    }
}