package cz.developerthomas.issuetrackerjooq.sample

import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Table
import org.jooq.TableField

fun <R : Record> DSLContext.upsertSample(
    record: R,
    table: Table<R>,
    idField: TableField<R, *>
) = insertInto(table)
    .set(record)
    .onConflict(idField).doUpdate()
    .set(record)
    .execute()