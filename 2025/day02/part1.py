if __name__ == "__main__":

    def sum_palindromes_in_range(start: int, end: int) -> int:
        total = 0
        for current in range(start, end + 1):
            s = str(current)
            mid = len(s) // 2

            if s[:mid] == s[mid:]:
                total += current

        return total

    def parse_range(raw: str) -> tuple[int, int]:
        left, right = raw.split("-")
        return int(left), int(right)

    with open("input.txt", "r", encoding="utf-8") as f:
        total_sum = 0
        for part in f.read().strip().split(","):
            part = part.strip()
            if not part:
                continue

            total_sum += sum_palindromes_in_range(*parse_range(part))


    print(f"There are {total_sum} invalid ids.")
