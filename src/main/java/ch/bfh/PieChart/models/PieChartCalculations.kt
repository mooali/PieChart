package ch.bfh.PieChart.models

import ch.bfh.matrix.Matrix
import java.sql.SQLException
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

const val MITTELPUNKT = 1200.0
const val RADIUS = 1000.0
const val UMFANG = 2 * RADIUS * PI


data class Product(val id: Int) {
    var valu: Int = 0
    var startAngle: Double = 0.0
    var endAngle: Double = 0.0
    var matrix: Matrix = Matrix(3, 2)
    val grad = UMFANG * (endAngle - startAngle)
}


fun main() {
    val sqLoperations = SQLoperations()
    val sqlDatabaseConnection = SQLDatabaseConnection();

    var prodList = List<Product>(6) { Product(it + 1) }


    //*********************************start and end Angle calculation


    prodList[0].startAngle = 0.0
    var totVal = 0


    for (i in 1..prodList.size) {
        prodList[i - 1].valu = sqLoperations.getValue(i)
        totVal += sqLoperations.getValue(i)
    }

    for (i in 1..prodList.size) {
        var totAngle = 0.0
        for (j in 1..i) {
            totAngle += (UMFANG / totVal) * prodList[i - 1].valu
        }
        prodList[i - 1].endAngle = totAngle
        if (prodList.size > i) {
            prodList[i].startAngle = totAngle
        }
    }


    //************************************** triangle connection points calculations

    val x1 = sin(prodList[0].grad) * RADIUS
    val y1 = (sqrt(RADIUS) - sqrt(x1)).pow(0.5)

    prodList[0].matrix =Matrix(arrayOf<DoubleArray>(
            doubleArrayOf(MITTELPUNKT, MITTELPUNKT),
            doubleArrayOf(MITTELPUNKT, MITTELPUNKT+ RADIUS),
            doubleArrayOf(x1+ MITTELPUNKT, y1+ MITTELPUNKT)))
/*
    for (i in 1 until prodList.size) {
        val x1 = sin(prodList[i].grad) * RADIUS
        val y1 = (sqrt(RADIUS) - sqrt(x1)).pow(0.5)
        prodList[i].matrix = Matrix(arrayOf<DoubleArray>(
                doubleArrayOf(MITTELPUNKT, MITTELPUNKT),
                doubleArrayOf(, 4.0),
                doubleArrayOf(1.0, 2.0)))
    }


*/





















    println("end")
}