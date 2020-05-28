import java.time.LocalDate;

public class Transaction {

    //blablabla
    //fuck YOU!
    private long amount;
    private LocalDate date;
    private Direction direction;
    private String category;

    public enum Direction {
        EXPENSE,
        INCOME
    }

    Transaction(long amount, LocalDate date, Direction direction, String description){
        this.amount = amount;
        this.date = date;
        this.direction = direction;
        String category = description.split("\\s{4,}")[1];
        this.category = category.substring(category.lastIndexOf("/") + 1);
    }

    @Override
    public String toString() {
        return date.toString() + ", тип операции: "
                + direction.name()
                + ", сумма: " + (double) amount/100
                + " руб., категория : "
                + category;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getCategory() {
        return category;
    }
}
