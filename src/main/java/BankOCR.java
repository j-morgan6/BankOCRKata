import java.util.HashMap;
import java.util.Map;

public class BankOCR {

    private final String[] lines;
    private static final Map<String, Character> accountNumberMap = new HashMap<>();

    static {
        accountNumberMap.put(" _ " +
                             "| |" +
                             "|_|", '0');
        accountNumberMap.put("   " +
                             "  |" +
                             "  |", '1');
        accountNumberMap.put(" _ " +
                             " _|" +
                             "|_ ", '2');
        accountNumberMap.put(" _ " +
                             " _|" +
                             " _|", '3');
        accountNumberMap.put("   " +
                             "|_|" +
                             "  |", '4');
        accountNumberMap.put(" _ " +
                             "|_ " +
                             " _|", '5');
        accountNumberMap.put(" _ " +
                             "|_ " +
                             "|_|", '6');
        accountNumberMap.put(" _ " +
                             "  |" +
                             "  |", '7');
        accountNumberMap.put(" _ " +
                             "|_|" +
                             "|_|", '8');
        accountNumberMap.put(" _ " +
                             "|_|" +
                             " _|", '9');
    };

    public BankOCR(String [] lines) {
        this.lines = lines;
    }

    public String parse() {
        String result = "";
        int length = lines[0].length();
        for (int i = 0; i < length; i += 3) {
            String key = grabKey(i);
            result += getCharFromKey(key);
        }
        return result;
    }

    private String grabKey(int i) {
        String key = "";
        for (int j = 0; j < 3; j++)
            key += lines[j].substring(i, i + 3);
        return key;
    }

    private static Character getCharFromKey(String key) {
        return accountNumberMap.getOrDefault(key, '?');
    }

    public boolean isValid() {
        String checkAccountNumber = parse();
        if (containsPlaceHolder(checkAccountNumber))
            return false;
        int checksum = calcChecksum(checkAccountNumber);
        return isChecksumValid(checksum);
    }

    private static boolean isChecksumValid(int checksum) {
        return (checksum % 11) == 0;
    }

    private static int calcChecksum(String checkAccountNumber) {
        int checksum = 0;
        for (int i = 0; i < checkAccountNumber.length(); i++) {
            char currentChar = checkAccountNumber.charAt(i);
            int digit = Character.getNumericValue(currentChar);
            checksum += digit * (i + 1);
        }
        return checksum;
    }

    private static boolean containsPlaceHolder(String checkAccountNumber) {
        return checkAccountNumber.contains("?");
    }


    public String getStatus() {
        String parsedNumber = parse();
        if (containsPlaceHolder(parsedNumber)) {
            return parsedNumber + " " + "ILL";
        } else if (!isValid()) {
            return parsedNumber + " " + "ERR";
        } else {
            return "";
        }
    }
}
