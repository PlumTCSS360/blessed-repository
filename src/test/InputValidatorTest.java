package test;

import model.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @BeforeEach
    void setUp() {
        InputValidator validator = new InputValidator();
    }

    @Test
    void validName() {
//        {
//                "[", "]"};
        InputValidator validator = new InputValidator();
        String emptyN = "";
        String longerTwentyFive = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String firstInvalid = "`aaaaaaaaaaaaaaaaaaaaaaa";
        String secondInvalid = "~aaaaaaaaaaaaaaaaaaaaaaa";
        String thirdInvalid = "a!aaaaaaaaaaaaaaaaaaaaaa";
        String forthInvalid = "aa@aaaaaaaaaaaaaaaaaaaaa";
        String fifthInvalid = "aa#aaaaaaaaaaaaaaaaaaaaa";
        String dollarSign = "aaaa$aaaaaaaaaaaaaaaaaaa";
        String percentSign = "aaaaaaaaaaaaa%aaaaaaaaaa";
        String powerSign = "aaaaaaaaaaa^aaaaaaaaaaaa";
        String andSign = "aaaaaaaaaaaaaaaaaaa&aaaa";
        String starSign = "aaaaaaaaaaaaaaaa*aaaaaaa";
        String firstPharase = "aaaaaaaaaaaaaaaa(aaaaaaa";
        String secondPharase = "aaaaaaaaaaaaaaaaa)aaaaaa";
        String plus = "aaaaaaaaaaaaaaaaaaa+aaaa";
        String equal = "aaaaaaaaaaaaaaaaaaaaa=aa";
        String sixthInvalid = "aaaaaaaaaaaaaaaaaaaaaa{a";
        String seventhInvalid = "aaaaaaaaaaaaaaaaaaaaaaa}";
        String bar = "aaaaa|aaaaaaaaaaaaaaaaaa";
        String eighth = "aaa\\aaaaaaaaaaaaaaaaaaaa";
        String slash = "aaa/aaaaaaaaaaaaaaaaaaaa";
        String nine = "aaa:aaaaaaaaaaaaaaaaaaaa";
        String ten = "aaa;aaaaaaaaaaaaaaaaaaaa";
        String quotation = "aaa\"aaaaaaaaaaaaaaaaaaaa";
        String greater = "aaaaaa<aaaaaaaaaaaaaaaaa";
        String less = "aaaaaaaa>aaaaaaaaaaaaaaa";
        String eleven = "aaaaaaaaaaaa,aaaaaaaaaaa";
        String dot = "aaaaaaaaaaaaaaa.aaaaaaaa";
        String question = "aaaaaaaaaaaaaaaaa?aaaaaa";
        String twelve = "aaaaaaaaa[aaaaaaaaaaaaaa";
        String last = "aaaaaaaaaaaaaaa]aaaaaaaa";
        String correct = "almnopQRSedacabzajkafgia";

        assertFalse(validator.validName(emptyN));
        assertFalse(validator.validName(longerTwentyFive));
        assertFalse(validator.validName(firstInvalid));
        assertFalse(validator.validName(secondInvalid));
        assertFalse(validator.validName(thirdInvalid));
        assertFalse(validator.validName(forthInvalid));
        assertFalse(validator.validName(fifthInvalid));
        assertFalse(validator.validName(dollarSign));
        assertFalse(validator.validName(percentSign));
        assertFalse(validator.validName(powerSign));
        assertFalse(validator.validName(andSign));
        assertFalse(validator.validName(starSign));
        assertFalse(validator.validName(firstPharase));
        assertFalse(validator.validName(secondPharase));
        assertFalse(validator.validName(plus));
        assertFalse(validator.validName(equal));
        assertFalse(validator.validName(sixthInvalid));
        assertFalse(validator.validName(seventhInvalid));
        assertFalse(validator.validName(bar));
        assertFalse(validator.validName(eighth));
        assertFalse(validator.validName(slash));
        assertFalse(validator.validName(nine));
        assertFalse(validator.validName(ten));
        assertFalse(validator.validName(quotation));
        assertFalse(validator.validName(greater));
        assertFalse(validator.validName(less));
        assertFalse(validator.validName(eleven));
        assertFalse(validator.validName(dot));
        assertFalse(validator.validName(question));
        assertFalse(validator.validName(twelve));
        assertFalse(validator.validName(last));
        assertTrue(validator.validName(correct));




    }

//    @Test
//    void validImageFile() {
//    }
}