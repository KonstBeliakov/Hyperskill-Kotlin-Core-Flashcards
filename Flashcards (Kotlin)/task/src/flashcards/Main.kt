package flashcards

import java.io.File
import kotlin.io.path.*

class Card(val term: String, val definition: String)

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
    for(card in cards){
        file.appendText("${card.term}: ${card.definition}\n")
    }
    println("${cards.size} cards have been saved.")
}

fun ask(cards: MutableList<Card>) {
    println("How many times to ask?")
    val timesAsk = readln().toInt()
    repeat(timesAsk){
        val card = cards.random()
        println("Print the definition of \"${card.term}\":")
        val userDefenition = readln()
        if (userDefenition == card.definition)
            println("Correct!")
        else {
            val matchingCard = cards.find { it.definition == userDefenition }

            if (matchingCard != null) {
                println("Wrong. The right answer is \"${card.definition}\", but your definition is correct for \"${matchingCard.definition}\"")
            } else {
                println("Wrong. The right answer is \"${card.definition}\".")
            }
        }
    }
}

fun main() {
    val cards: MutableList<Card> = mutableListOf()

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

            else -> println("Invalid command")
        }
    }
}
