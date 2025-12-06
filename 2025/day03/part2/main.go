package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

const DEBUG = true
const PICK = 12

func main() {
	file, err := os.Open("2025/day03/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	var totalJoltage int64 = 0
	for scanner.Scan() {
		batteryBank := scanner.Text()
		joltage := maxJoltageFromBank(batteryBank, PICK)

		if DEBUG {
			fmt.Println("Bank:", batteryBank, "=>", joltage)
		}

		totalJoltage += joltage
	}

	fmt.Println("Total output joltage:", totalJoltage)

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}

func maxJoltageFromBank(bank string, k int) int64 {
	batteries := []rune(bank)

	if len(batteries) < k {
		panic(fmt.Sprintf("Bank zu klein für %d Batterien: %q", k, bank))
	}

	digits := make([]int, len(batteries))
	for i, r := range batteries {
		digits[i] = int(r - '0')
	}

	toDelete := len(digits) - k
	stack := make([]int, 0, len(digits))

	for _, d := range digits {
		for toDelete > 0 && len(stack) > 0 && stack[len(stack)-1] < d {
			stack = stack[:len(stack)-1]
			toDelete--
		}
		stack = append(stack, d)
	}

	if toDelete > 0 {
		stack = stack[:len(stack)-toDelete]
	}

	if len(stack) < k {
		panic(fmt.Sprintf("Interner Fehler: len(stack)=%d, k=%d", len(stack), k))
	}
	stack = stack[:k]

	var joltage int64 = 0
	for _, d := range stack {
		joltage = joltage*10 + int64(d)
	}

	if DEBUG {
		fmt.Println("Bank: ", bank)
		fmt.Println("Pick: ", k, " -> digits:", stack, " -> joltage:", joltage)
	}

	return joltage
}
