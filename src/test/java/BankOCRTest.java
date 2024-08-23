import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankOCRTest {
    @Test
    @DisplayName("parse record with all 0's")
    public void parseRecordWithAll0() {
        String[] zerosTest = {" _  _  _  _  _  _  _  _  _ ",
                              "| || || || || || || || || |",
                              "|_||_||_||_||_||_||_||_||_|"};
        BankOCR accountNumber = new BankOCR(zerosTest);
        assertEquals("000000000", accountNumber.parse());
    }

    @Test
    @DisplayName("parse record with all 1's")
    public void parseRecordWithAll1() {
        String[] onesTest = {"                           ",
                             "  |  |  |  |  |  |  |  |  |",
                             "  |  |  |  |  |  |  |  |  |"};
        BankOCR accountNumber = new BankOCR(onesTest);
        assertEquals("111111111", accountNumber.parse());
    }

    @Test
    @DisplayName("account with 1 through 9")
    public void testAccount1Through9() {
        String[] oneThroughNine = {"    _  _     _  _  _  _  _ ",
                                   "  | _| _||_||_ |_   ||_||_|",
                                   "  ||_  _|  | _||_|  ||_| _|"};
        BankOCR accountNumber = new BankOCR((oneThroughNine));
        assertEquals("123456789", accountNumber.parse());
    }

    @Test
    @DisplayName("valid account number")
    public void testAValidAccountNumber() {
        String[] validNumber = {
                " _  _  _  _        _  _  _ ",
                "| || || || |  |  || || || |",
                "|_||_||_||_|  |  ||_||_||_|"
        };
        BankOCR accountNumber = new BankOCR(validNumber);
        assertTrue(accountNumber.isValid());
    }

    @Test
    @DisplayName("invalid account number")
    public void testInvalidAccountNumber() {
        String[] oneThroughNine = {"    _  _     _  _  _  _  _ ",
                                   "  | _| _||_||_ |_   ||_||_|",
                                   "  ||_  _|  | _||_|  ||_| _|"};
        BankOCR accountNumber = new BankOCR((oneThroughNine));
        assertFalse(accountNumber.isValid());
    }

    @Test
    @DisplayName("parse record with illegible characters")
    public void parseRecordWithIllegibleCharacters() {
        String[] illegibleTest = {" _  _  _  _  _  _  _  _  _ ",
                                  "| || || || || || || || || |",
                                  "|_||_||_||_||_||_||_||_||_?"};
        BankOCR accountNumber = new BankOCR(illegibleTest);
        assertEquals("00000000?", accountNumber.parse());
    }

    @Test
    @DisplayName("account number with illegible characters results in invalid status")
    public void testIllegibleAccountNumberResultsInILL() {
        String[] illegibleTest = {" _  _  _  _  _  _  _  _  _ ",
                                  "| || || || || || || || || |",
                                  "|_||_||_||_||_||_||_||_||_?"};
        BankOCR accountNumber = new BankOCR(illegibleTest);
        assertEquals("00000000? ILL", accountNumber.getStatus());
    }

    @Test
    @DisplayName("account number with invalid checksum results in ERR")
    public void testInvalidChecksumResultsInERR() {
        String[] invalidChecksum = {" _  _  _  _  _  _  _  _    ",
                                    "| || || || || || || || |  |",
                                    "|_||_||_||_||_||_||_||_|  |"};
        BankOCR accountNumber = new BankOCR(invalidChecksum);
        assertEquals("000000001 ERR", accountNumber.getStatus());
    }
}
