package tech.gusgol.secretsanta.data

interface MessagesDataSource {
    suspend fun sendMessage(messageRequest: MessageRequest): String
}