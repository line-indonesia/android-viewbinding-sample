package com.linecorp.id.research.findviewbyid.ui.customview

enum class TitleGravity(value: Int) {
    START(0),
    CENTER(1),
    END(2);

    val id: Int = value

    companion object {
        fun fromInt(id: Int): TitleGravity {
            for (f in TitleGravity.values()) {
                if (f.id == id) return f
            }
            return START
        }
    }
}
