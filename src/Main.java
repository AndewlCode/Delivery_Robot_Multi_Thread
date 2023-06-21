import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    // Заведите мапу в статическом поле public static final Map<Integer, Integer> sizeToFreq
    // которая после завершения потоков должна хранить в ключах попавшиеся частоты буквы 'R',
    // а в значениях — количество раз их появления.
    public static final Map<Integer, Integer> sizeToFreq = new ConcurrentHashMap<>();
    // Количество потоков равно количеству генерируемых маршрутов и равно 1000.
    public static final int numberOfRoutes = 1000;
    // Какой символ будем считать в функции countSymbol(). В условии задачи это 'R'.
    public static final char symbolToFind = 'R';

    public static void main(String[] args) {
        // Количество потоков равно количеству генерируемых маршрутов и равно 1000.
        for (int i = 0; i < numberOfRoutes; i++) {
            new Thread(() -> {
                // В каждом потоке генерирует текст
                String s = generateRoute("RLRFR", 100);
                // считает количество команд поворота направо (буквы 'R')
                int symbolRepeats = countSymbol(s, symbolToFind);
                synchronized (sizeToFreq) {
                    // кладет значения в sizeToFreq.
                    if (sizeToFreq.containsKey(symbolRepeats)) {
                        int currentValue = sizeToFreq.get(symbolRepeats);
                        sizeToFreq.put(symbolRepeats, currentValue + 1);
                    } else {
                        sizeToFreq.put(symbolRepeats, 1);
                    }
                    // выводит на экран результат.
                    System.out.println("В потоке " + Thread.currentThread().getId() +
                            " символ '" + symbolToFind + "' повторяется " + symbolRepeats + " раз");
                }
            }).start();
        }

        // Получаем значение Key в нашей Map для наибольшего значения Value
        int maxKey = Collections.max(sizeToFreq.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Получаем Value из Key
        int maxValue = sizeToFreq.get(maxKey);

        System.out.println("Самое частое количество повторений " + maxKey + " (встретилось" + maxValue + " раз)");

        // Удаляем максимальное значение по ключу maxKey
        sizeToFreq.remove(maxKey);

        // Печатаем остальное содержимое Map
        System.out.println("Другие размеры:");
        sizeToFreq.forEach((key, value) -> System.out.println(key + " " + value + " раз"));
    }

    // В процессе построения карты маршрутов вам поручили проанализировать разнообразие существующих путей.
    // Для генерации маршрутов вы используете функцию:
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countSymbol(String str, char symbol) {
        return (int) str.chars()
                .filter(x -> x == symbol)
                .count();
    }
}