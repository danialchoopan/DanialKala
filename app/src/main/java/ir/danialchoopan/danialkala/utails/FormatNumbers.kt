package ir.danialchoopan.danialkala.utails

import java.text.DecimalFormat

class FormatNumbers {
    companion object {
        fun formatPrice(number: String): String {
            val decimalFormat = DecimalFormat("###,###")
            return decimalFormat.format(number.toInt())
        }
    }
}