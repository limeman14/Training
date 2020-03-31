import java.util.*;
import java.util.stream.Collectors;

public class CardAnalysis {

    private static long income;
    private static long expenses;
    private List<Transaction> transactionList;
    private static Map<String, Long> expensesByCategory = new HashMap<>();

    CardAnalysis(List<Transaction> tlist) {
        this.transactionList = tlist;
        for (Transaction tr: transactionList) {
            if (tr.getDirection().equals(Transaction.Direction.EXPENSE)) {
                if (expensesByCategory.containsKey(tr.getCategory())) {
                    long newAmount = (expensesByCategory.get(tr.getCategory()) + tr.getAmount());
                    expensesByCategory.replace(tr.getCategory(), newAmount);
                }
                else {
                    expensesByCategory.put(tr.getCategory(), tr.getAmount());
                }
                expenses += tr.getAmount();
            }
            else {
                income += tr.getAmount();
            }
        }
    }


    public void printExpenses() {
        System.out.println("Расходы по категориям: ");
        LinkedHashMap<String, Long> mapForPrinting = expensesByCategory
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        for (String key : mapForPrinting.keySet()) {
            System.out.println(key + " - " + (double) mapForPrinting.get(key)/100 + " руб.");
        }
    }

    public double getIncome() {
        return (double) income/100;
    }

    public double getExpenses() {
        return (double) expenses/100;
    }

}
