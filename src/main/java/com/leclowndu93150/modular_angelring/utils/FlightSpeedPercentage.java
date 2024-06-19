package com.leclowndu93150.modular_angelring.utils;

public class FlightSpeedPercentage {

    /**
     * Converts a given float to a percentage based on the range where 0.02F is 100%.
     * The range is from 0% to 300%.
     *
     * @param input the float value to be converted to percentage
     * @return the calculated percentage as an integer
     * @throws IllegalArgumentException if the input value is out of range (less than 0 or greater than 0.06)
     */
    public static int speedToPercentage(float input) {

        if (input < 0 || input > 0.06F) {
            throw new IllegalArgumentException("Input must be in the range from 0 to 0.06 (inclusive)");
        }

        int percentage = Math.round((input / 0.02F) * 100);

        if (percentage < 0) {
            return 0;
        } else if (percentage > 300) {
            return 300;
        } else {
            return percentage;
        }
    }

}
