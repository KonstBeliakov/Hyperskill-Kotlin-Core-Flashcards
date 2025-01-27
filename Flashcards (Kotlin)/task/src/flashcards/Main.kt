package flashcards

class Card(val term: String, val definition: String)

fun main() {
    val cards: MutableList<Card> = mutableListOf()

    println("Input the number of cards:")
    val cardNumber = readln().toInt()

    for (i in 1..cardNumber) {
        println("Card #$i")
        val term = readln()
        if (cards.any { it.term == term })
            println("The term \"$term\" already exists. Try again:")
        println("The definition for card #$i:")
        val defenition = readln()
        if (cards.any { it.definition == defenition })
            println("The definition \"$defenition\" already exists.")

        cards.add(Card(term, defenition))
    }

    for (card in cards) {
        println("Print the definition of \"${card.term}\":")
        val userDefenition = readln()
        if (userDefenition == card.definition)
            println("Correct!")
        else {
            val matchingCard = cards.find {it.definition == userDefenition}

            if(matchingCard != null){
                println("Wrong. The right answer is \"${card.definition}\", but your definition is correct for \"${matchingCard.definition}\"")
            }else{
                println("Wrong. The right answer is \"${card.definition}\".")
            }
        }
    }
}
