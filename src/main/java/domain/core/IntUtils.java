package domain.core;

public class IntUtils {
   private IntUtils() {}

   public static int parsePositiveInt(String text) {
      var number = parseInt(text);
      if (number < 1)
         throw new IllegalArgumentException();
      return number;
   }

   public static int parseInt(String text) {
      try {
         return Integer.parseInt(text, 10);
      } catch (Exception e) {
         throw new IllegalArgumentException();
      }
   }
}
