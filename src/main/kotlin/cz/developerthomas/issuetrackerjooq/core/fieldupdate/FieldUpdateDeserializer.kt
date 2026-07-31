package cz.developerthomas.issuetrackerjooq.core.fieldupdate

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.ContextualDeserializer

class FieldUpdateDeserializer(
    private val valueType: JavaType? = null,
) : JsonDeserializer<FieldUpdate<*>>(), ContextualDeserializer {

    override fun createContextual(
        ctxt: DeserializationContext,
        property: BeanProperty,
    ): JsonDeserializer<*> {
        val type = property.type.containedType(0)
        return FieldUpdateDeserializer(type)
    }

    override fun deserialize(
        parser: JsonParser,
        ctxt: DeserializationContext,
    ): FieldUpdate<*> {
        val value = ctxt.readValue<Any?>(parser, valueType)
        return FieldUpdate.Value(value)
    }

    override fun getNullValue(ctxt: DeserializationContext): FieldUpdate<*> =
        FieldUpdate.Value(null)
}
