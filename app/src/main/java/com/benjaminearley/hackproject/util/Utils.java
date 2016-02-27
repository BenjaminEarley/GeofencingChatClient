package com.benjaminearley.hackproject.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class Utils {

    public static Drawable makeCircleAvatar(Bitmap bmp) {
        ShapeDrawable dr = new ShapeDrawable(new OvalShape());
        dr.getPaint().setShader(new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        dr.setIntrinsicWidth(bmp.getWidth());
        dr.setIntrinsicHeight(bmp.getHeight());
        return dr;
    }
}
