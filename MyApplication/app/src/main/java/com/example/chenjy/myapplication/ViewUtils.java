package com.example.chenjy.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;

import java.util.Collection;
import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;


/**
 * Created by chenjy on 2019/4/18.
 */

public class ViewUtils {

    /**
     * Created by chenjy on 2019/4/12.
     */


    public void closeDefaultAnimator(RecyclerView mRvCustomer) {
        if (null == mRvCustomer) {
            return;
        }
        mRvCustomer.getItemAnimator().setAddDuration(0);
        mRvCustomer.getItemAnimator().setChangeDuration(0);
        mRvCustomer.getItemAnimator().setMoveDuration(0);
        mRvCustomer.getItemAnimator().setRemoveDuration(0);
        ((SimpleItemAnimator) mRvCustomer.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public void registerScrollChangeListener(RecyclerView recyclerView, OnScrollListener onScrollListener) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        boolean isTop = false;
                        boolean isBottom = false;
                        if (linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                            isTop = calcTopV(recyclerView);
                            if (!isTop) {
                                isBottom = calcBottomV(recyclerView);
                            }
                        } else {
                            isTop = calcTopH(recyclerView);
                            if (!isTop) {
                                isBottom = calcBottomH(recyclerView);
                            }
                        }
                        if (isTop) {
                            onScrollListener.onScrollTop();
                        } else if (isBottom) {
                            onScrollListener.onScrollBottom();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //得到第一个view
                View headView = recyclerView.getLayoutManager().getChildAt(0);
                if (headView == null) {
                    return;
                }
                //通过这个headView得到这个view当前的position值
                int headViewPosition = recyclerView.getLayoutManager().getPosition(headView);

                //得到当前显示的最后一个item的view
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
                if (lastChildView == null) {
                    return;
                }
                //得到lastChildView的bottom坐标值
                int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);

                onScrollListener.onVisibleItemChange(headViewPosition, lastPosition);

            }
        });
    }

    public boolean calcTopV(RecyclerView recyclerView) {
        //得到第一个view
        View headView = recyclerView.getLayoutManager().getChildAt(0);
        if (headView == null) {
            return false;
        }
        //通过这个headView得到这个view当前的position值
        int headViewPosition = recyclerView.getLayoutManager().getPosition(headView);
        //得到headView的bottom坐标值
        int headViewBottom = headView.getBottom();

        //得到recyclerview的第一个坐标减去底部padding值，也就是显示内容最顶部的坐标
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstChildPosition = layoutManager.findFirstVisibleItemPosition();
        //这里是判断前提是有加headview
        View firstChildView = layoutManager.getChildAt(firstChildPosition);
        if (firstChildView == null) {
            return false;
        }
        int firstChildBottom = firstChildView.getBottom();

        //如果两个条件都满足则说明是真正的滑动到了顶部
        //判断head是否处于可见状态
        if (headViewBottom == firstChildBottom && firstChildPosition == headViewPosition) {
            return true;
        }
        return false;
    }

    public boolean calcTopH(RecyclerView recyclerView) {
        //得到第一个view
        View headView = recyclerView.getLayoutManager().getChildAt(0);
        if (headView == null) {
            return false;
        }
        //通过这个headView得到这个view当前的position值
        int headViewPosition = recyclerView.getLayoutManager().getPosition(headView);
        //得到headView的bottom坐标值
        int headViewBottom = headView.getLeft();

        //得到recyclerview的第一个坐标减去底部padding值，也就是显示内容最顶部的坐标
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstChildPosition = layoutManager.findFirstVisibleItemPosition();
        //这里是判断前提是有加headview
        View firstChildView = layoutManager.getChildAt(firstChildPosition);
        if (firstChildView == null) {
            return false;
        }
        int firstChildBottom = firstChildView.getLeft();

        //如果两个条件都满足则说明是真正的滑动到了顶部
        //判断head是否处于可见状态
        if (headViewBottom == firstChildBottom && firstChildPosition == headViewPosition) {
            return true;
        }
        return false;
    }

    public boolean calcBottomV(RecyclerView recyclerView) {
        //得到当前显示的最后一个item的view
        View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
        if (lastChildView == null) {
            return false;
        }
        //得到lastChildView的bottom坐标值
        int lastChildBottom = lastChildView.getBottom();
        //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
        int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
        //通过这个lastChildView得到这个view当前的position值
        int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);
        //判断lastChildView的bottom值跟recyclerBottom 判断lastPosition是不是最后一个position 如果两个条件都满足则说明是真正的滑动到了底部
        if (lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
            return true;
        }
        return false;
    }

    public boolean calcBottomH(RecyclerView recyclerView) {
        //得到当前显示的最后一个item的view
        View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
        if (lastChildView == null) {
            return false;
        }
        //得到lastChildView的bottom坐标值
        int lastChildBottom = lastChildView.getRight();
        //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
        int recyclerBottom = recyclerView.getRight() - recyclerView.getPaddingRight();
        //通过这个lastChildView得到这个view当前的position值
        int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);
        //判断lastChildView的bottom值跟recyclerBottom 判断lastPosition是不是最后一个position 如果两个条件都满足则说明是真正的滑动到了底部
        if (lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
            return true;
        }
        return false;
    }


    public void getVisibleIndexs(RecyclerView recyclerView, OnScrollListener onScrollListener) {
        //得到当前显示的最后一个item的view
        View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
        if (lastChildView == null) {
            return;
        }
        //得到第一个view
        View headView = recyclerView.getLayoutManager().getChildAt(0);
        if (headView == null) {
            return;
        }
        //通过这个headView得到这个view当前的position值
        int headViewPosition = recyclerView.getLayoutManager().getPosition(headView);
        //得到lastChildView的bottom坐标值
        int lastChildBottom = lastChildView.getBottom();
        //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
        int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
        //通过这个lastChildView得到这个view当前的position值
        int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);

        onScrollListener.onVisibleItemChange(headViewPosition, lastPosition);
    }


    public static Pair<Integer, Integer> onViewMeasure(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        return new Pair<>(width, height);
    }


    /**
     * 获取当前recyclerview 一页可加载item的数量
     *
     * @param view
     */
    public int getVisibleCount(View view, int orientation) {
        int count = 0;
        Pair<Integer, Integer> pair = onViewMeasure(view);
        int[] pixels = getDisplayMetrics(view.getContext());
        int width = pair.first;
        int height = pair.second;
        int measure = LinearLayoutManager.VERTICAL == orientation ? height : width;
        //四舍五入取整数
        count = (int) Math.ceil(pixels[1] / measure);
        return count;
    }

    public int[] getDisplayMetrics(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density1 = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int[] pixels = new int[]{width, height};
        return pixels;
    }

    public boolean removeIf(Collection<UserVO> set, UserVO userVO) {
        boolean removed = false;
        final Iterator<UserVO> each = set.iterator();
        while (each.hasNext()) {
            if (each.next().getUserId() == userVO.getUserId()) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    /**
     * 滑动到指定位置
     */
    public void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        mRecyclerView.scrollToPosition(position);
    }


    public interface OnScrollListener {
        void onScrollBottom();

        void onScrollTop();

        void onVisibleItemChange(int start, int end);
    }

    public static abstract class SimpleScrollListener implements OnScrollListener {
        @Override
        public void onScrollBottom() {

        }

        @Override
        public void onScrollTop() {

        }

        @Override
        public void onVisibleItemChange(int start, int end) {

        }
    }

}
