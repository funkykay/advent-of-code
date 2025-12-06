if __name__ == "__main__":

    def is_repeated_pattern(n: int) -> bool:
        s = str(n)
        length = len(s)

        if length < 2:
            return False

        for size in range(1, length // 2 + 1):
            if length % size != 0:
                continue

            pattern = s[:size]
            repeats = length // size

            if pattern * repeats == s:
                return True

        return False

    def sum_invalid_ids_in_range(start: int, end: int) -> int:
        total = 0

        for current in range(start, end + 1):
            if is_repeated_pattern(current):
                total += current

        return total

    def parse_range(raw: str) -> tuple[int, int]:
        left, right = raw.split("-")
        return int(left), int(right)

    with open("2025/day02/input.txt", "r", encoding="utf-8") as f:
        total_sum = 0
        for part in f.read().strip().split(","):
            part = part.strip()
            if not part:
                continue

            total_sum += sum_invalid_ids_in_range(*parse_range(part))

    print(f"There are {total_sum} invalid ids.")
