package util;

import java.util.*;

public class MultiMap<K, V> {
   private final Map<K, List<V>> data = new HashMap<>();

   public boolean isEmpty() {
      return size() == 0;
   }

   public int size() {
      return data.size();
   }

   public int valuesSize() {
      return values().size();
   }

   public void put(K key, V value) {
      data.computeIfAbsent(Objects.requireNonNull(key), k -> new ArrayList<>()).add(value);
   }

   public List<V> get(K key) {
      return data.get(key);
   }

   public Collection<V> values() {
       return data.values().stream()
          .flatMap(Collection::stream)
          .toList();
   }

   public void clear() {
      data.clear();
   }
}
