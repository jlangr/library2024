package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.StringUtils.removeHyphens;

class StringUtilsTest {
   @Test
   void removeHyphensStripsHyphens() {
      assertEquals("12345678901234", removeHyphens("123-456-7890-1234"));
   }
}