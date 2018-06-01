package ru.chipenable.openglhockey

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import ru.chipenable.openglhockey.util.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by Pavel.B on 26.05.2018.
 */
class AirHockeyRenderer(val context: Context): GLSurfaceView.Renderer {

    private companion object {
        val COLOR_COMPONENT_COUNT = 3
        val POSITION_COMPONENT_COUNT = 2
        val BYTES_PER_FLOAT = 4
        val U_COLOR = "u_Color"
        val A_POSITION = "a_Position"
        val A_COLOR = "a_Color"
        val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT
    }

    private var aPositionLocation = 0
    private var aColorLocation = 0
    private var program = 0

    private val vertexData: FloatBuffer
    private val tableVerticesWithTriangles: FloatArray = floatArrayOf(
            // Triangle Fan
             0f,    0f,   1f,   1f,   1f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
             0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

             0.5f,  0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f,  0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

            // Line 1
            -0.5f, 0f, 1f, 0f, 0f,
            0.5f, 0f, 1f, 0f, 0f,

            // Mallets
            0f, -0.25f, 0f, 0f, 1f,
            0f, 0.25f, 1f, 0f, 0f
    )

    init{
        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()

        vertexData.put(tableVerticesWithTriangles)
    }


    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        val vertexShaderSource = readTextFileFromResource(context, R.raw.simple_vertex_shader)
        val fragmentShaderSource = readTextFileFromResource(context, R.raw.simple_fragment_shader)
        val vertexShader = compileVertexShader(vertexShaderSource)
        val fragmentShader = compileFragmentShader(fragmentShaderSource)
        program = linkProgram(vertexShader, fragmentShader)
        validateProgram(program)
        glUseProgram(program)

        aPositionLocation = glGetAttribLocation(program, A_POSITION)
        aColorLocation = glGetAttribLocation(program, A_COLOR)

        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT,
                GL_FLOAT, false, STRIDE, vertexData)
        glEnableVertexAttribArray(aPositionLocation)

        vertexData.position(POSITION_COMPONENT_COUNT)
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT,
                GL_FLOAT, false, STRIDE, vertexData)
        glEnableVertexAttribArray(aColorLocation)

    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        // Set the open gl viewport to fill the entire surface.
       glViewport(0, 0, width, height)

    }

    override fun onDrawFrame(gl: GL10) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT)

        glUniform4f(aColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6)

        glUniform4f(aColorLocation, 1.0f, 0.0f, 0.0f,1.0f)
        glDrawArrays(GL_LINES, 6, 2)

        glUniform4f(GL_POINTS, 0.0f, 0.0f, 1.0f, 1.0f)
        glDrawArrays(GL_POINTS, 8, 1)

        glUniform4f(GL_POINTS, 0.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_POINTS, 9, 1)
    }
}