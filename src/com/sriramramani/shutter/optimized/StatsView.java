/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sriramramani.shutter.R;

public class StatsView extends TextView {
    
    private static ImageSpan sLikeSpan;
    private static ImageSpan sCommentSpan;

    public StatsView(Context context) {
        this (context, null);
    }

    public StatsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        if (sLikeSpan == null) {
            Drawable like = getResources().getDrawable(R.drawable.ic_like);
            Drawable comment = getResources().getDrawable(R.drawable.ic_comment);
            final int width = like.getIntrinsicWidth() * 3/4;
            final Rect bounds = new Rect(0, 0, width, width); 
            like.setBounds(bounds);
            comment.setBounds(bounds);
            sLikeSpan = new ImageSpan(like, ImageSpan.ALIGN_BOTTOM);
            sCommentSpan = new ImageSpan(comment, ImageSpan.ALIGN_BOTTOM);
        }
    }
    
    public void setStats(int likes, int comments) {
        final int likeLength = new String("" + likes).length();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("  " + likes + "    " + comments);
        builder.setSpan(sLikeSpan, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(sCommentSpan, likeLength + 4, likeLength + 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        setText(builder, BufferType.SPANNABLE);
    }

}
