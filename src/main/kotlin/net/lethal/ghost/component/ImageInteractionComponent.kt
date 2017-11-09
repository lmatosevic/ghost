package net.lethal.ghost.component

import javafx.scene.effect.Light
import javafx.scene.effect.Lighting
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import net.lethal.ghost.event.DeviceType
import tornadofx.*

class ImageInteractionComponent(private val keyboardImg: ImageView, private val mouseImg: ImageView) : Component() {
    private val lightingEffect: Lighting
    private var mouseLastEvent: Long = System.currentTimeMillis()
    private var keyboardLastEvent: Long = System.currentTimeMillis()

    init {
        val lighting = Lighting(Light.Distant(45.0, 45.0, Color.web("#4191e0")))
        lighting.diffuseConstant = 0.51
        lighting.specularConstant = 1.23
        lighting.specularExponent = 0.93
        lighting.surfaceScale = 3.36
        lightingEffect = lighting
    }

    fun changeImageColor(deviceType: DeviceType) {
        val imageView: ImageView
        when (deviceType) {
            DeviceType.KEYBOARD -> {
                keyboardLastEvent = System.currentTimeMillis()
                imageView = keyboardImg
            }
            DeviceType.MOUSE -> {
                mouseLastEvent = System.currentTimeMillis()
                imageView = mouseImg
            }
            else -> {
                return
            }
        }

        if (imageView.effect == null) {
            imageView.effect = lightingEffect
        }

        runAsync {
            Thread.sleep(300)
        } ui {
            val lastEvent = if (deviceType == DeviceType.KEYBOARD) keyboardLastEvent else mouseLastEvent
            if (lastEvent < System.currentTimeMillis() - 50) {
                imageView.effect = null
            }
        }
    }
}