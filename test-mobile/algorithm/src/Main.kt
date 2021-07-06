fun main() {
    rotateLeft(arrayOf(1, 2, 3, 4, 5, 6), 3)
    rotateLeft(arrayOf(2, 3, 5, 1, 2, 3, 9, 8), 5)
}

private fun rotateLeft(arrInt: Array<Int>, numberOfRotation: Int) {
    for (i in 0 until numberOfRotation) {
        val temp = arrInt[0]
        for (j in arrInt.indices) arrInt[j] = if (j == arrInt.lastIndex) temp else arrInt[j + 1]
    }
    println(arrInt.toList())
}