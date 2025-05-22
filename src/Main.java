import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String filePath = "word_dataset.csv";
        List<String> lines = readFile(filePath);
        if (lines == null) return;

        List<String> words = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            for (String word : parts) {
                if (!word.isBlank()) {
                    words.add(word.toLowerCase());
                }
            }
        }

        Set<String> uniqueWords = new HashSet<>(words);
        Map<String, Integer> frequency = new HashMap<>();
        Map<Integer, Integer> rowGroups = new HashMap<>();

        for (String word : words) {
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        for (String line : lines) {
            int length = line.split(",").length;
            rowGroups.put(length, rowGroups.getOrDefault(length, 0) + 1);
        }

        String mostFrequentWord = frequency.isEmpty() ? "Brak słów w pliku" :
                Collections.max(frequency.entrySet(), Map.Entry.comparingByValue()).getKey();

        System.out.println("Unikalne słowa: " + uniqueWords.size());
        System.out.println("Całkowita liczba słów: " + words.size());

        System.out.println("\nCzęstotliwość słów:");
        frequency.forEach((word, count) -> System.out.println(" Liczba słów \"" + word + "\": " + count));

        System.out.println("\nGrupowanie po długości wierszy:");
        rowGroups.forEach((length, count) ->
                System.out.println("    Wiersze o długości " + length + " słów: " + count));

        System.out.println("\nNajczęściej występujące słowo: \"" + mostFrequentWord + "\"");
    }

    private static List<String> readFile(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Nie można odczytać pliku: " + filePath);
            return null;
        }
    }
}
