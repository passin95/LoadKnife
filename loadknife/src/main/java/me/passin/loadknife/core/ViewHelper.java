package me.passin.loadknife.core;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 18:07
 * @desc:
 */
public class ViewHelper {

    private SparseArray<View> views;
    private View mRootView;
    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    public ViewHelper(View rootView) {
        mRootView = rootView;
    }

    /**
     * Will set the text of a TextView.
     *
     * @param viewId The view id.
     * @param value The text to put in the text view.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    /**
     * Will set the image of an ImageView from a resource id.
     *
     * @param viewId The view id.
     * @param strId The string resource id.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setText(@IdRes int viewId, @StringRes int strId) {
        TextView view = getView(viewId);
        view.setText(strId);
        return this;
    }

    /**
     * Will set the image of an ImageView from a resource id.
     *
     * @param viewId The view id.
     * @param imageResId The image resource id.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * Will set background color of a view.
     *
     * @param viewId The view id.
     * @param color A color, not a resource id.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * Will set background of a view.
     *
     * @param viewId The view id.
     * @param backgroundRes A resource to use as a background.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * Will set text color of a TextView.
     *
     * @param viewId The view id.
     * @param textColor The text color (not a resource id).
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    /**
     * Set the enabled state of this view.
     *
     * @param viewId The view id.
     * @param enable The enable
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setEnable(@IdRes int viewId, boolean enable) {
        View view = getView(viewId);
        view.setEnabled(enable);
        return this;
    }

    /**
     * Will set the image of an ImageView from a drawable.
     *
     * @param viewId The view id.
     * @param drawable The image drawable.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * Add an action to set the image of an image view. Can be called multiple times.
     */
    public ViewHelper setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Add an action to set the alpha of a view. Can be called multiple times.
     * Alpha between 0-1.
     */
    public ViewHelper setAlpha(@IdRes int viewId, @FloatRange(from = 0, to = 1) float value) {
        View view = getView(viewId);
        view.setAlpha(value);
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     *
     * @param viewId The view id.
     * @param visible True for VISIBLE, false for GONE.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setGone(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or INVISIBLE (false).
     *
     * @param viewId The view id.
     * @param visible True for VISIBLE, false for INVISIBLE.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setVisible(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    /**
     * Add links into a TextView.
     *
     * @param viewId The id of the TextView to linkify.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper linkify(@IdRes int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     * Apply the typeface to the given viewId, and enable subpixel rendering.
     */
    public ViewHelper setTypeface(@IdRes int viewId, Typeface typeface) {
        TextView view = getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    /**
     * Sets the progress of a ProgressBar.
     *
     * @param viewId The view id.
     * @param progress The progress.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the progress and max of a ProgressBar.
     *
     * @param viewId The view id.
     * @param progress The progress.
     * @param max The max value of a ProgressBar.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the range of a ProgressBar to 0...max.
     *
     * @param viewId The view id.
     * @param max The max value of a ProgressBar.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setMax(@IdRes int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) and max of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @param max The range of the RatingBar to 0...max.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the adapter of a view.
     *
     * @param viewId The view id.
     * @param adapter The adapter;
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setAdapter(@IdRes int viewId, Adapter adapter) {
        AdapterView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    /**
     * Sets the checked status of a checkable.
     *
     * @param viewId The view id.
     * @param checked The checked status;
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setChecked(@IdRes int viewId, boolean checked) {
        View view = getView(viewId);
        // View unable cast to Checkable
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param tag The tag;
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    /**
     * Sets the listview or gridview's item selected click listener of the view
     *
     * @param viewId The view id.
     * @param listener The item selected click listener;
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setOnItemSelectedClickListener(@IdRes int viewId,
            AdapterView.OnItemSelectedListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemSelectedListener(listener);
        return this;
    }

    /**
     * Sets the on checked change listener of the view.
     *
     * @param viewId The view id.
     * @param listener The checked change listener of compound button.
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setOnCheckedChangeListener(@IdRes int viewId,
            CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param key The key of tag;
     * @param tag The tag;
     * @return The ViewHelper for chaining.
     */
    public ViewHelper setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * add onClick view id.
     *
     * @param viewId add the view id
     * @return The ViewHelper for chaining.
     */
    public ViewHelper addOnClickListener(@IdRes final int... viewId) {
        for (int id : viewId) {
            final View view = getView(id);
            if (view != null) {
                if (!view.isClickable()) {
                    view.setClickable(true);
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickListener != null) {
                            onClickListener
                                    .onClick(v);
                        }
                    }
                });
            }
        }
        return this;
    }

    /**
     * add onLongClick view id.
     *
     * @param viewId add the view id
     * @return The ViewHelper for chaining.
     */
    public ViewHelper addOnLongClickListener(@IdRes final int... viewId) {
        for (int id : viewId) {
            final View view = getView(id);
            if (view != null) {
                if (!view.isClickable()) {
                    view.setLongClickable(true);
                }
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (onLongClickListener != null) {
                            return onLongClickListener.onLongClick(v);
                        }
                        return false;
                    }
                });
            }
        }
        return this;
    }

    public ViewHelper setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public ViewHelper setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
        return this;
    }

    /**
     * from findViewById get View
     */
    public <T extends View> T getView(@IdRes int viewId) {
        if (views == null) {
            views = new SparseArray<>();
        }
        View view = views.get(viewId);
        if (null == view) {
            view = mRootView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getRootView() {
        return mRootView;
    }

}
