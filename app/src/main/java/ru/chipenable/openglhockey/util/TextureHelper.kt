package ru.chipenable.openglhockey.util

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLUtils.texImage2D

/**
 * Created by Pavel.B on 02.06.2018.
 */

private val TAG = "TextureHelper"

fun loadTexture(context: Context, resourceId: Int): Int {
    val textureObjectIds = IntArray(1)
    glGenTextures(1, textureObjectIds, 0)

    if (textureObjectIds[0] == 0){
        debugLogger(TAG){
            "Could not generate a new OpenGL texture object"
        }
    }

    val options = BitmapFactory.Options()
    options.inScaled = false

    val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, options)
    if (bitmap == null){
        debugLogger(TAG){
            "Resource ID $resourceId could not be decoded"
        }

        glDeleteTextures(1, textureObjectIds, 0)
        return 0
    }

    glBindTexture(GL_TEXTURE_2D, textureObjectIds[0])
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
    texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
    bitmap.recycle()
    glGenerateMipmap(GL_TEXTURE_2D)
    glBindTexture(GL_TEXTURE_2D, 0)

    return textureObjectIds[0]
}