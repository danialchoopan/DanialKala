package ir.danialchoopan.danialkala.utails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;


@SuppressLint("ParcelCreator")
public class CustomTypefaceSpan extends TypefaceSpan {
    private final Typeface newType;
    private Context _context;

    public CustomTypefaceSpan(Context context, String family, Typeface type) {
        super(family);
        newType = type;
        this._context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        applyCustomTypeFace(_context, ds, newType);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeFace(_context, paint, newType);
    }

    private static void applyCustomTypeFace(Context context, Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTextSize(24f);
        paint.setTypeface(tf);
    }
}
