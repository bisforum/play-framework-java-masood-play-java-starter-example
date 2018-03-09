package services;


public class Statistics {

    private int totalSalesAmount;
    private int averageAmountPerOrder;


    public Statistics(int totalSalesAmount, int averageAmountPerOrder) {
        this.totalSalesAmount = totalSalesAmount;
        this.averageAmountPerOrder = averageAmountPerOrder;
    }

    public int getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public int getAverageAmountPerOrder() {
        return averageAmountPerOrder;
    }

}
