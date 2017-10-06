package com.onval.bakingapp.utils;

import junit.framework.Assert;

import org.junit.Test;
/**
 * Created by gval on 06/10/2017.
 */
public class FormatUtilsTest {
    @Test
    public void formatStepInstructionTest() {
        String input = "step1.step2.step3.";
        String exptected = "step1)step2.\nstep3.\n";

        String output = FormatUtils.formatStepInstructions(input);
        Assert.assertEquals(exptected, output);

        input = "step1.step2.step3";
        exptected = "step1)step2.\nstep3.\n";

        output = FormatUtils.formatStepInstructions(input);
        Assert.assertEquals(exptected, output);
    }

}