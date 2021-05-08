package com.weiwu.youxuanfinanciala.dmin.utils;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IntDef;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.weiwu.youxuanfinanciala.dmin.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SystemBarUtils {


    // ------------------------------------

    /**
     *  以下三种全屏模式，点击（屏幕任意区域）或者滑动system bar区域 显示(bring back ) system bar .在不同版本上是有区别的：
     *  API > 16: 状态栏和导航栏显示，
     *  14 <= API <=16 : 只显示导航栏，不显示状态栏，
     *  API < 14 ： 都不显示，因为   View.SYSTEM_UI_FLAG_FULLSCREEN （ 隐藏状态栏）是 大于16 才提供的，状态栏是 14 才开始提供的。
     */

    // --------------------------------------

    /**
     * 这种模式，适用于用户和屏幕交互不是很多的app，比如电影播放，用户需要重新显示 system bar 的时候只需要
     * 点击屏幕任何区域，system bar 就会被显示，但是有个问题就是，点击屏幕时点击事件会被系统消耗用于显示 system bar
     * 因此 app 是收不到点击事件的，只有当 system bar 显示出来后，再次点击 app 才会收到点击事件。
     */
    public static final int FULL_SCREEN_MODE_LEAN_BACK = 0X100;

    /**
     * 这种模式使用与用户和屏幕交互相对比较多的情况，比如游戏，相册，等app，用户会一直和屏幕交互，各种点击和触摸，在这种
     * 模式下，用户如果想重新显示 system bar ，需要用户手指按住 状态栏往下滑动或者按住底部导航栏往上滑动。和 LEAN BACK，一样
     * 滑动显示system bar 的事件 app 收不到。
     */
    public static final int FULL_SCREEN_MODE_IMMERSIVE = 0X101;


    /**
     * 这种模式和 FULL_SCREEN_MODE_IMMERSIVE 基本一样，但是有2个区别,1:就是用户在滑动 system bar 区域的时候，system bar 显示
     * 的同时 app 也会收到点击或者滑动事件 ,2: 状态栏和导航栏是半透明，
     */
    public static final int FULL_SCREEN_MODE_IMMERSIVE_STICKY = 0X102;


    @IntDef({FULL_SCREEN_MODE_LEAN_BACK, FULL_SCREEN_MODE_IMMERSIVE, FULL_SCREEN_MODE_IMMERSIVE_STICKY})
    @Retention(value = RetentionPolicy.SOURCE)
    public @interface FullScreenMode {

    }


    /**
     * Android 4.0 Api 14 才支持 dim （暗淡） system bar（ status bar + navigation bar）,
     * <p>
     * 当 system bar 被dim 后，一旦用户在屏幕上点击了 system bar 区域， flat View.SYSTEM_UI_FLAG_LOW_PROFILE
     * 将会被清楚，system bar 变得完全可见，一旦清除后，要想再次 dim  system bar ,必须调用该方法重新设置一次，
     *
     * @param activity
     */
    @RequiresApi(14)
    public static void dimSystemBar(AppCompatActivity activity) {
        // This example uses decor view, but you can use any visible view.
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * Android 4.0 Api 14 才支持 dim  system bar
     * <p>
     * 手动通过代码的方式 清除 View.SYSTEM_UI_FLAG_LOW_PROFILE，让 system bar 完全可见
     *
     * @param activity
     */
    @RequiresApi(14)
    public static void unDimSystemBar(AppCompatActivity activity) {
        // This example uses decor view, but you can use any visible view.
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
    }


    /**
     * 隐藏状态栏,内容布局大小会改变，隐藏后，这个设置将会一直有效，除非手动调用 {@link SystemBarUtils#showStatusBarNoReSizeContent} 方法去显示 状态栏
     * 注意：隐藏 system bar 包括（status bar 和 navigation bar）,当用户离开这个界面后，被隐藏的 system bar 会被重新显示（也就是之前设置的flag
     * 被清除了，因此你需要在用户重新回到这个界面时重新隐藏 system bar）
     *
     * @param activity 需要隐藏状态的activity
     */
    public static void hideStatusBar(AppCompatActivity activity) {

        if (Build.VERSION.SDK_INT <= 16) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = activity.getWindow().getDecorView();
            // Hide the status bar.
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }


    /**
     * 隐藏状态栏,内容布局大小不会改变，内容 behind 状态栏，隐藏后，这个设置将会一直有效，除非手动调用 {@link SystemBarUtils#showStatusBarNoReSizeContent} 方法去显示 状态栏
     * WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN 就是让我们内容 behind 到状态栏下，必须和 WindowManager.LayoutParams.FLAG_FULLSCREEN 结核使用
     * <p>
     * 注意：隐藏 system bar 包括（status bar 和 navigation bar）,当用户离开这个界面后，被隐藏的 system bar 会被重新显示（也就是之前设置的flag
     * 被清除了，因此你需要在用户重新回到这个界面时重新隐藏 system bar）
     *
     * @param activity 需要隐藏状态的activity
     */
    public static void hideStatusBarNotResizeContent(AppCompatActivity activity) {

        if (Build.VERSION.SDK_INT <= 16) {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            activity.getWindow().setAttributes(attributes);

        } else {
            View decorView = activity.getWindow().getDecorView();
            // Hide the status bar.
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN // 隐藏状态栏
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content layout 保持稳定（不要重新计算打下 resize）
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // content layout behind 状态栏
            );
        }
    }

    /**
     * 显示状态栏就是把  View.SYSTEM_UI_FLAG_FULLSCREEN // 隐藏状态栏 去掉，其他
     * View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content layout 保存稳定 ，View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 保留
     *
     * @param activity
     */
    public static void showStatusBarNoReSizeContent(AppCompatActivity activity) {

        if (Build.VERSION.SDK_INT <= 16) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content layout 保持稳定
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  // content layout behind 状态栏
            );
        }
    }

    /**
     * 显示状态栏就是把  View.SYSTEM_UI_FLAG_FULLSCREEN , content layout 会重新计算大小
     *
     * @param activity
     */
    public static void showStatusBar(AppCompatActivity activity) {

        if (Build.VERSION.SDK_INT <= 16) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(0);
        }
    }


    /**
     * 4.0 （API 14） 才提供的方法隐藏底部导航栏
     * <p>
     * 注意：隐藏 system bar 包括（status bar 和 navigation bar）,当用户离开这个界面后，被隐藏的 system bar 会被重新显示（也就是之前设置的flag
     * 被清除了，因此你需要在用户重新回到这个界面时重新隐藏 system bar）
     *
     * @param activity
     */
    public static void hideNavigationBar(AppCompatActivity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public static void showNavigationBar(AppCompatActivity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
    }

    /**
     * 4.0 （API 14） 才提供的方法隐藏底部导航栏
     * 注意：隐藏 system bar 包括（status bar 和 navigation bar）,当用户离开这个界面后，被隐藏的 system bar 会被重新显示（也就是之前设置的flag
     * 被清除了，因此你需要在用户重新回到这个界面时重新隐藏 system bar）
     *
     * @param activity
     */
    public static void hideNavigationBarNotResizeContent(AppCompatActivity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // 隐藏底部导航啦
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content layout 保持稳定
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 让 content layout(内容布局) behind navigation bar（底部导航栏）

        );
    }

    public static void showNavigationBarNotResizeContent(AppCompatActivity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content layout 保持稳定
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);// 让 content layout(内容布局) behind navigation bar（底部导航栏）);
    }

    /**
     * 显示系统bar
     *
     * @param activity
     */
    public static void showSystemBar(AppCompatActivity activity) {
        View decorView = activity.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT <= 16) {
            if (Build.VERSION.SDK_INT >= 14) {
                // 清除隐藏的导航栏，显示导航栏
                decorView.setSystemUiVisibility(0);

                // 清除 隐藏状态栏的 flag，显示状态栏
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                // 只能显示 status bar ，因为没有因此 navigation bar 的功能
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        } else {
            // 清除隐藏的导航栏和状态栏的 flag，显示状态栏和导航栏
            decorView.setSystemUiVisibility(0);
        }
    }

    /**
     * 显示系统bar
     *
     * @param activity
     */
    public static void showSystemBarNoResizeContent(AppCompatActivity activity) {
        View decorView = activity.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT <= 16) {
            if (Build.VERSION.SDK_INT >= 14) {
                //
                // 显示状态栏和底部导航栏，content layout behind system bar, 内容布局（content layout ） 不会重新计算大小 resize
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content layout 保持稳定（不要重新计算打下 resize）
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // content layout behind 状态栏
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 让 content layout(内容布局) behind navigation bar（底部导航栏）
                );
            } else {
                // 显示状态栏，因为api 小于14 没有隐藏导航栏的功能
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            }
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {
            //
            // 显示状态栏和底部导航栏，content layout behind system bar, 内容布局（content layout ） 不会重新计算大小 resize
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content layout 保持稳定（不要重新计算打下 resize）
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // content layout behind 状态栏
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 让 content layout(内容布局) behind navigation bar（底部导航栏）
            );

        }
    }

    /**
     * 隐藏系统bar (status bar  and  navigation bar)，内容布局会重新计算大小，这种方式也叫 lean back ，三种 全屏模式中的一种
     * lean back 模式下，只要用户点击屏幕的任何地方，system bar 将会 重新显示，如果你还想再次隐藏 system bar，需要重新调用该方法，
     * 可以通过设置setOnSystemUiVisibilityChangeListener监听，来判断 system bar 是否显示和隐藏。
     * 注意：1.隐藏 system bar 包括（status bar 和 navigation bar）,当用户离开这个界面后，被隐藏的 system bar 会被重新显示（也就是之前设置的flag
     * 被清除了，因此你需要在用户重新回到这个界面时重新隐藏 system bar）
     * 2.
     *
     * @param activity
     */
    public static void hideSystemBar(AppCompatActivity activity, @FullScreenMode int mode) {
        View decorView = activity.getWindow().getDecorView();

        if (Build.VERSION.SDK_INT <= 16) {

            if (Build.VERSION.SDK_INT >= 14) {
                // 隐藏底部导航，只有 api 14 才提供
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                // 隐藏状态栏
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                // 隐藏状态栏
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        } else {
            // 隐藏状态栏和底部导航啦
            int flag = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

            if (Build.VERSION.SDK_INT >= 19) {
                if (mode == FULL_SCREEN_MODE_IMMERSIVE) {
                    flag |= View.SYSTEM_UI_FLAG_IMMERSIVE; // 设置 IMMERSIVE 全屏模式
                } else if (mode == FULL_SCREEN_MODE_IMMERSIVE_STICKY) {
                    flag |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  // 设置IMMERSIVE_STICKY 全屏模式
                }
            }
            decorView.setSystemUiVisibility(flag);

        }

    }

    /**
     * 隐藏系统bar,让我们内容布局不重新计算大小 (status bar  and  navigation bar)
     * 注意：隐藏 system bar 包括（status bar 和 navigation bar）,当用户离开这个界面后，被隐藏的 system bar 会被重新显示（也就是之前设置的flag
     * 被清除了，因此你需要在用户重新回到这个界面时重新隐藏 system bar）
     *
     * @param activity
     */
    public static void hideSystemBarNoResizeContent(AppCompatActivity activity, @FullScreenMode int mode) {
        View decorView = activity.getWindow().getDecorView();

        if (Build.VERSION.SDK_INT <= 16) {
            if (Build.VERSION.SDK_INT >= 14) {
                // 隐藏底部导航栏，同时让 content layout behind navigation bar

                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // 隐藏底部导航啦
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content layout 保持稳定
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION); // 让 content layout(内容布局) behind navigation bar（底部导航栏）
                // 隐藏状态栏
                WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
                attributes.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN; // 让 content layout behind  状态栏
                activity.getWindow().setAttributes(attributes);

            } else {
                // 隐藏状态栏
                WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
                attributes.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN; // 让 content layout behind  状态栏
                activity.getWindow().setAttributes(attributes);
            }
        } else {
            // 隐藏状态栏和底部导航啦

            int flag = View.SYSTEM_UI_FLAG_FULLSCREEN // 隐藏状态栏
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content layout 保持稳定（不要重新计算打下 resize）
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // content layout behind 状态栏
                    // 上面三个flag 是控制状态栏，下面两个是控制底部导航栏
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //// 隐藏底部导航啦
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;// 让 content layout(内容布局) behind navigation bar（底部导航栏）

            if (Build.VERSION.SDK_INT >= 19) {
                if (mode == FULL_SCREEN_MODE_IMMERSIVE) {
                    flag |= View.SYSTEM_UI_FLAG_IMMERSIVE; // 设置 IMMERSIVE 全屏模式
                } else if (mode == FULL_SCREEN_MODE_IMMERSIVE_STICKY) {
                    flag |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  // 设置IMMERSIVE_STICKY 全屏模式
                }
            }
            decorView.setSystemUiVisibility(flag);

        }
    }


    // ----------------------------------------

    /**
     * 状态栏颜色修改包设置为透明或者设置为其他颜色，
     * 1.  19 <= API <= 20 只支持状态栏透明，不能设置其他颜色，如果想让状态栏看起来有颜色，只能让内容 behind 到状态栏，然后在状态栏区域添加一个
     *                      View 然后给这个View 设置颜色，这样视觉效果上状态栏是有颜色的。
     * 2.  API >= 21 : 支持修改状态颜色，包括透明
     * 3. API >= 23 : 支持修改状态栏字体和 系统icon 颜色，但是不能人为设置其他颜色，只能在系统的亮色（白色）和暗色（灰色）之间切换，
     *                比如状态栏颜色是白色，那么就需要把状态栏字体和系统icon 切换为 暗色，这样才看得见文字(系统日期)和 系统icon。
     */

    /**
     * 设置沉浸式状态栏
     *
     * @param isSetThemeStatusBarColor 是否对状态栏单独设置颜色
     */
    public static void setStatusBarColor(AppCompatActivity activity, boolean isSetThemeStatusBarColor, boolean isSetTextColor) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (isSetThemeStatusBarColor) {
                window.setStatusBarColor(activity.getResources().getColor(R.color.whiteBg));
            } else {
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = window.getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isSetTextColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 状态栏透明
     */
    public static void translucentStatusBar(AppCompatActivity activity) {

    }


}
