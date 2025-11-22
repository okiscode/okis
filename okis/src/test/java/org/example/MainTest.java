package org.example;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
public class MainTest {
    @Test(groups = {"positive", "deposit"})
    public void testDepositPositiveAmount() {
        App app = new App();
        boolean result = app.deposit(100.0);
        Assert.assertTrue(result);
        Assert.assertEquals(app.getBalance(), 100.0, 0.001);
    }
    @Test(groups = {"positive", "withdraw"})
    public void testWithdrawSufficientFunds() {
        App app = new App();
        app.deposit(200.0);
        boolean result = app.withdraw(50.0);
        Assert.assertTrue(result);
        Assert.assertEquals(app.getBalance(), 150.0, 0.001);
    }
    @Test(groups = {"positive", "balance"})
    public void testGetBalance() {
        App app = new App();
        Assert.assertEquals(app.getBalance(), 0.0, 0.001);
        app.deposit(75.5);
        Assert.assertEquals(app.getBalance(), 75.5, 0.001);
    }
    @Test(groups = {"positive", "history"})
    public void testGetHistory() {
        App app = new App();
        Assert.assertTrue(app.getHistory().isEmpty());
        app.deposit(100.0);
        Assert.assertEquals(app.getHistory().size(), 1);
    }
    @Test(groups = {"positive", "calculation"})
    public void testCalculateFutureValue() {
        App app = new App();
        double result = app.calculateFutureValue(1000.0, 12.0, 12);
        Assert.assertTrue(result > 1000.0);
    }
    @Test(groups = {"negative", "deposit"})
    public void testDepositNegativeAmount() {
        App app = new App();
        boolean result = app.deposit(-50.0);
        Assert.assertFalse(result);
        Assert.assertEquals(app.getBalance(), 0.0, 0.001);
    }
    @Test(groups = {"negative", "withdraw"})
    public void testWithdrawInsufficientFunds() {
        App app = new App();
        app.deposit(30.0);
        boolean result = app.withdraw(50.0);
        Assert.assertFalse(result);
        Assert.assertEquals(app.getBalance(), 30.0, 0.001);
    }
    @Test(groups = {"negative", "withdraw"})
    public void testWithdrawZeroAmount() {
        App app = new App();
        app.deposit(100.0);
        boolean result = app.withdraw(0.0);
        Assert.assertFalse(result);
        Assert.assertEquals(app.getBalance(), 100.0, 0.001);
    }
    @Test(groups = {"exceptions", "deposit"},expectedExceptions = IllegalArgumentException.class)
    public void testDepositWithException() {
        App app = new App();
        app.depositWithException(-50.0);
    }
    @Test(groups = {"exceptions", "withdraw"},expectedExceptions = IllegalArgumentException.class)
    public void testWithdrawWithException() {
        App app = new App();
        app.deposit(30.0);
        app.withdrawWithException(50.0);
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
        boolean result = app.deposit(amount);
        Assert.assertEquals(result, expectedResult);
        Assert.assertEquals(app.getBalance(), expectedBalance, 0.001);
    }
}