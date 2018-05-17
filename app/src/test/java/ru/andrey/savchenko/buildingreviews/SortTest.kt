package ru.andrey.savchenko.buildingreviews

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Created by savchenko on 16.05.18.
 */
class SortTest {
    private lateinit var randomArray: MutableList<Int>
    private var start: Long = 0
    private var end: Long = 0


    init {
        println("init")
        randomArray = generateRandomArray(10000).toMutableList()
    }

    @Before
    fun beforeTests() {
        start = System.currentTimeMillis()
    }

    @After
    fun afterTests() {
        end = System.currentTimeMillis()
        println("all tests finished at ${end - start} milliseconds")
    }

    @Test
    fun allSorts() {
        combSort()
        fullSort()
        bubbleSort() //too slow
        collectionsBaseSort()
        arraysSort()
        insertSort()
        chooseSort()
        mergeSort()
        quickSort()
        shellSort()
    }

    private fun bubbleSortFromFool(items: MutableList<Int>) {
        do {
            var swapped = false
            for (index in 1 until items.size) {
                val previousItem = items[index - 1]
                val currentItem = items[index]
                if (previousItem.compare(currentItem)) {
                    items.swapItems(index - 1, index)
                    swapped = true
                }
            }
        } while (swapped)
    }


    private fun cocktailSortAlgorithm(items: MutableList<Int>) {
        var rightBorder = items.size
        var leftBorder = 1

        fun swapIfNeedGoUp(items: MutableList<Int>): Boolean {
            var swapped = false
            for (index in 1 until rightBorder) {
                val previousItem = items[index - 1]
                val currentItem = items[index]
                if (previousItem.compare(currentItem)) {
                    items.swapItems(index - 1, index)
                    rightBorder = index - 1
                    swapped = true
                }
            }
            return swapped
        }

        fun swapIfNeedGoDown(items: MutableList<Int>): Boolean {
            var swapped = false
            for (index in items.size - 1 downTo leftBorder) {
                val previousItem = items[index - 1]
                val currentItem = items[index]
                if (previousItem.compare(currentItem)) {
                    items.swapItems(index - 1, index)
                    leftBorder = index
                    swapped = true
                }
            }
            return swapped
        }

        do {
            val swapped: Boolean = swapIfNeedGoUp(items) && swapIfNeedGoDown(items)
        } while (swapped)
    }


//    private fun swapIfNeedGoUp(items: MutableList<Int>):Boolean{
//        var swapped = false
//        for(index in 1 until items.size){
//            swapped = swapIfNeed(items, index-1, index, "up")
//        }
//        return swapped
//    }
//
//    private fun swapIfNeedGoDown(items: MutableList<Int>):Boolean{
//        var swapped = false
//        for(index in items.size-1 downTo 1){
//            swapped = swapIfNeed(items, index-1, index, "down")
//        }
//        return swapped
//    }
//
//    fun swapIfNeed(items:MutableList<Int>, previousIndex:Int, currentIndex:Int, upOrDown:String):Boolean{
//        val previousItem = items[previousIndex]
//        val currentItem = items[currentIndex]
//        var swapped = false
//        if(previousItem.compare(currentItem)){
//            items.swapItems(previousIndex, currentIndex)
//            println(upOrDown + " " + items)
//            swapped = true
//        }
//        return swapped
//    }

    private fun swapIfNeedNotReturn(items: MutableList<Int>): Boolean {
        for (index in 1 until items.size) {
            val previousItem = items[index - 1]
            val currentItem = items[index]
            if (previousItem.compare(currentItem)) {
                items.swapItems(index - 1, index)
                return true
            }
        }
        return false
    }

    fun combSort() {
        getTimeMethod("comb", {
            //            val fake = mutableListOf(14, 9, 1, 0, 33, 15, 12, 222, 45, 62, 1, 3)
            combSortAlgorithm(it)
        })
    }

    fun combSortAlgorithm(items: MutableList<Int>) {
        var gap = items.size
        var swapped = true
        var index: Int

        while (gap > 1 || swapped) {
            if (gap > 1) {
                gap = (gap / 1.3).toInt()
            }
            swapped = false
            index = 0
            while (index + gap < items.size) {
                if (items[index] > items[index + gap]) {
                    items.swapItems(index, index + gap)
                }
                index++
            }
        }
    }

