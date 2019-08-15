package com.example.chenjy.myapplication.sketch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.SparseArray;

import com.example.chenjy.myapplication.R;

import me.panpf.sketch.display.TransitionImageDisplayer;
import me.panpf.sketch.process.GaussianBlurImageProcessor;
import me.panpf.sketch.request.DisplayOptions;
import me.panpf.sketch.request.DownloadOptions;
import me.panpf.sketch.request.LoadOptions;
import me.panpf.sketch.request.ShapeSize;
import me.panpf.sketch.shaper.CircleImageShaper;
import me.panpf.sketch.shaper.RoundRectImageShaper;
import me.panpf.sketch.state.DrawableStateImage;
import me.panpf.sketch.state.OldStateImage;
import me.panpf.sketch.util.SketchUtils;

/**
 * Created by chenjy on 2019/4/25.
 */

public class ImageOptions2 {
    /**
     * 通用矩形
     */
    public static final int RECT = 101;
    /**
     * 带描边的圆形
     */
    public static final int CIRCULAR_STROKE = 102;

    /**
     * 窗口背景
     */
    public static final int WINDOW_BACKGROUND = 103;

    /**
     * 圆角矩形
     */
    public static final int ROUND_RECT = 104;

    /**
     * 充满列表
     */
    public static final int LIST_FULL = 105;

    private SparseArray<OptionsHolder> OPTIONS_ARRAY = new SparseArray();

    public ImageOptions2() {
        init();
    }

    public void init() {
        TransitionImageDisplayer transitionImageDisplayer = new TransitionImageDisplayer();

        OPTIONS_ARRAY.append(ImageOptions.RECT, new OptionsHolder() {
            @Override
            protected DownloadOptions onCreateOptions(Context context) {
                return new DisplayOptions()
                        .setLoadingImage(R.drawable.image_loading)
                        .setErrorImage(R.drawable.image_error)
                        .setPauseDownloadImage(R.drawable.image_pause_download)
                        .setDisplayer(transitionImageDisplayer)
                        .setShapeSize(ShapeSize.byViewFixedSize());
            }
        });

        OPTIONS_ARRAY.append(ImageOptions.CIRCULAR_STROKE, new OptionsHolder() {
            @Override
            protected DownloadOptions onCreateOptions(Context context) {
                return new DisplayOptions()
                        .setLoadingImage(R.drawable.image_loading)
                        .setErrorImage(R.drawable.image_error)
                        .setPauseDownloadImage(R.drawable.image_pause_download)
                        .setDisplayer(transitionImageDisplayer)
                        .setShaper(new CircleImageShaper().setStroke(Color.WHITE, SketchUtils.dp2px(context, 1)))
                        .setShapeSize(ShapeSize.byViewFixedSize());
            }
        });

        OPTIONS_ARRAY.append(ImageOptions.WINDOW_BACKGROUND, new OptionsHolder() {
            @Override
            protected DownloadOptions onCreateOptions(Context context) {
                return new DisplayOptions()
                        .setLoadingImage(new OldStateImage(new DrawableStateImage(R.drawable.shape_window_background)))
                        .setProcessor(GaussianBlurImageProcessor.makeLayerColor(Color.parseColor("#66000000")))
                        .setCacheProcessedImageInDisk(true)
                        .setBitmapConfig(Bitmap.Config.ARGB_8888)   // 效果比较重要
                        .setShapeSize(ShapeSize.byViewFixedSize())
                        .setMaxSize(context.getResources().getDisplayMetrics().widthPixels / 4,
                                context.getResources().getDisplayMetrics().heightPixels / 4)
                        .setDisplayer(new TransitionImageDisplayer(true));
            }
        });

        OPTIONS_ARRAY.append(ImageOptions.ROUND_RECT, new OptionsHolder() {
            @Override
            protected DownloadOptions onCreateOptions(Context context) {
                return new DisplayOptions()
//                        .setLoadingImage(R.drawable.image_loading)
//                        .setErrorImage(R.drawable.image_error)
                        .setPauseDownloadImage(R.drawable.image_pause_download)
                        .setShaper(new RoundRectImageShaper(SketchUtils.dp2px(context, 6)))
                        .setDisplayer(transitionImageDisplayer);
//                        .setShapeSize(ShapeSize.byViewFixedSize());
            }
        });


        OPTIONS_ARRAY.append(ImageOptions.LIST_FULL, new OptionsHolder() {
            @Override
            protected DownloadOptions onCreateOptions(Context context) {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                return new DisplayOptions()
//                        .setLoadingImage(R.drawable.image_loading)
//                        .setErrorImage(R.drawable.image_error)
                        .setDecodeGifImage(true)
                        .setPauseDownloadImage(R.drawable.image_pause_download)
                        .setMaxSize(displayMetrics.widthPixels, displayMetrics.heightPixels)
                        .setDisplayer(transitionImageDisplayer);
            }
        });
    }

    public DisplayOptions getDisplayOptions(Context context, int optionsId) {
        return (DisplayOptions) OPTIONS_ARRAY.get(optionsId).getOptions(context);
    }

    public LoadOptions getLoadOptions(Context context, int optionsId) {
        return (LoadOptions) OPTIONS_ARRAY.get(optionsId).getOptions(context);
    }


    public DownloadOptions getDownloadOptions(Context context, int optionsId) {
        return (DownloadOptions) OPTIONS_ARRAY.get(optionsId).getOptions(context);
    }

    private abstract class OptionsHolder {
        private DownloadOptions options;

        public DownloadOptions getOptions(Context context) {
            if (options == null) {
                synchronized (this) {
                    if (options == null) {
                        options = onCreateOptions(context);
                    }
                }
            }
            return options;
        }

        protected abstract DownloadOptions onCreateOptions(Context context);
    }

}
