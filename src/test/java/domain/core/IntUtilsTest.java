package domain.core;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntUtilsTest {
   @Nested
   class ParsePositiveInt {
      @Test
      void returnsCorrspondingInteger() {
         assertEquals(42, IntUtils.parsePositiveInt("42"));
      }

      @ParameterizedTest
      @CsvSource({"-1", "0"})
      void throwsWhenNotPositive(String numberText) {
         assertThrows(IllegalArgumentException.class, () ->
            IntUtils.parsePositiveInt(numberText));
      }
   }

   @Nested
   class ParseInt {
      @Test
      void returnsCorrespondingInteger() {
         assertEquals(-11, IntUtils.parseInt("-11"));
      }

      @Test
      void throwsWhenNotValidInteger() {
         assertThrows(IllegalArgumentException.class, () ->
            IntUtils.parseInt("hey"));
      }
   }
}