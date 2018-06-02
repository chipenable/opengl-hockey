package ru.chipenable.openglhockey.util

import android.opengl.GLES20.*

/**
 * Created by Pavel.B on 26.05.2018.
 */

private val TAG = "ShaderHelper"

fun buildProgram(vertexShaderSource: String, fragmentShaderSource: String): Int {

    val vertexShader = compileVertexShader(vertexShaderSource)
    val fragmentShader = compileFragmentShader(fragmentShaderSource)
    val program = linkProgram(vertexShader, fragmentShader)

    validateProgram(program)
    return program
}

fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
    val programObjectId = glCreateProgram()

    if (programObjectId == 0){
        debugLogger(TAG){
            "Could not create new program"
        }

        return 0
    }

    glAttachShader(programObjectId, vertexShaderId)
    glAttachShader(programObjectId, fragmentShaderId)
    glLinkProgram(programObjectId)

    debugLogger(TAG){
        "Result of linking program: \n${glGetProgramInfoLog(programObjectId)}"
    }

    val linkStatus = IntArray(1)
    glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0)

    if (linkStatus[0] == 0){
        // If it failed, delete the program object;
        glDeleteProgram(programObjectId)
        debugLogger(TAG){
            "Linking of program failed."
        }

        return 0
    }

    return programObjectId
}

fun validateProgram(programObjectId: Int): Boolean {
    glValidateProgram(programObjectId)

    val validateStatus = IntArray(1)
    glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0)
    debugLogger(TAG){
        "results of validating program: ${validateStatus[0]}\nLog: ${glGetProgramInfoLog(programObjectId)}"
    }

    return validateStatus[0] != 0
}

fun compileVertexShader(shaderCode: String)
        = compileShader(GL_VERTEX_SHADER, shaderCode)

fun compileFragmentShader(shaderCode: String)
        = compileShader(GL_FRAGMENT_SHADER, shaderCode)

private fun compileShader(type: Int, shaderCode: String): Int{

    val shaderObjectId = glCreateShader(type)

    if (shaderObjectId == 0){
        debugLogger(TAG){
            "Could not create new shader"
        }
        return 0
    }

    glShaderSource(shaderObjectId, shaderCode)
    glCompileShader(shaderObjectId)

    debugLogger(TAG){
        "Results of compiling: \n$shaderCode \n${glGetShaderInfoLog(shaderObjectId)}"
    }

    val compileStatus = IntArray(1)
    glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0)

    if (compileStatus[0] == 0){
        // if it failed, delete the shader object.
        glDeleteShader(shaderObjectId)

        debugLogger(TAG){
            "Compilation of shader failed"
        }

        return 0
    }

    return shaderObjectId
}