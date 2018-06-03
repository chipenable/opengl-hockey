package ru.chipenable.openglhockey

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix.*
import ru.chipenable.openglhockey.objects.Mallet
import ru.chipenable.openglhockey.objects.Puck
import ru.chipenable.openglhockey.objects.Table
import ru.chipenable.openglhockey.programs.ColorShaderProgram
import ru.chipenable.openglhockey.programs.TextureShaderProgram
import ru.chipenable.openglhockey.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by Pavel.B on 26.05.2018.
 */
class AirHockeyRenderer(val context: Context): GLSurfaceView.Renderer {

    private lateinit var table: Table
    private lateinit var mallet: Mallet
    private lateinit var puck: Puck

    private lateinit var textureProgram: TextureShaderProgram
    private lateinit var colorProgram: ColorShaderProgram

    private var texture = 0

    private val projectionMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val viewProjectionMatrix = FloatArray(16)
    private val modelViewProjectionMatrix = FloatArray(16)

    private val TAG = "AirHockeyRenderer"

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        table = Table()
        mallet = Mallet(0.08f, 0.15f, 32)
        puck = Puck(0.06f, 0.02f, 32)

        textureProgram = TextureShaderProgram(context)
        colorProgram = ColorShaderProgram(context)

        texture = loadTexture(context, R.drawable.air_hockey_surface)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        // Set the open gl viewport to fill the entire surface.
        glViewport(0, 0, width, height)
        perspectiveM(projectionMatrix, 45f, width.toFloat()/height.toFloat(),
                1f, 10f)
        setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f,
                0f, 0f, 0f, 0f, 1f, 0f)
    }

    override fun onDrawFrame(gl: GL10) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT)
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        // Draw the table
        positionTableInScene()
        textureProgram.useProgram()
        textureProgram.setUniforms(modelViewProjectionMatrix, texture)
        table.bindData(textureProgram)
        table.draw()

        // Draw the mallets
        positionObjectInScene(0f, mallet.height / 2f, -0.4f)
        colorProgram.useProgram()
        colorProgram.setUniform(modelViewProjectionMatrix, 1f, 0f, 0f)
        mallet.bindData(colorProgram)
        mallet.draw()

        positionObjectInScene(0f, mallet.height / 2f, 0.4f)
        colorProgram.setUniform(modelViewProjectionMatrix, 0f, 0f, 1f)
        mallet.draw()

        // Draw the puck
        positionObjectInScene(0f, puck.height / 2f, 0f)
        colorProgram.setUniform(modelViewProjectionMatrix, 0.8f, 0.8f, 1f)
        puck.bindData(colorProgram)
        puck.draw()
    }

    private fun positionTableInScene(){
        setIdentityM(modelMatrix, 0)
        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f)
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0,
                modelMatrix, 0)
    }

    private fun positionObjectInScene(x: Float, y: Float, z: Float){
        setIdentityM(modelMatrix, 0)
        translateM(modelMatrix, 0, x, y, z)
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0)
    }

}