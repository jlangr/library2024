package domain.core;

import java.util.*;
import javax.annotation.Nonnull;

public class HoldingMap implements Iterable<Holding> {
   private final Map<String, Holding> holdings = new HashMap<>();

   public boolean isEmpty() {
      return 0 == size();
   }

   public int size() {
      return holdings.size();
   }

   public void add(Holding holding) {
      holdings.put(holding.getBarcode(), holding);
   }

   public Holding get(String barCode) {
      return holdings.get(barCode);
   }

   public boolean contains(Holding holding) {
      return holdings.containsKey(holding.getBarcode());
   }

   public List<Holding> holdings() {
      return new ArrayList<>(holdings.values());
   }

   public void remove(Holding holding) {
      holdings.remove(holding.getBarcode());
   }

   @Override
   @Nonnull
   public Iterator<Holding> iterator() {
      return holdings.values().iterator();
   }
}
