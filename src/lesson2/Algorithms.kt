@file:Suppress("UNUSED_PARAMETER")

package lesson2

import java.io.File
import kotlin.math.sqrt

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
//Трудоемкость O(n^2)
//Ресурсоемкость O(n)
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    val limiter = Regex("""\d+""")
    val list = mutableListOf<Int>()
    var currentProfit = 0
    var result: Pair<Int, Int> = 0 to 0
    for (line in File(inputName).readLines()) {
        if (!(line.matches(limiter))) throw IllegalArgumentException()
        list += line.toInt()
    }
    for (i in list.indices) {
        for (j in i until list.size) {
            if (list[j] - list[i] > currentProfit) {
                result = i + 1 to j + 1
                currentProfit = list[j] - list[i]
            }
        }
    }
    return result
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 *
 * Общий комментарий: решение из Википедии для этой задачи принимается,
 * но приветствуется попытка решить её самостоятельно.
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    TODO()
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
fun longestCommonSubstring(first: String, second: String): String {
    val table: Array<Array<Int>> = Array(first.length + 1) { Array(second.length + 1) { 0 } }
    var maxLength = 0
    var marker = 0
    table[0][0] = 0
    for (i in first.indices) {
        for (j in second.indices) {
            if (first[i] == second[j]) {
                val intermediate = table[i][j] + 1
                table[i + 1][j + 1] = intermediate
                if (intermediate > maxLength) {
                    maxLength = intermediate
                    marker = i + 1
                }
            }
        }
    }
    return first.substring(marker - maxLength, marker)
}

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
fun calcPrimesNumber(limit: Int): Int {
    if (limit <= 1) return 0
    val list = Array(limit - 1) { it + 2 }
    if (limit <= 3) return list.size
    else {
        for (i in 0..sqrt(limit.toDouble()).toInt()) {
            if (list[i] != 0) {
                for (j in i + 1 until list.size) if (list[j] % list[i] == 0) list[j] = 0
            }
        }
    }
    return list.filter { it != 0 }.size
}

fun main() {
    val limit = 4
    val list = Array(limit - 1) { it + 2 }
    if (limit <= 3) println("Break")
    else {
        for (i in 0..sqrt(list.size.toDouble()).toInt()) {
            if (list[i] != 0) {
                for (j in i + 1 until limit - 1) if (list[j] % list[i] == 0) list[j] = 0
            }
        }
    }
    val result = list.filter { it != 0 }
    for (i in result) println(i)
    println(result.size)
}
