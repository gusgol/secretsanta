package tech.gusgol.secretsanta.data

data class Person(val name: String, val phone: String) {

    var secretSanta: Person? = null

    override fun toString(): String {
        return "[$name pegou ${secretSanta?.name}]"
    }

    override fun equals(other: Any?): Boolean {
        return name == (other as? Person)?.name
    }

}