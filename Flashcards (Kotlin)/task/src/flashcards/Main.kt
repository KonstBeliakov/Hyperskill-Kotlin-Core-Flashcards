package flashcards

class Card(val term: String, val defenition: String)

fun main() {
    val cards: MutableList<Card> = mutableListOf()

    println("Input the number of cards:")
    val cardNumber = readln().toInt()

    for (i in 1..cardNumber) {
        println("Card #$i")
        val term = readln()
        println("The definition for card #$i:")
        val defenition = readln()

        cards.add(Card(term, defenition))
    }

    for(card in cards){
        println("Print the definition of \"${card.term}\":")
        val userDefenition = readln()
        if(userDefenition == card.defenition)
            println("Correct!")
        else
            println("Wrong. The right answer is \"${card.defenition}\".")
    }
}
