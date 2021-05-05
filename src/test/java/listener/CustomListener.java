package listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("\n[" + result.getStartMillis() + "] The test name is -> " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("\n" + ConsoleColors.GREEN_BOLD_BRIGHT + "[" + result.getStartMillis() + "] The test -> " + result.getName() + " status is SUCCESS" + ConsoleColors.RESET);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("\n" + ConsoleColors.RED_BOLD_BRIGHT + "[" + result.getStartMillis() + "] The test -> " + result.getName() + " status is FAILED" + ConsoleColors.RESET);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("\n" + ConsoleColors.CYAN_BOLD_BRIGHT + "[" + result.getStartMillis() + "] The test -> " + result.getName() + " status is SKIPPED" + ConsoleColors.RESET);
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("\n[" + context.getStartDate() + "] !!!!!!!!!!LET'S GO!!!!!!!!!!!!!");
        System.out.println("[" + context.getStartDate() + "] TESTS COUNT = " + context.getAllTestMethods().length);
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n" + ConsoleColors.GREEN_BOLD_BRIGHT + "[" + context.getStartDate() + "] SUCCESS TESTS COUNT = " + context.getPassedTests().size() + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "[" + context.getStartDate() + "] FAILED TESTS COUNT = " + context.getFailedTests().size() + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "[" + context.getStartDate() + "] SKIPPED TESTS COUNT = " + context.getSkippedTests().size() + ConsoleColors.RESET);
        System.out.println("\n[" + context.getStartDate() + "] !!!!!!!!!!END!!!!!!!!!!!!!\n");
    }
}
