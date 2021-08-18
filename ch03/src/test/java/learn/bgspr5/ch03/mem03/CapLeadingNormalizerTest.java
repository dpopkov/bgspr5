package learn.bgspr5.ch03.mem03;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CapLeadingNormalizerTest {

    CapLeadingNormalizer normalizer = new CapLeadingNormalizer();

    @DataProvider
    Object[][] data() {
        return new Object[][] {
                {"this is a test", "This Is A Test"},
                {"This IS a test", "This Is A Test"},
                {"this        is             a test", "This Is A Test"}
        };
    }

    @Test(dataProvider = "data")
    public void testTransform(String input, String expected) {
        String actual = normalizer.transform(input);
        assertEquals(actual, expected);
    }
}