    fun fullSort() {
        getTimeMethod("coctail", {
            //            val fake = mutableListOf(14, 9, 1, 0, 33, 5, 1, 15, 2, 45, 5, 15, 2266, 3)
            cocktailSortAlgorithm(it)
        })
    }

    private fun foolSortAlgorithm(items: MutableList<Int>) {
        var swapped = true
        while (swapped) {
            swapped = swapIfNeedAndReturn(items)
        }
    }

    private fun swapIfNeedAndReturn(items: MutableList<Int>): Boolean {
        for (index in 1 until items.size) {
            val previousItem = items[index - 1]
            val currentItem = items[index]
            if (previousItem.compare(currentItem)) {
                items.swapItems(index - 1, index)
                return true
            }
        }
        return false
    }

    private fun MutableList<Int>.swapItems(leftIndex: Int, rightIndex: Int) {
        if (leftIndex != rightIndex) {
            val temporary = this[leftIndex]
            this[leftIndex] = this[rightIndex]
            this[rightIndex] = temporary
        }
    }

    fun <T> swap(items: MutableList<T>, left: Int, right: Int) {
        if (left != right) {
            val temporary = items[left]
            items[left] = items[right]
            items[right] = temporary
        }
    }

    private fun Int.compare(anotherItem: Int): Boolean {
        return this - anotherItem > 0
    }

    fun shellSort(array: MutableList<Int>) {
        var interval = 1
        var insertedValue: Int
        var insertedIndex: Int

        while (interval < array.size / 3) {
            interval = 3 * interval + 1
        }

        while (interval > 0) {
            for (i in interval until array.size) {
                insertedValue = array[i]
                insertedIndex = i

                while (insertedIndex >= interval && insertedValue < array[insertedIndex - interval]) {
                    array[insertedIndex] = array[insertedIndex - interval]
                    insertedIndex = insertedIndex - interval
                }

                array[insertedIndex] = insertedValue
            }

            interval = (interval - 1) / 3

//            printArray(array)
        }
    }


    fun shellSort() {
        getTimeMethod("shell", {
            //            it.shellSort()
            shellSort(it)

        })
    }

    fun shelSortAlgorithm(items: MutableList<Int>) {
        val length = items.size
        for (gap in length / 2 downTo 0) {
            for (index in gap..length) {
                val tamporary = items[index]

            }
        }
    }

    private fun mergeSort() {
        getTimeMethod("merge", { items ->
            //            val fakeItems = mutableListOf(1, 15, 29, 30, 11, 42, 5)
            sortMergeAlgorithm(items)
        })
    }

    //делим коллекцию на две части рекурсивно(потом выполняем слияние)
    private fun sortMergeAlgorithm(items: MutableList<Int>) {
        fun merge(items: MutableList<Int>, left: MutableList<Int>, right: MutableList<Int>) {
            var leftIndex = 0
            var rightIndex = 0
            var targetIndex = 0
            var remaining = left.size + right.size
            while (remaining > 0) {
                when {
                //смотрим, где должен быть массив, в зависимости от уже отсортированного массива, вставляем либо справа либо слева
                    leftIndex >= left.size -> {
                        items[targetIndex] = right[rightIndex++]
                    }
                    rightIndex >= right.size -> {
                        items[targetIndex] = left[leftIndex++]
                    }
                    left[leftIndex] - right[rightIndex] < 0 -> {
                        items[targetIndex] = left[leftIndex++]
                    }
                    else -> {
                        items[targetIndex] = right[rightIndex++]
                    }
                }

                targetIndex++
                remaining--
            }
        }

        if (items.size <= 1) {
            return
        }
        val leftSize = items.size / 2
        val rightSize = items.size - leftSize
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        for (index in 0 until items.size) {
            if (index < leftSize) {
                left.add(items[index])
            } else {
                right.add(items[index])
            }
        }
        sortMergeAlgorithm(left)
        sortMergeAlgorithm(right)
        merge(items, left, right)
    }

    private fun quickSort() {
        getTimeMethod("quick", {

            quickSortAlgorithm(it, 0, it.size - 1)

        })
    }

