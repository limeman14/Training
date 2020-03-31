package core;

import java.util.Comparator;

public class LineComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        Double num1, num2;
        if (o1.endsWith("А")) {num1 = Double.parseDouble(o1.replaceAll("А", "\\.5"));}
        else {num1 = Double.parseDouble(o1);}
        if (o2.endsWith("А")) {num2 = Double.parseDouble(o2.replaceAll("А", "\\.5"));}
        else {num2 = Double.parseDouble(o2);}

        return num1.compareTo(num2);
    }
}
