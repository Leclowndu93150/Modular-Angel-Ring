package com.leclowndu93150.modular_angelring.utils;

public class FlightSpeedPercentage {

    /**
     * Converts a given float to a percentage based on the range where 0.05F is 100%.
     * Allows values beyond 300% if higher speeds are configured.
     *
     * @param input the float value to be converted to percentage
     * @return the calculated percentage as an integer
     * @throws IllegalArgumentException if the input value is negative
     */
    public static int speedToPercentage(float input) {

        if (input < 0) {
            throw new IllegalArgumentException("Input must be non-negative");
        }

        // Calculate percentage based on 0.05F as 100%
        return Math.round((input / 0.05F) * 100);
    }
}
