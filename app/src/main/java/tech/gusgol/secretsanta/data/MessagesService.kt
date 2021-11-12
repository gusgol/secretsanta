package tech.gusgol.secretsanta.data

import retrofit2.Response
import retrofit2.http.*

interface MessagesService {

    @FormUrlEncoded
    @POST("{account}/Messages.json")
    suspend fun send(@Path("account") account: String,
                     @Field("Body") body: String,
                     @Field("From") from: String,
                     @Field("To") to: String): Response<MessageResponse>

}