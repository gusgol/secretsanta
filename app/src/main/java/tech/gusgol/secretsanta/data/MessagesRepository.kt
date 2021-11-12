package tech.gusgol.secretsanta.data

class MessagesRepository(private val messagesDataSource: MessagesDataSource) {
    suspend fun send(messageRequest: MessageRequest): String = messagesDataSource.sendMessage(messageRequest)
}