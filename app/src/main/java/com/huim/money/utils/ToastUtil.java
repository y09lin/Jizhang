package com.huim.money.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.huim.money.R;

import java.util.Random;

/**
 * 显示通知公告处理类
 */
public class ToastUtil {

    //显示Toast
    public static final int SHOW_TOAST_TYPE = 0;
    //显示Notify
    public static final int SHOW_NOTIFY_TYPE = 1;

    //显示时间短
    public static final int SHOW_TIME_SHORT = Toast.LENGTH_SHORT;
    //显示时间长
    public static final int SHOW_TIME_LONG = Toast.LENGTH_LONG;

    private static ToastUtil instance = null;
    private Context mContext;
    private final Handler mHandler;
    private NotificationManager mNotificationManager;
    private final Random random;

    private ToastUtil(Context context, Handler handler) {
        mContext = context.getApplicationContext();
        mHandler = handler;
        mNotificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        random = new Random(System.currentTimeMillis());
    }

    public static void init(Context context, Handler handler) {
        instance = new ToastUtil(context, handler);
    }

    public static ToastUtil getIns() {
        return instance;
    }

    /**
     * 显示提示消息
     *
     * @param msg 需要显示的消息
     */
    public void showNotification(String msg) {
        showNotification(msg, SHOW_TOAST_TYPE);
    }

    /**
     * 显示提示消息
     *
     * @param msg      需要显示的消息
     * @param showType 显示类型  SHOW_TOAST_TYPE 显示成Toast
     *                 SHOW_NOTIFY_TYPE 显示成Notify
     */
    public void showNotification(String msg, int showType) {
        showNotification(msg, showType, SHOW_TIME_SHORT);
    }

    /**
     * 显示提示消息
     *
     * @param msg          需要显示的消息
     * @param showType     显示类型  SHOW_TOAST_TYPE 显示成Toast
     *                     SHOW_NOTIFY_TYPE 显示成Notify
     * @param showTimeType 显示时长，使用Toast的时长  SHOW_TIME_SHORT 2s SHOW_TIME_LONG 5s
     */
    public void showNotification(String msg, int showType, int showTimeType) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (SHOW_NOTIFY_TYPE == showType) {
            int nodeId = random.nextInt();
            showNotification(nodeId, msg, msg);
            long delayMillis = 2000;
            if (showTimeType == SHOW_TIME_LONG) {
                delayMillis = 5000;
            }
            delayMillis += msg.length() / 15 * 500;
            mHandler.postDelayed(new RemoveNotifyRunnable(nodeId), delayMillis);
        } else {
            Toast.makeText(mContext, msg, showTimeType).show();
        }
    }

    public void showToast(final String msg) {
        if (TextUtils.isEmpty(msg)){
            return;
        }
        showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    public void showToast(final String msg, final int duration) {
        showToast(mContext, msg, duration);
    }

    public void showToast(final Context activity, final String msg, final int duration) {
        if(!SystemUtils.isMainThread()){
            if(activity instanceof Activity){
                ((Activity) activity).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, msg, duration).show();
                    }
                });
                return;
            }else{
                try {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, msg, duration).show();
                        }
                    });
                    return;
                } catch (Exception e) {
                }
            }
        }

        Toast.makeText(activity, msg, duration).show();
    }

    /**
     * 显示Toast消息，并保证运行在UI线程中
     *
     * @param context 上下文句柄
     * @param msg 需要显示的消息
     * @param duration 执行时间
     * @param postion  位置
     */
    public static void showToast(final Context context, final String msg,
                                 final int duration, final int postion) {
        Toast toast = Toast.makeText(context, msg, duration);
        toast.setGravity(postion, 0, 0);
        toast.show();
    }

    /**
     * 显示通知
     *
     * @param id         消息id
     * @param tickerText
     * @param msg        显示的消息
     */
    public void showNotification(int id, String tickerText, String msg) {
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Notification.Builder builder = new Notification.Builder(mContext);
//			builder.setLights(Color.argb(0xff,0,0xff,0), 300, 1000)
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(tickerText)
                .setContentTitle(msg)
                .setContentText("")
                .setContentIntent(pendingIntent);
//        builder.setDefaults(Notification.DEFAULT_SOUND);
        Notification notification = builder.getNotification();
        notification.flags = (Notification.FLAG_AUTO_CANCEL | notification.flags);
        mNotificationManager.notify(id, notification);
    }

    /**
     * 取消通知
     *
     * @param id
     */
    public void cancelNotification(int id) {
        mNotificationManager.cancel(id);
    }

    /**
     * 移除消息Runnable
     *
     * @author panxiaohe
     */
    public class RemoveNotifyRunnable implements Runnable {

        private int noteId = 0;

        public RemoveNotifyRunnable(int noteId) {
            super();
            this.noteId = noteId;
        }

        @Override
        public void run() {
            cancelNotification(noteId);
        }
    }
}
