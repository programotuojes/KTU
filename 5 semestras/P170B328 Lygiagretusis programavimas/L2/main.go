package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

var outputSize = 0
var doneSendingData = false
var doneSendingToWork = false

const inputSize = 100
const threadCount = inputSize / 4
const inputFile = "data/IFF-8-7_KlevinskasG_L2_dat_1.txt"
const outputFile = "data/IFF-8-7_KlevinskasG_L2_rez_1.txt"

type Car struct {
	mileage int
	economy float64
	model   string
}

type CarResult struct {
	car    Car
	result string
}

func getCarResult(car Car) CarResult {
	var result CarResult
	var stringSum int32

	for _, char := range car.model {
		stringSum += char
	}

	result.car = car
	result.result = strconv.FormatFloat(
		float64(car.mileage)*car.economy+float64(stringSum),
		'f',
		2,
		32,
	)

	return result
}

func readFile() [inputSize]Car {
	file, err := os.Open(inputFile)

	if err != nil {
		log.Fatal(err)
	}

	defer file.Close()

	scanner := bufio.NewScanner(file)

	var cars [inputSize]Car
	i := 0

	for scanner.Scan() {
		str := strings.Split(scanner.Text(), " ")

		cars[i].mileage, _ = strconv.Atoi(str[0])
		cars[i].economy, _ = strconv.ParseFloat(str[1], 64)
		cars[i].model = str[2]

		i++
	}

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}

	return cars
}

func writeFile(cars [inputSize]CarResult) {
	file, err := os.OpenFile(outputFile, os.O_CREATE|os.O_WRONLY|os.O_TRUNC, 0644)

	if err != nil {
		log.Fatal(err)
	}

	writer := bufio.NewWriter(file)

	if outputSize == 0 {
		_, _ = writer.WriteString("Table is empty\n")
		writer.Flush()
		file.Close()
		return
	}

	header := "+-----------+----------+-------------+------------------+\n"
	header += "|    ODO    |   Econ   |    Model    |    Calculated    |\n"
	header += "+-----------+----------+-------------+------------------+\n"

	_, _ = writer.WriteString(header)

	for _, data := range cars[:outputSize] {
		str := fmt.Sprintf(
			"| %9d | %8.1f | %-11s | %16s |\n",
			data.car.mileage,
			data.car.economy,
			data.car.model,
			data.result)

		_, _ = writer.WriteString(str)
	}

	_, _ = writer.WriteString("+-----------+----------+-------------+------------------+\n")

	writer.Flush()
	file.Close()
}

func dataThread(input <-chan Car, output chan<- Car, workRequest <-chan bool) {
	var cars [inputSize / 2]Car
	size := 0

	for {
		if size+1 < cap(cars) {
			select {
			case receivedCar := <-input:
				cars[size] = receivedCar
				size++
			default:
			}
		}

		if size > 0 {
			select {
			case <-workRequest:
				size--
				output <- cars[size]
			default:
			}
		} else {
			if doneSendingData {
				doneSendingToWork = true

				for i := 0; i < threadCount; i++ {
					<-workRequest
				}

				return
			}
		}
	}
}

func workThread(input <-chan Car, output chan<- CarResult, workRequest chan bool, done chan bool) {
	for {
		workRequest <- true

		select {
		case receivedCar := <-input:
			carResult := getCarResult(receivedCar)
			firstDigit := carResult.result[0] - '0'

			if firstDigit%2 == 0 {
				output <- carResult
			}
		default:
		}

		if doneSendingToWork {
			done <- true
			return
		}
	}
}

func resultThread(input <-chan CarResult, output chan<- [inputSize]CarResult, doneWriting chan bool) {
	var carsResult [inputSize]CarResult
	var threadsTerminated int

	for {
		select {
		case car := <-input:
			insertIndex := outputSize

			for i := 0; i < outputSize; i++ {
				newFloat, _ := strconv.ParseFloat(car.result, 64)
				arrFloat, _ := strconv.ParseFloat(carsResult[i].result, 64)

				if newFloat < arrFloat {
					insertIndex = i
					break
				}
			}

			for i := outputSize; i > insertIndex; i-- {
				carsResult[i] = carsResult[i-1]
			}

			carsResult[insertIndex] = car
			outputSize++

		case <-doneWriting:
			threadsTerminated++
		default:
		}

		if threadsTerminated == threadCount {
			output <- carsResult
			return
		}
	}
}

func main() {
	cars := readFile()

	dataInput := make(chan Car)
	dataOutput := make(chan Car)
	calculatedValue := make(chan CarResult)
	resultOutput := make(chan [inputSize]CarResult)
	workRequest := make(chan bool)
	doneWorking := make(chan bool)

	for i := 0; i < threadCount; i++ {
		go workThread(dataOutput, calculatedValue, workRequest, doneWorking)
	}

	go dataThread(dataInput, dataOutput, workRequest)
	go resultThread(calculatedValue, resultOutput, doneWorking)

	for _, car := range cars {
		dataInput <- car
	}
	doneSendingData = true

	writeFile(<-resultOutput)
}
