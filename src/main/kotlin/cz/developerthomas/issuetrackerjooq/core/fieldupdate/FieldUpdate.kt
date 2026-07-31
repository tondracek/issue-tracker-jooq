package cz.developerthomas.issuetrackerjooq.core.fieldupdate

sealed interface FieldUpdate<out T> {

    data class Value<T>(val value: T) : FieldUpdate<T>

    data object Undefined : FieldUpdate<Nothing>


    fun <R> map(transform: (T) -> R): FieldUpdate<R> = when (this) {
        is Value -> Value(transform(this.value))
        Undefined -> Undefined
    }

    fun <R> fold(onValue: (T) -> R, onUndefined: () -> R): R = when (this) {
        is Value -> onValue(this.value)
        Undefined -> onUndefined()
    }
}

inline fun <T> FieldUpdate<T>.onValue(block: (T) -> Unit) = when (this) {
    is FieldUpdate.Value<T> -> block(this.value)
    FieldUpdate.Undefined -> Unit
}

inline fun <T> FieldUpdate<T?>.onValueNotNull(block: (T) -> Unit) {
    if (this is FieldUpdate.Value<T?> && this.value != null)
        block(this.value)
}
