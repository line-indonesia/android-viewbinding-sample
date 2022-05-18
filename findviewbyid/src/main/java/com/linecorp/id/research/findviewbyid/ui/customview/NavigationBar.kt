package com.linecorp.id.research.findviewbyid.ui.customview

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.linecorp.id.research.findviewbyid.R

class NavigationBar(
    context: Context,
    attrs: AttributeSet?
) : RelativeLayout(context, attrs) {

    private var backClickListener: OnClickListener? = null

    private var _titleGravity = TitleGravity.START
    fun titleGravity(): TitleGravity = _titleGravity

    var title: CharSequence
        get() = findViewById<TextView>(R.id.textViewTitle).text
        set(value) {
            findViewById<TextView>(R.id.textViewTitle).text = value
        }

    init {
        inflate(context, R.layout.layout_navigation, this)

        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar)
            try {
                //Background
                val background = attributes.getDrawable(
                    R.styleable.NavigationBar_background
                )
                background?.let {
                    findViewById<RelativeLayout>(R.id.layoutBar).background = it
                }

                //Back tint
                val backTint = attributes.getColor(
                    R.styleable.NavigationBar_backTint, 0
                )
                if (backTint != 0) {
                    findViewById<ImageButton>(R.id.imageButtonBack).colorFilter = PorterDuffColorFilter(
                        backTint, PorterDuff.Mode.SRC_IN
                    )
                }

                //Back src
                val backSrc = attributes.getDrawable(
                    R.styleable.NavigationBar_backSrc
                )
                backSrc?.let { drawable ->
                    findViewById<ImageButton>(R.id.imageButtonBack).setImageDrawable(drawable)
                }

                //Title
                val titleText = attributes.getString(R.styleable.NavigationBar_title)
                titleText?.let {
                    title = it
                }

                val textViewTitle = findViewById<TextView>(R.id.textViewTitle)

                //Title Color
                val titleColor = attributes.getColor(
                    R.styleable.NavigationBar_titleColor, 0
                )
                if (titleColor != 0) {
                    textViewTitle.setTextColor(titleColor)
                }

                //Title Gravity
                _titleGravity = TitleGravity.fromInt(
                    attributes.getInteger(
                        R.styleable.NavigationBar_titleGravity,
                        TitleGravity.START.id
                    )
                )
                when (_titleGravity) {
                    TitleGravity.START -> textViewTitle.gravity = Gravity.START or Gravity.CENTER_VERTICAL
                    TitleGravity.CENTER -> textViewTitle.gravity = Gravity.CENTER
                    TitleGravity.END -> textViewTitle.gravity = Gravity.END or Gravity.CENTER_VERTICAL
                }

                //Title size
                val titleSize = attributes.getDimensionPixelSize(R.styleable.NavigationBar_titleSize, 0)
                if (titleSize > 0) {
                    textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
                }
            }
            finally {
                attributes.recycle()
            }
        }

        findViewById<ImageButton>(R.id.imageButtonBack).setOnClickListener {
            if (backClickListener == null) {
                if (context is Activity) {
                    context.finish()
                }
            }
            else {
                backClickListener?.onClick(it)
            }
        }
    }

    fun setTitleText(text: String) {
        title = text
    }

    fun setOnBackClickListener(listener: OnClickListener) {
        backClickListener = listener
    }
}
