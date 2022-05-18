package com.linecorp.id.research.common.extension

import android.content.res.Resources
import com.linecorp.id.research.common.R
import java.lang.reflect.Field

fun Resources.getStringFromResourceName(resName: String): String? {
    val id = getResourceId(resName, R.string::class.java)
    if (id == -1) return null

    return getString(id)
}

private fun getResourceId(resName: String, c: Class<*>): Int {
    return try {
        val idField: Field = c.getDeclaredField(resName)
        idField.getInt(idField)
    } catch (e: Exception) {
        e.printStackTrace()
        -1
    }
}
