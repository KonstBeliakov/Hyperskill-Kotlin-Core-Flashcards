package flashcards

import java.io.File
import kotlin.io.path.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.io.InputStream


class Card(val term: String, val definition: String) {
    var mistakes = 0
}

fun addCard(cards: MutableList<Card>) {
    println("The card:")
    val term = readln()
    println("The definition of the card:")
    val definition = readln()
    cards.add(Card(term, definition))
    println("The pair (\"$term\":\"$definition\") has been added")
}

fun removeCard(cards: MutableList<Card>) {
    println("Which card?")
    val term = readln()
    val card = cards.firstOrNull({ it.term == term })
    if (card != null) {
        cards.remove(card)
        println("The card has been removed.")
    } else
        println("Can't remove \"$term\": there is no such card.")
}

fun importCard(cards: MutableList<Card>) {
    println("File name:")
    val filename = readln()
    val textFile = Path(filename)

    if (!textFile.exists()) {
        println("File not found.")
    } else {
        var cardsAdded = 0
        textFile.forEachLine {
            val (term, definition) = it.split(":")
            cards.add(Card(term, definition))
            cardsAdded++
        }
        println("$cardsAdded cards have been loaded.")
    }

}

fun exportCard(cards: MutableList<Card>) {
    println("File name:")
    val filename = readln()
    val file = File(filename)
    for (card in cards) {
        file.appendText("${card.term}: ${card.definition}\n")
    }
    println("${cards.size} cards have been saved.")
}

fun ask(cards: MutableList<Card>) {
    println("How many times to ask?")
    val timesAsk = readln().toInt()
    repeat(timesAsk) {
        val card = cards.random()
        println("Print the definition of \"${card.term}\":")
        val userDefenition = readln()
        if (userDefenition == card.definition)
            println("Correct!")
        else {
            card.mistakes++

            val matchingCard = cards.find { it.definition == userDefenition }

            if (matchingCard != null) {
                println("Wrong. The right answer is \"${card.definition}\", but your definition is correct for \"${matchingCard.term}\"")
            } else {
                println("Wrong. The right answer is \"${card.definition}\".")
            }
        }
    }
}

@Suppress("unused")
fun startLog(logBuffer: StringBuilder){
    val originalOut = System.out
    val capturingOut = PrintStream(object : ByteArrayOutputStream() {
        override fun write(b: Int) {
            logBuffer.append(b.toChar())
            originalOut.write(b)
        }
    })
    System.setOut(capturingOut)

    val originalIn = System.`in`
    val customIn = object : InputStream() {
        override fun read(): Int {
            val char = originalIn.read()
            if (char != -1) {
                logBuffer.append(char.toChar())
            }
            return char
        }
    }
    System.setIn(customIn)
}

fun main() {
    val cards: MutableList<Card> = mutableListOf()

    val logBuffer = StringBuilder()
    //startLog(logBuffer)

    var exit = false
    while (!exit) {
        println("Input the action (add, remove, import, export, ask, exit):")
        val command = readln()
        when (command) {
            "add" -> addCard(cards)
            "remove" -> removeCard(cards)
            "import" -> importCard(cards)
            "export" -> exportCard(cards)
            "ask" -> ask(cards)
            "exit" -> {
                println("Buy bye!")
                exit = true
            }

            "log" -> {
                println("File name:")
                val filename = readln()
                val file = File(filename)
                file.writeText(logBuffer.toString())
                println("The log has been saved.")
            }

            "hardest card" -> {
                val maxMistakes = cards.maxOf { it.mistakes }

                if (maxMistakes == 0)
                    println("There are no cards with errors.")
                else {
                    val matchingCards = cards.filter { it.mistakes == maxMistakes }
                    if (matchingCards.size == 1)
                        println("The hardest card is \"${matchingCards[0].term}\". You have ${maxMistakes} errors answering it.")
                    else
                        println("The hardest cards are \"${matchingCards.joinToString("\", \"")}\"")
                }
            }

            "reset stats" -> {
                for (card in cards)
                    card.mistakes = 0
            }

            else -> println("Invalid command")
        }
    }
}
