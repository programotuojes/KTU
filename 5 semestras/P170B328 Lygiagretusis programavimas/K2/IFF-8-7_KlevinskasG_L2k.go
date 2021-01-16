package main

import "fmt"

func calc(input chan int, collectorInput chan int, mainOutput chan int, done chan bool) {
	for {
		select {
		case num := <-input:
			amount := 0

			for i := 2; i < num; i++ {
				if num%i == 0 {
					amount++
				}
			}

			if amount == 4 {
				collectorInput <- num
			}

			if amount == 3 {
				mainOutput <- num
			} else {
				mainOutput <- -1
			}
		case <-done:
			return
		}
	}
}

func collector(input chan int, output chan int, done chan bool) {
	amount := 0

	for {
		select {
		case <-input:
			amount++
		case <-done:
			output <- amount
			return
		}
	}
}

func main() {
	calcInput := make(chan int)
	mainOutput := make(chan int)

	collectorInput := make(chan int)
	collectorOutput := make(chan int)

	calcDone := make(chan bool)
	collectorDone := make(chan bool)

	for i := 0; i < 5; i++ {
		go calc(calcInput, collectorInput, mainOutput, calcDone)
	}
	go collector(collectorInput, collectorOutput, collectorDone)

	var result = 0

	for i := 15000; true; i += 5 {
		for j := 0; j < 5; j++ {
			calcInput <- i + j
		}

		for j := 0; j < 5; j++ {
			resp := <- mainOutput

			if resp > 0 {
				result = resp
				break
			}
		}

		if result != 0 {
			break
		}
	}

	calcDone <- true
	collectorDone <- true

	collectorAmount := <-collectorOutput

	fmt.Println("Result:", result)
	fmt.Println("Amount:", collectorAmount)
}
