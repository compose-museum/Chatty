package com.chatty.compose.ui.utils

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

interface Comparator<T> {
    fun compare(target: T): Boolean
}

fun <E> List<E>.searchLastElementIndex(comparator: Comparator<E>): Int {
    var l = -1
    var r = size - 1
    while (l < r) {
        val mid = l - (l - r - 1) / 2
        if (comparator.compare(get(mid))) {
            l = mid
        } else {
            r = mid - 1
        }
    }
    return l
}

fun <E> List<E>.searchFirstElementIndex(comparator: Comparator<E>): Int {
    var l = 0
    var r = size
    while (l < r) {
        val mid = l - (l - r) / 2
        if (comparator.compare(get(mid))) {
            r = mid
        } else {
            l = mid + 1
        }
    }
    return l
}



@Composable
fun FunctionalityNotAvailablePopup(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = "Functionality not available \uD83D\uDE48",
                style = MaterialTheme.typography.body1
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "CLOSE")
            }
        }
    )
}
