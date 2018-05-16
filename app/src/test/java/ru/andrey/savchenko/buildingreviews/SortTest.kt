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
        bubbleSort()
        collectionsBaseSort()
        arraysSort()
        insertSort()
        chooseSort()
        mergeSort()
        quickSort()
    }

    private fun mergeSort() {
        getTimeMethod("merge", { items ->
            val fakeItems = mutableListOf(1, 15, 29, 30, 11, 42, 5)
            sortMergeAlgorithm(fakeItems)
        })
    }

    //делим коллекцию на две части рекурсивно(потом выполняем слияние)
    private fun sortMergeAlgorithm(items: MutableList<Int>) {
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
        println("merge items: $items left: $left right: $right")
    }


    private fun merge(items: MutableList<Int>, left: MutableList<Int>, right: MutableList<Int>) {
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

    private fun quickSort() {
        getTimeMethod("quick", {

            quickSortAlgorithm(it, 0, it.size-1)

        })
    }

    fun quickSortAlgorithm(items:MutableList<Int>, left:Int, right:Int){
        if(left<right){
            val pivotIndex :Int= (Math.random()*(right-left)).toInt()
            val newPivot = partitions(items, left, right, pivotIndex)

            quickSortAlgorithm(items, left, newPivot-1)
            quickSortAlgorithm(items, newPivot+1, right)
        }
    }

    fun partitions(items:MutableList<Int>, left:Int, right:Int, pivotIndex:Int):Int{
        val pivotValue = items[pivotIndex]
        swap(items, pivotIndex, right)
        var storeIndex = left
        for(index in left until right){
            if(items[index]-pivotValue<0){
                swap(items, index, storeIndex)
                storeIndex++
            }
        }
        swap(items, storeIndex, right)
        return storeIndex
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
            var sortedRange = 1
            while (sortedRange < notSortedArray.size) {
                if (notSortedArray[sortedRange] - notSortedArray[sortedRange - 1] < 0) {
                    //сравнить текущий и предыдущий, если текущий меньше, то продолжить алгоритм, если нет, перейти к след. элементу
                    val insertIndex = findInsertionIndex(notSortedArray, notSortedArray[sortedRange])
                    insert(notSortedArray, insertIndex, sortedRange)
                }
                sortedRange++
            }
        })
    }

    private fun findInsertionIndex(items: MutableList<Int>, value: Int): Int {
        //ищем элемент, который необходимо вставить
        //todo  как мы определяем какой в элемент будем вставлять??? what is value, how get it?
        for (index in 0 until items.size) {
            if (items[index] - value > 0) {
                return index
            }
        }
        throw Throwable("The insertion index was not found")
    }


    private fun insert(itemArray: MutableList<Int>,
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

    fun <T> swap(items: MutableList<T>, left: Int, right: Int) {
        if (left != right) {
            val temporary = items[left]
            items[left] = items[right]
            items[right] = temporary
        }
    }
}