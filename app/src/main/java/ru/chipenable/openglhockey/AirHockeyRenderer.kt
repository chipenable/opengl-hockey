package ru.chipenable.openglhockey

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by Pavel.B on 26.05.2018.
 */
class AirHockeyRenderer: GLSurfaceView.Renderer {

    private companion object {
        val POSITION_COMPONENT_COUNT: Int = 2
        val BYTES_PER_FLOAT: Int = 4
    }

    private val vertexData: FloatBuffer
    private val tableVerticesWithTriangles: FloatArray = floatArrayOf(
            // Triangle 1
            0f, 0f,
            9f, 14f,
            0f, 14f,

            // Triangle 2
            0f, 0f,
            9f, 0f,
            9f, 14f,

            // Line 1
            0f, 7f,
            9f, 7f,

            // Mallets
            4.5f, 2f,
            4.5f, 12f

    )

    init{
        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()

        vertexData.put(tableVerticesWithTriangles)
    }


    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        // Set the open gl viewport to fill the entire surface.
       glViewport(0, 0, width, height)

    }

    override fun onDrawFrame(gl: GL10) {
      // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT)
    }
}