    fun quickSortAlgorithm(items: MutableList<Int>, left: Int, right: Int) {
        fun partitions(items: MutableList<Int>, left: Int, right: Int, pivotIndex: Int): Int {
            //это опорный элемент
            val pivotValue = items[pivotIndex]
            //меняем местами опорный элемент и самый правый
            items.swapItems(pivotIndex, right)
            //запоминаем левый
            var storeIndex = left
            for (index in left until right) {
                if (items[index] - pivotValue < 0) {
                    swap(items, index, storeIndex)
                    storeIndex++
                }
            }
            swap(items, storeIndex, right)
            return storeIndex
        }

        if (left < right) {
            //выбираем случайным образом опорный элемент, который будем сравнивать
            // в диапазоне между большим и меньшим отсортированным элементом
            val pivotIndex: Int = (Math.random() * (right - left)).toInt()
            val newPivot = partitions(items, left, right, pivotIndex)

            quickSortAlgorithm(items, left, newPivot - 1)
            quickSortAlgorithm(items, newPivot + 1, right)
        }
    }


    private fun getTimeMethod(sort: String, sortMethod: (notSortedArray: MutableList<Int>) -> Unit) {
        val notSortedArray = mutableListOf<Int>()
        notSortedArray.addAll(randomArray)
        val start = System.currentTimeMillis()

        sortMethod(notSortedArray)

        val end = System.currentTimeMillis()
        println("${end - start} $sort time of method in milliseconds")
    }

    private fun insertSort() {
        getTimeMethod("insert", { notSortedArray ->
            insertSortAlgorithm(notSortedArray)
        })
    }

    private fun insertSortAlgorithm(notSortedArray:MutableList<Int>){
        var sortedRange = 1

        fun findInsertionIndex(items: MutableList<Int>, value: Int): Int {
            //ищем элемент, который необходимо вставить
            for (index in 0 until items.size) {
                if (items[index] - value > 0) {
                    return index
                }
            }
            throw Throwable("The insertion index was not found")
        }


        fun insert(itemArray: MutableList<Int>,
                           maxUnsortedElementFromStart: Int,
                           firstUnsortedElement: Int) {
            //сохранить текущий индекс во временную переменную
            val temporary = itemArray[maxUnsortedElementFromStart]
            //присвоить текущему элементу элемент, который хотим вставить на его место
            itemArray[maxUnsortedElementFromStart] = itemArray[firstUnsortedElement]
            //сдвинуть все элементы на один с того, который хотим вставить до места куда вставляем
            for (current in maxUnsortedElementFromStart downTo firstUnsortedElement) {
                itemArray[current] = itemArray[current - 1]
            }
            //присвоить временный файл индексу текущего элемента +1
            itemArray[maxUnsortedElementFromStart + 1] = temporary
        }

        while (sortedRange < notSortedArray.size) {
            if (notSortedArray[sortedRange] - notSortedArray[sortedRange - 1] < 0) {
                //сравнить текущий и предыдущий, если текущий меньше, то продолжить алгоритм, если нет, перейти к след. элементу
                val insertIndex = findInsertionIndex(notSortedArray, notSortedArray[sortedRange])
                insert(notSortedArray, insertIndex, sortedRange)
            }
            sortedRange++
        }
    }



    private fun collectionsBaseSort() {
        getTimeMethod("collections", {
            it.sort()
        })
    }

    private fun arraysSort() {
        getTimeMethod("arrays", { notSortedArray ->
            Arrays.sort(notSortedArray.toIntArray())
        })
    }

    private fun chooseSort() {

    }

    private fun bubbleSort() {
        getTimeMethod("bubble", { notSortedArray ->
            do {
                var swapped = false

                for (index in 1 until notSortedArray.size) {
                    //если предыдущий элемент меньше текущего, они меняются местами
                    if (notSortedArray[index - 1] - notSortedArray[index] > 0) {
                        swap(notSortedArray, index - 1, index)
                        swapped = true
                    }
                }
            } while (swapped)
        })
    }

    fun generateRandomArray(n: Int): List<Int> {
        val list = ArrayList<Int>(n)
        val random = Random()

        for (i in 0 until n) {
            list.add(random.nextInt(1000))
        }
        return list
    }
}