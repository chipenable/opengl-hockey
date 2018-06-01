package ru.chipenable.openglhockey

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.toast

class AirHockeyActivity : AppCompatActivity() {

    private lateinit var glSurfaceView: GLSurfaceView
    private var rendererSet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        glSurfaceView = GLSurfaceView(this)

        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        val supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000

        if (supportsEs2){
            // Request an OpenGL ES 2.0 compatible context.
            glSurfaceView.setEGLContextClientVersion(2)

            // Assign our renderer.
            glSurfaceView.setRenderer(AirHockeyRenderer())
            rendererSet = true
        }
        else{
            toast("This device does not support OpenGL ES 2.0")
            return
        }

        setContentView(glSurfaceView)
    }

    override fun onResume() {
        super.onResume()

        if (rendererSet){
            glSurfaceView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()

        if (rendererSet){
            glSurfaceView.onPause()
        }
    }
}
