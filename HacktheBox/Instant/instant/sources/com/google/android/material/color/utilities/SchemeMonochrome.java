package com.google.android.material.color.utilities;

/* loaded from: classes.dex */
public class SchemeMonochrome extends DynamicScheme {
    public SchemeMonochrome(Hct hct, boolean z, double d) {
        super(hct, Variant.MONOCHROME, z, d, TonalPalette.fromHueAndChroma(hct.getHue(), 0.0d), TonalPalette.fromHueAndChroma(hct.getHue(), 0.0d), TonalPalette.fromHueAndChroma(hct.getHue(), 0.0d), TonalPalette.fromHueAndChroma(hct.getHue(), 0.0d), TonalPalette.fromHueAndChroma(hct.getHue(), 0.0d));
    }
}
