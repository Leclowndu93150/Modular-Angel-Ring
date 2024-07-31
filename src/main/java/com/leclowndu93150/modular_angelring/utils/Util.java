package com.leclowndu93150.modular_angelring.utils;

import net.minecraft.client.Options;

public class Util{

public static void setGamma(Options options, double gamma) {
    options.gamma().set(gamma);
}
public static double getGamma(Options options) {
    return options.gamma().get();

}

}

