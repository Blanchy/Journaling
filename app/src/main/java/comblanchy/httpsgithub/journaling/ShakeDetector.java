package comblanchy.httpsgithub.journaling;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by blanchypolangcos on 12/7/17.
 */

class ShakeDetector implements SensorEventListener {

    private long timeOfLastShake;

    private static final float SHAKE_THRESHOLD_GRAVITY = 22.0f;
    private static final int SHAKE_TIME_LAPSE = 500;
    private OnShakeListener shakeListener;

    public ShakeDetector(OnShakeListener shakeListener) {
        this.shakeListener = shakeListener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            float gX = x - SensorManager.GRAVITY_EARTH;
            float gY = y - SensorManager.GRAVITY_EARTH;
            float gZ = z - SensorManager.GRAVITY_EARTH;

            double value = Math.pow(gX, 2.0) + Math.pow(gY, 2.0) + Math.pow(gZ, 2.0);
            float gForce = (float) Math.sqrt(value);

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                if (timeOfLastShake + SHAKE_TIME_LAPSE > now) {
                    return;
                }
                timeOfLastShake = now;

                shakeListener.onShake();
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public interface OnShakeListener {
        void onShake();
    }
}
