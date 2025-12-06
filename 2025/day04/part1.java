boolean isFieldRoll(char[][] map, int x, int y) {
    try {
        return map[x][y] == '@';
    } catch (Exception e) {
        return false;
    }
}

private boolean hasLessThan4AdjacentRolls(char[][] map, int x, int y) {
    int adjacentRolls = 0;
    if (isFieldRoll(map, x - 1, y - 1)) adjacentRolls++;
    if (isFieldRoll(map, x - 1, y)) adjacentRolls++;
    if (isFieldRoll(map, x - 1, y + 1)) adjacentRolls++;
    if (isFieldRoll(map, x, y - 1)) adjacentRolls++;
    if (isFieldRoll(map, x, y + 1)) adjacentRolls++;
    if (isFieldRoll(map, x + 1, y - 1)) adjacentRolls++;
    if (isFieldRoll(map, x + 1, y)) adjacentRolls++;
    if (isFieldRoll(map, x + 1, y + 1)) adjacentRolls++;
    return adjacentRolls < 4;
}

void main() {

    ArrayList<char[]> rows = new ArrayList<>();
    try (BufferedReader br = Files.newBufferedReader(Path.of("2025/day04/input.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            rows.add(line.toCharArray());
        }
    } catch (IOException e) {
        System.out.println("Fehler beim Lesen der Datei: " + e.getMessage());
        return;
    }
    char[][] map = rows.toArray(new char[0][]);

    int totalAccessibleRolls = 0;
    for (int rowIdx = 0; rowIdx < map.length; rowIdx++) {
        for (int colIdx = 0; colIdx < map[rowIdx].length; colIdx++) {
            if (isFieldRoll(map, rowIdx, colIdx) && hasLessThan4AdjacentRolls(map, rowIdx, colIdx)) {
                // System.out.println("found accessible at (" + rowIdx + ", " + colIdx + ")");
                totalAccessibleRolls++;
            }
        }
    }

    System.out.println("total accessible rolls: " + totalAccessibleRolls);
}
