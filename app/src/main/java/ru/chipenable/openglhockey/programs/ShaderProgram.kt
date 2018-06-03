package ru.chipenable.openglhockey.programs

import android.content.Context
import android.opengl.GLES20.glUseProgram
import ru.chipenable.openglhockey.util.buildProgram
import ru.chipenable.openglhockey.util.readTextFileFromResource

/**
 * Created by Pavel.B on 02.06.2018.
 */
open class ShaderProgram(context: Context, vertexShaderResourceId: Int, fragmentShaderResourceId: Int){

    protected companion object {
        // Uniform constants
        val U_MATRIX = "u_Matrix"
        val U_TEXTURE_UNIT = "u_TextureUnit"
        val U_COLOR = "u_Color"

        // Attribute constants
        val A_POSITION = "a_Position"
        val A_COLOR = "a_Color"
        val A_TESTURE_COORDINATES = "a_TextureCoordinates"
    }

    // Shader program
    protected val program: Int

    init{
        // Compile and link the program
        program = buildProgram(readTextFileFromResource(context, vertexShaderResourceId),
                readTextFileFromResource(context, fragmentShaderResourceId))
    }

    // Set the current OpenGL shader program to this program
    fun useProgram() = glUseProgram(program)

}