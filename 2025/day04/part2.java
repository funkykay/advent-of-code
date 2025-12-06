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

List<int[]> findAccessibleRolls(char[][] map) {
    List<int[]> list = new ArrayList<>();
    for (int rowIdx = 0; rowIdx < map.length; rowIdx++) {
        for (int colIdx = 0; colIdx < map[rowIdx].length; colIdx++) {
            if (isFieldRoll(map, rowIdx, colIdx) && hasLessThan4AdjacentRolls(map, rowIdx, colIdx)) {
                list.add(new int[]{rowIdx, colIdx});
            }
        }
    }
    return list;
}

private void removeRolls(char[][] map, List<int[]> rolls) {
    for (int[] rollToRemove : rolls) {
        map[rollToRemove[0]][rollToRemove[1]] = '.';
    }
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

    int totalRemovedRolls = 0;

    for (int i = 0; ; i++) {
        List<int[]> accessibleRolls = findAccessibleRolls(map);
        if (accessibleRolls.isEmpty()) {
            System.out.println("Totally removed " + totalRemovedRolls + " rolls in " + i + " iterations.");
            return;
        }
        totalRemovedRolls += accessibleRolls.size();
        removeRolls(map, accessibleRolls);
    }
}
