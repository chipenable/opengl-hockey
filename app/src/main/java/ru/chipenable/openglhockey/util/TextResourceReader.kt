package ru.chipenable.openglhockey.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by Pavel.B on 26.05.2018.
 */

fun readTextFileFromResource(context: Context, resourceId: Int): String {

    var body = ""
    val resources = context.resources

    BufferedReader(InputStreamReader(resources.openRawResource(resourceId)))
            .use{
                body = it.readLines().joinToString(separator="\n")
    }

    return body
}