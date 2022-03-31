package com.chatty.compose.ui.utils

interface Comparator<T> {
    fun compare(midValue: T, target: T): Boolean
}

fun <E> binarySearchLastElementIndex(preSum: List<E>, target: E, comparator: Comparator<E>): Int {
    var l = -1
    var r = preSum.size - 1
    while (l < r) {
        var mid = l - (l - r - 1) / 2
        if (comparator.compare(preSum[mid], target)) {
            l = mid
        } else {
            r = mid - 1
        }
    }
    return l
}

fun <E> binarySearchFirstElementIndex(preSum: List<E>, target: E, comparator: Comparator<E>): Int {
    var l = 0
    var r = preSum.size
    while (l < r) {
        var mid = l - (l - r) / 2
        if (comparator.compare(preSum[mid], target)) {
            r = mid
        } else {
            l = mid + 1
        }
    }
    return l
}