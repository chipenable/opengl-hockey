package ru.chipenable.openglhockey.data

import android.opengl.GLES20.*
import ru.chipenable.openglhockey.Constants
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created by Pavel.B on 02.06.2018.
 */
class VertexArray(vertexData: FloatArray) {

    val floatBuffer: FloatBuffer



    init {
        floatBuffer = ByteBuffer
                .allocateDirect(vertexData.size * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData)
    }

    fun setVertexAttribPointer(dataOffset: Int, attributeLocation: Int,
                               componentCount: Int, stride: Int){
        floatBuffer.position(dataOffset)
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT,
                false, stride, floatBuffer)
        glEnableVertexAttribArray(attributeLocation)
        floatBuffer.position(0)
    }

}