package finalproject.util.encryption;

import com.verbovskiy.server.exception.EncryptionException;
import com.verbovskiy.server.util.encryption.Cryptographer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class CryptographerTest {
    Cryptographer cryptographer;

    @BeforeClass
    public void setUp() {
        cryptographer = new Cryptographer();
    }

    @Test
    public void encryptPasswordPositiveTest() throws EncryptionException {
        String actual = cryptographer.encrypt("12345678");
        String expected = "FA585D89C851DD338A70DCF535AA2A92FEE7836DD6AFF1226583E88E0996293F16BC009C652826E0FC5C70" +
                "6695A03CDDCE372F139EFF4D13959DA6F1F5D3EABE";
        assertEquals(actual, expected);
    }

    @Test
    public void encryptPasswordNegativeTest() throws EncryptionException {
        String actual = cryptographer.encrypt("1234");
        String expected = "D404559F602EAB6FD602AC7680DACBFAADD13630335E951F097AF3900E9DE176B6DB";
        assertNotEquals(actual, expected);
    }
}