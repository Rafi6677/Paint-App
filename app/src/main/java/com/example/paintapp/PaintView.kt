package com.example.paintapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View

class PaintView: View {

    companion object {
        const val BRUSH_SIZE: Int = 10
        const val DEFAULT_COLOR = Color.RED
        const val DEFAULT_BACKGROUND_COLOR = Color.WHITE
        const val TOUCH_TOLERANCE = 4
    }

    private var pointX: Float = 0f
    private var pointY: Float = 0f
    private var path: Path ?= null
    private var paint: Paint = Paint()
    private var paths: MutableList<FingerPath> = mutableListOf<FingerPath>()

    private var currentColor: Int = 0
    private var currentBackgroundColor: Int = DEFAULT_BACKGROUND_COLOR
    private var strokeWidth: Int = 0
    private var emboss: Boolean = false
    private var blur: Boolean = false

    private var embossMaskFilter: MaskFilter ?= null
    private var blurMaskFilter: MaskFilter ?= null
    private var bitmap: Bitmap ?= null
    private var canvas: Canvas ?= null
    private var bitmapPaint: Paint = Paint(Paint.DITHER_FLAG)

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = DEFAULT_COLOR
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.xfermode = null
        paint.alpha = 0xff

        embossMaskFilter = EmbossMaskFilter(floatArrayOf(1f, 1f, 1f), 0.4f, 6f, 3.5f)
        blurMaskFilter = BlurMaskFilter(5f, BlurMaskFilter.Blur.NORMAL)
    }

    fun init(metrics: DisplayMetrics) {
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap!!)

        currentColor = DEFAULT_COLOR
        strokeWidth = BRUSH_SIZE
    }

    fun normal() {
        emboss = false
        blur = false
    }

    fun emboss() {
        emboss = true
        blur = false
    }

    fun blur() {
        emboss = false
        blur = true
    }

    fun clear() {
        currentBackgroundColor = DEFAULT_BACKGROUND_COLOR
        paths.clear()
        normal()
        invalidate()
    }

    fun changeColor(color: Int) {
        currentColor = color
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.save()

        this.canvas!!.drawColor(currentBackgroundColor)

        for(fingerPath : FingerPath in paths) {
            this.paint.color = fingerPath.color
            this.paint.strokeWidth = fingerPath.strokeWidth.toFloat()
            this.paint.maskFilter = null

            if (fingerPath.emboss) {
                this.paint.maskFilter = this.embossMaskFilter
            } else if (fingerPath.blur) {
                this.paint.maskFilter = this.blurMaskFilter
            }

            this.canvas!!.drawPath(fingerPath.path!!, this.paint)
        }

        //canvas.drawBitmap(this.bitmap, 0, 0, this.bitmapPaint)
        canvas.drawBitmap(this.bitmap!!, 0f, 0f, this.bitmapPaint)
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x: Float = event!!.getX()
        val y: Float = event!!.getY()

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }

        return true
    }

    private fun touchStart(x: Float, y: Float) {
        path = Path()
        val fingerPath = FingerPath(currentColor, emboss, blur, strokeWidth, path!!)

        paths.add(fingerPath)

        path!!.reset()
        path!!.moveTo(x, y)
        pointX = x
        pointY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx: Float = Math.abs(x - pointX)
        val dy: Float = Math.abs(y - pointY)

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path!!.quadTo(pointX, pointY, (x + pointX) / 2, (y + pointY) / 2)
            pointX = x
            pointY = y
        }
    }

    private fun touchUp() {
        path!!.lineTo(pointX, pointY)
    }
}