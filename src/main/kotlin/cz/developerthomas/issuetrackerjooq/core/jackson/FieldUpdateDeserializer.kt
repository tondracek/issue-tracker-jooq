package cz.developerthomas.issuetrackerjooq.core.jackson


import cz.developerthomas.issuetrackerjooq.core.fieldupdate.FieldUpdate
import tools.jackson.core.JsonParser
import tools.jackson.databind.BeanProperty
import tools.jackson.databind.DeserializationContext
import tools.jackson.databind.JavaType
import tools.jackson.databind.ValueDeserializer
import tools.jackson.databind.deser.std.StdDeserializer

class FieldUpdateDeserializer(
    private val valueType: JavaType? = null,
) : StdDeserializer<FieldUpdate<*>>(FieldUpdate::class.java) {

    override fun createContextual(
        ctxt: DeserializationContext?,
        property: BeanProperty?
    ): ValueDeserializer<*> {
        val valueType = property?.type?.containedType(0)
        return FieldUpdateDeserializer(valueType)
    }

    override fun deserialize(
        parser: JsonParser,
        ctxt: DeserializationContext,
    ): FieldUpdate<*> {

        val valueDeserializer =
            ctxt.findContextualValueDeserializer(valueType!!, null)

        return FieldUpdate.Value(
            valueDeserializer.deserialize(parser, ctxt)
        )
    }

    override fun getNullValue(ctxt: DeserializationContext?) =
        FieldUpdate.Value(null)
}