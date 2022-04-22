package com.example.sensorsurvey

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import java.lang.StringBuilder

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var mSensorManager : SensorManager

    private lateinit var mSensorProximty: Sensor
    private lateinit var mSensorLight: Sensor

    private lateinit var mTextSensorLight: TextView
    private lateinit var mTextSensorProximity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val sensorList : List<Sensor> = mSensorManager.getSensorList(Sensor.TYPE_ALL)

        val sensorText : StringBuilder = StringBuilder()

        for (currentSensor: Sensor in sensorList){
            sensorText.append(currentSensor.name).append(System.getProperty("line.separator"))
        }

        findViewById<TextView>(R.id.sensor_list).text = sensorText.toString()

        mTextSensorLight = findViewById(R.id.label_light)
        mTextSensorProximity = findViewById(R.id.label_proximity)

        mSensorProximty = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    override fun onStart() {
        super.onStart()

        mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL)

        mSensorManager.registerListener(this, mSensorProximty, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        super.onStop()
        mSensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensorType = event!!.sensor.type
        val currentValue = event.values[0]

        Log.d(TAG, "onSensorChanged: $sensorType $currentValue")

        when(sensorType){
            Sensor.TYPE_LIGHT -> mTextSensorLight.text = getString(R.string.light_sensor_text, currentValue)
            Sensor.TYPE_PROXIMITY -> mTextSensorProximity.text = getString(R.string.proximity_sensor_text, currentValue)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
//        TODO("Not yet implemented")
    }
}