package tech.gusgol.secretsanta.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.gusgol.secretsanta.Graph
import tech.gusgol.secretsanta.data.MessageRequest
import tech.gusgol.secretsanta.data.MessagesRepository
import tech.gusgol.secretsanta.data.Person

class HomeViewModel(private val repository: MessagesRepository = Graph.repository) : ViewModel() {

    companion object {
        const val FROM = "+12055397242"
    }

    private val list = mutableListOf(
        Person("Gustavo", "+5551992732182"),
        Person("Ana Paula", "+5551992564827"),
        Person("Marielle", "+5551997911587"),
        Person("Marcel", "+5551997915357"))

    fun shuffle(): String {
        val shuffled = list.shuffled()
        list.forEachIndexed { index, person ->
            val secretSanta = shuffled[index]
            if (person == secretSanta) {
                Log.e("HomeViewModel", "Starting over...")
                return shuffle()
            } else {
                person.secretSanta = shuffled[index]
            }
        }
        list.forEach { person ->
            send(person)
        }
        return list.shuffled().toString()
    }

    private fun send(person: Person) {
        val message = MessageRequest(
            from = FROM,
            body = "Olá ${person.name}, seu amigo secreto da família Goldhardt é: ${person.secretSanta?.name}",
            to = person.phone
        )
        viewModelScope.launch {
            val response = repository.send(message)
            Log.e("HomeViewModel", response)
        }
    }
}