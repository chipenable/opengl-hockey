package ru.chipenable.openglhockey

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by Pavel.B on 26.05.2018.
 */
class FirstOpenGLProjectRenderer: GLSurfaceView.Renderer {


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