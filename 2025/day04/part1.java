import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

void main() {
    try {
        List<String> lines = Files.readAllLines(Paths.get("2025/day04/input.txt"));
        for (String line : lines) {
            System.out.println("Gelesen: " + line);
        }
    } catch (IOException e) {
        System.out.println("Fehler beim Lesen der Datei: " + e.getMessage());
    }
}