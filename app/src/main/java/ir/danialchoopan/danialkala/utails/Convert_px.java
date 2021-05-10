package ir.danialchoopan.danialkala.utails;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Convert_px {
    public static int convertpx(float px, Context context){
        Resources resources=context.getResources();
        DisplayMetrics metrics=resources.getDisplayMetrics();
        float dp= px /((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT );
       return (int)dp;
    }
}
