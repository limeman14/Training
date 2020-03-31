import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        final String PATH = "BankCSV/src/main/resources/movementList.csv";

        Parcer parcer = new Parcer();
        List<Transaction> transactionList = parcer.createTransactionList(PATH);

        CardAnalysis cardAnalysis = new CardAnalysis(transactionList);
        System.out.println("Доход: " + cardAnalysis.getIncome() + " руб.\n"
                           + "Расходы: " + cardAnalysis.getExpenses() + " руб.\n");
        cardAnalysis.printExpenses();
    }
}
