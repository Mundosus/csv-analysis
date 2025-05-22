import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private static final String TEST_FILE_PATH = "test_dataset.csv";

    @Test
    public void testCSVProcessing() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE_PATH));
        assertFalse(lines.isEmpty(), "Plik testowy nie powinien być pusty");

        List<String> allWords = new ArrayList<>();
        Set<String> uniqueWords = new HashSet<>();
        Map<String, Integer> wordCount = new HashMap<>();
        Map<Integer, Integer> rowLengthMap = new HashMap<>();

        for (String line : lines) {
            String[] words = line.split(",");
            int count = words.length;
            for (String word : words) {
                if (!word.isBlank()) {
                    word = word.toLowerCase();
                    allWords.add(word);
                    uniqueWords.add(word);
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
            rowLengthMap.put(count, rowLengthMap.getOrDefault(count, 0) + 1);
        }

        String mostFrequentWord = wordCount.isEmpty() ? "Brak słów w pliku" :
                Collections.max(wordCount.entrySet(), Map.Entry.comparingByValue()).getKey();

        System.out.println("\n Testowe unikalne słowa: " + uniqueWords.size());
        System.out.println(" Testowa całkowita liczba słów: " + allWords.size());

        System.out.println("\n Testowa częstotliwość słów:");
        wordCount.forEach((word, count) -> System.out.println("Liczba słów \"" + word + "\": " + count));

        System.out.println("\n Testowe grupowanie po długości wierszy:");
        rowLengthMap.forEach((length, count) ->
                System.out.println(" Wiersze o długości " + length + " słów: " + count));

        System.out.println("\n Najczęściej występujące słowo w testowym pliku: \"" + mostFrequentWord + "\"");

        assertEquals(lines.size(), rowLengthMap.values().stream().mapToInt(i -> i).sum(), "Liczba linii powinna się zgadzać");
        assertEquals(uniqueWords.size(), new HashSet<>(allWords).size(), "Unikalne słowa powinny się zgadzać");
        assertEquals(allWords.size(), wordCount.values().stream().mapToInt(i -> i).sum(), "Liczba słów powinna się zgadzać");
        assertFalse(mostFrequentWord.isBlank(), "Najczęściej występujące słowo nie powinno być puste");
    }
}
