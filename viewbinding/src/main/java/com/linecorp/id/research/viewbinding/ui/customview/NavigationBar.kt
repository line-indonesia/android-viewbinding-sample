package com.linecorp.id.research.viewbinding.ui.customview

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.linecorp.id.research.viewbinding.R
import com.linecorp.id.research.viewbinding.databinding.LayoutNavigationBinding

class NavigationBar(
    context: Context,
    attrs: AttributeSet?
) : RelativeLayout(context, attrs) {

    private val binding = LayoutNavigationBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private var backClickListener: OnClickListener? = null

    private var _titleGravity = TitleGravity.START
    fun titleGravity(): TitleGravity = _titleGravity

    var title: CharSequence
        get() = binding.textViewTitle.text
        set(value) {
            binding.textViewTitle.text = value
        }

    init {
        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar)
            try {
                //Background
                val background = attributes.getDrawable(
                    R.styleable.NavigationBar_background
                )
                background?.let {
                    binding.layoutBar.background = it
                }

                //Back tint
                val backTint = attributes.getColor(
                    R.styleable.NavigationBar_backTint, 0
                )
                if (backTint != 0) {
                    binding.imageButtonBack.colorFilter = PorterDuffColorFilter(
                        backTint, PorterDuff.Mode.SRC_IN
                    )
                }

                //Back src
                val backSrc = attributes.getDrawable(
                    R.styleable.NavigationBar_backSrc
                )
                backSrc?.let { drawable ->
                    binding.imageButtonBack.setImageDrawable(drawable)
                }

                //Title
                val titleText = attributes.getString(R.styleable.NavigationBar_title)
                titleText?.let {
                    title = it
                }

                //Title Color
                val titleColor = attributes.getColor(
                    R.styleable.NavigationBar_titleColor, 0
                )
                if (titleColor != 0) {
                    binding.textViewTitle.setTextColor(titleColor)
                }

                //Title Gravity
                _titleGravity = TitleGravity.fromInt(
                    attributes.getInteger(
                        R.styleable.NavigationBar_titleGravity,
                        TitleGravity.START.id
                    )
                )
                when (_titleGravity) {
                    TitleGravity.START -> binding.textViewTitle.gravity = Gravity.START or Gravity.CENTER_VERTICAL
                    TitleGravity.CENTER -> binding.textViewTitle.gravity = Gravity.CENTER
                    TitleGravity.END -> binding.textViewTitle.gravity = Gravity.END or Gravity.CENTER_VERTICAL
                }

                //Title size
                val titleSize = attributes.getDimensionPixelSize(R.styleable.NavigationBar_titleSize, 0)
                if (titleSize > 0) {
                    binding.textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
                }
            }
            finally {
                attributes.recycle()
            }
        }

        binding.imageButtonBack.setOnClickListener {
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
