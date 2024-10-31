package util;

import org.junit.Test;

import static util.StringUtils.removeHyphens;
import static org.junit.Assert.*;

public class StringUtilsTest {
   @Test
   public void removeHyphensStripsHyphens() {
      assertEquals("12345678901234", removeHyphens("123-456-7890-1234"));
   }

}