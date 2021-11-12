package tech.gusgol.secretsanta.data

data class MessageRequest(
    val body: String,
    val from: String,
    val to: String
) {
    override fun toString(): String {
        return "[ from=$from, body=$body, to=$to ]"
    }
}