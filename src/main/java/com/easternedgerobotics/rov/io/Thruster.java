package com.easternedgerobotics.rov.io;

import com.easternedgerobotics.rov.io.pololu.PololuMaestroOutputChannel;
import com.easternedgerobotics.rov.value.SpeedValue;

import rx.Observable;

/**
 * See the <a href="http://docs.bluerobotics.com/besc/#specification-table">Specification Table for the Blue
 * Robotics Basic ESCs</a> for more information.
 */
public class Thruster {
    /**
     * Max forward value for PololuMaestroOutputChannel connected to the Blue Robotics basic ESC.
     */
    public static final float MAX_FWD = 1100;

    /**
     * Max reverse value for PololuMaestroOutputChannel connected to the Blue Robotics basic ESC.
     */
    public static final float MAX_REV = 1900;

    /**
     * The output pololu channel to write speeds.
     */
    private final PololuMaestroOutputChannel device;

    /**
     * The latest value from the speed observable.
     */
    private SpeedValue value;

    /**
     * Create a Thruster device which uses a pololu channel for communication.
     *
     * @param values speed observable mapped to the pololu channel
     * @param device pololu channel to write speeds.
     */
    public Thruster(
        final Observable<SpeedValue> values,
        final PololuMaestroOutputChannel device
    ) {
        this.device = device;
        values.subscribe(v -> value = v);
    }

    /**
     * Write the latest {@code SpeedValue} for this thruster to the device.
     */
    public final void write() {
        device.write(value.getSpeed());
    }

    /**
     * Write zero to the thruster.
     */
    public final void writeZero() {
        device.writeZero();
    }
}
