package tech.gusgol.secretsanta.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.gusgol.secretsanta.Twillio
import tech.gusgol.secretsanta.data.MessageRequest
import tech.gusgol.secretsanta.data.MessagesDataSource
import tech.gusgol.secretsanta.data.MessagesService

class MessagesRemoteDataSource(private val messagesService: MessagesService,
                               private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : MessagesDataSource {
    override suspend fun sendMessage(messageRequest: MessageRequest): String = withContext(ioDispatcher){
        val response = messagesService.send(
            account = Twillio.ACCOUNT_SID,
            body = messageRequest.body,
            from = messageRequest.from,
            to = messageRequest.to)
        return@withContext response.body()?.status ?: "No response body"
    }
}