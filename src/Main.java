import java.util.*;

public class Main {
    // Заведите мапу в статическом поле public static final Map<Integer, Integer> sizeToFreq.
    public static final Map<Integer, Integer> sizeToFreq = new TreeMap<>();
    // Количество потоков равно количеству генерируемых маршрутов и равно 1000.
    public static final int numberOfRoutes = 1000;
    // Какой символ будем считать в функции countSymbol(). В условии задачи это 'R'.
    public static final char symbolToFind = 'R';

    public static void main(String[] args) {
        // Количество потоков равно количеству генерируемых маршрутов и равно 1000.
        for (int i = 0; i < numberOfRoutes; i++) {
            int currentThreadNumber = i;
            new Thread(() -> {
                // В каждом потоке генерирует текст
                String s = generateRoute("RLRFR", 100);
                // считает количество команд поворота направо (буквы 'R')
                int symbolRepeats = countSymbol(s, symbolToFind);
                synchronized (sizeToFreq) {
                    // кладет значения в sizeToFreq.
                    sizeToFreq.put(currentThreadNumber, symbolRepeats);
                    // выводит на экран результат.
                    System.out.println("В потоке " + currentThreadNumber +
                            " символ \'" + symbolToFind + "\' повторяется " + symbolRepeats + " раз");
                }
            }).start();
        }
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
