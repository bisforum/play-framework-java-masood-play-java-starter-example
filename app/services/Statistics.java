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

    public void setTotalSalesAmount(int totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public int getAverageAmountPerOrder() {
        return averageAmountPerOrder;
    }

    public void setAverageAmountPerOrder(int averageAmountPerOrder) {
        this.averageAmountPerOrder = averageAmountPerOrder;
    }
}
