import kotlin.math.min

fun main(args: Array<String>) {
    var randomNumbers = getRandomNumber() // Gets the random number from the function
    var n = 0
    var m = 0

    println("Guess the random generated four digit number:")

    do {
        // Sets n and m to 0
        n = 0
        m = 0

        println("User Input: ")
        val digits = readLine()?.toCharArray() // Reads the user input

        val inputCheckDuplicates = digits?.distinct() // Checks for duplicates

        for (number in inputCheckDuplicates.toString()) {  // Checks digits guessed correctly regardless of position

            n += if (randomNumbers.contains(number)) {
                1
            } else {
                0
            }

        }
        // Check digits guessed correctly at their correct position
        for (i in 0 until min(digits!!.size, randomNumbers.length)){
            m += if (digits[i] == randomNumbers[i]) {
                1
            } else {
                0
            }
        }

        println("Output: $n:$m ")
        println("Actual number to guess: $randomNumbers")

    } while (m < 4) // While the number hasn't been guessed correctly keep looping

    println("Congratulations you guessed the correct number: $randomNumbers")
}

fun getRandomNumber() : String {
    val numbers = IntArray(10) // Makes a new array for the numbers

    for (i in numbers.indices) {
        numbers[i] = i  // Fills the Array with numbers from 1 to 10. So numbers[6] is 6
    }

    numbers.shuffle() // Reshuffles the array to have a random number with 4 digits

    // Returns the random 4 digit number
    return numbers[0].toString() + numbers[1].toString() + numbers[2].toString() + numbers[3].toString()
}

