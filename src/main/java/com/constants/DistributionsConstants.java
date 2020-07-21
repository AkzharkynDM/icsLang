package com.constants;

public class DistributionsConstants {
    //public final static String BERNOULLI = "Bernoulli";
    //public final static String EXPONENTIAL = "Exponential";
    public final static String EasyAndCertain = "[Exponential(1)]";
    public final static String EasyAndUncertian	= "[Bernoulli(0.5)]";
    public final static String HardAndCertain = "[Exponential(0.1)]";
    public final static String HardAndUncertain	= "[Bernoulli(0.5) * Exponential(0.1)]";
    public final static String VeryHardAndCertain	= "[Exponential(0.01)]";
    public final static String VeryHardAndUncertain	= "[Bernoulli(0.5) * Exponential(0.01)]";
}
