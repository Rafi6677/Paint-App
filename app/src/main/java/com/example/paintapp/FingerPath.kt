package com.example.paintapp

class FingerPath {

    var color: Int = 0
    var emboss: Boolean = false
    var blur: Boolean = false
    var strokeWidth: Int = 0
    var path: android.graphics.Path ?= null

    constructor(color: Int, emboss: Boolean, blur: Boolean, strokeWidth: Int, path: android.graphics.Path) {
        this.color = color
        this.emboss = emboss
        this.blur = blur
        this.strokeWidth = strokeWidth
        this.path = path
    }
}