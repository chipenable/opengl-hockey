package ru.chipenable.openglhockey.programs

import android.content.Context
import android.opengl.GLES20.*
import ru.chipenable.openglhockey.R

/**
 * Created by Pavel.B on 02.06.2018.
 */
class ColorShaderProgram(context: Context): ShaderProgram(context,
        R.raw.simple_vertex_shader, R.raw.simple_fragment_shader) {

    // Attribute locations
    val aPositionLocation: Int
    val aColorLocation: Int

    // Uniform locations
    private val uMatrixLocation: Int
    private val uColorLocation: Int

    init{
        // Retrieve uniform locations for the shader program
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX)
        uColorLocation = glGetUniformLocation(program, U_COLOR)

        // Retrieve attribute locations for the shader program
        aPositionLocation = glGetAttribLocation(program, A_POSITION)
        aColorLocation = glGetAttribLocation(program, A_COLOR)
    }

    fun setUniform(matrix: FloatArray, r: Float, g: Float, b: Float){
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        glUniform4f(uColorLocation, r, g, b, 1f)
    }

}