import com.opencsv.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parcer {

    private final int DATE_POSITION = 3;
    private final int DESCRIPTION_POSITION = 5;
    private final int INCOME_POSITION = 6;
    private final int EXPENSE_POSITION = 7;

    private List<String[]> allRows;
    private static List<Transaction> transactionList = new ArrayList<>();

    public List<Transaction> createTransactionList(String path) throws IOException {
        FileReader reader = new FileReader(path);
        CSVParser parser = new CSVParserBuilder()
                .withQuoteChar('\"')
                .withSeparator(',')
                .withEscapeChar('|')
                .build();
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();
        allRows = csvReader
                .readAll()
                .stream()
                .peek(x -> x[EXPENSE_POSITION] = x[EXPENSE_POSITION].replace(',', '.'))
                .peek(x -> x[DESCRIPTION_POSITION] = x[DESCRIPTION_POSITION].replaceAll("\\\\", "/"))
                .collect(Collectors.toList());
        for (String[] row : allRows) {
            LocalDate date = LocalDate.parse(row[DATE_POSITION], DateTimeFormatter.ofPattern("dd.MM.yy"));
            String description = row[DESCRIPTION_POSITION];
            Transaction.Direction direction;
            long amount;
            if (Double.parseDouble(row[INCOME_POSITION]) == 0) {
                amount = (long) (Double.parseDouble(row[EXPENSE_POSITION]) * 100);
                direction = Transaction.Direction.EXPENSE;
            }
            else {
                amount = (long) (Double.parseDouble(row[INCOME_POSITION]) * 100);
                direction = Transaction.Direction.INCOME;
            }
            transactionList.add(new Transaction(amount, date, direction, description));
        }
        return transactionList;
    }
}
