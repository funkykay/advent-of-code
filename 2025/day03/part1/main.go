package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

const DEBUG = true

func main() {
	file, err := os.Open("2025/day03/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer func(file *os.File) {
		err := file.Close()
		if err != nil {

		}
	}(file)

	scanner := bufio.NewScanner(file)

	totalJoltage := 0
	for scanner.Scan() {

		batteryBank := scanner.Text()
		joltage := maxJoltageFromBank(batteryBank)

		if DEBUG {
			fmt.Println(joltage)
		}

		totalJoltage += joltage
	}

	fmt.Println("Total output joltage:", totalJoltage)

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}

func maxJoltageFromBank(bank string) int {
	batteries := []rune(bank)

	if len(batteries) < 2 {
		panic(fmt.Sprintf("Bank zu klein: %q", bank))
	}

	bestJoltage := -1

	for first := 0; first < len(batteries); first++ {
		a := int(batteries[first] - '0')
		for second := first + 1; second < len(batteries); second++ {
			b := int(batteries[second] - '0')
			joltage := 10*a + b
			if joltage > bestJoltage {
				bestJoltage = joltage
			}
		}
	}

	if DEBUG {
		fmt.Println("Bank:  ", bank)
		fmt.Println("Jolts: ", bestJoltage)
	}

	return bestJoltage
}
