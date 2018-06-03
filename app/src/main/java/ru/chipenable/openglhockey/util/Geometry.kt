package ru.chipenable.openglhockey.util

/**
 * Created by Pavel.B on 03.06.2018.
 */
class Point(val x: Float, val y: Float, val z: Float){

    fun translateY(distance: Float) =
            Point(x, y + distance, z)

}

class Circle(val center: Point, val radius: Float){

    fun scale(scale: Float) =
            Circle(center, radius * scale)

}

class Cylinder(val center: Point, val radius: Float, val height: Float)