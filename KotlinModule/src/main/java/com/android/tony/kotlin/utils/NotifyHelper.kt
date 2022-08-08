package com.android.tony.kotlin.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.android.tony.kotlin.R

class NotifyHelper {
    private val NOTIFICATION_CHANNEL_ID = "liberty"

    fun showNotification(context: Context, title: String, content: String, imgUrl: String, intent: Intent, channelName: String, pushId: Int, isPendingBroadcast: Boolean) {
        val bitmap: Bitmap? = null
        val width = context.resources.getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fabOffsetEndMode)
        if (!TextUtils.isEmpty(imgUrl)) {
            //            try {
            //                bitmap = BitmapFactory.decodeFile(GlideUtils.getAbsolutePath(context, imgUrl, width, width));
            //            } catch (ExecutionException e) {
            //                e.printStackTrace();
            //            } catch (InterruptedException e) {
            //                e.printStackTrace();
            //            }
        }

        var notification: Notification? = null
        val channelId = NOTIFICATION_CHANNEL_ID
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//            notification = buildBigNotification(context, title, content, bitmap, intent, isPendingBroadcast)
//        else
            notification = buildNormalNotification(context, title, content, bitmap, intent, isPendingBroadcast)
        val notifyManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = genNotificationChannel(channelId, channelName)
            notifyManager.createNotificationChannel(channel)
        }
        //        context.startForeground(LIBERTY_GAME_NOTIFICATION_ID, notification);
        notifyManager.notify(pushId, notification)
        Log.d("NotifyHelper", "callback(GamePushNotificationHelper.java : 183) ------------------>>>>>>>>> " + "")
    }

    fun buildNormalNotification(context: Context, title: String, content: String, albumart: Bitmap?, intent: Intent, isPendingBroadcast: Boolean): Notification {
        Log.d("NotifyHelper", "buildNormalNotification(GamePushNotificationHelper.java : 82) ------------------>>>>>>>>> " + "")
        val notificationBuilder = getNotificationCompatBuilder(context, NOTIFICATION_CHANNEL_ID)

        notificationBuilder.setOngoing(false)
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setTicker(title)
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX)
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
        //        notificationBuilder.setContent(createNormalNotificationView(context, title, content, albumart));

        val requesCode = System.currentTimeMillis().toInt()

        var pendingIntent: PendingIntent? = null
        if (isPendingBroadcast) {
            pendingIntent = PendingIntent.getBroadcast(context, requesCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            pendingIntent = PendingIntent.getActivity(context, requesCode, intent, PendingIntent.FLAG_ONE_SHOT)
        }
        notificationBuilder.setContentIntent(pendingIntent)

        val notification = notificationBuilder.build()
        notification.contentView = createNormalNotificationView(context, title, content, albumart)
        //        notification.flags = Notification.FLAG_FOREGROUND_SERVICE | Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        return notification
    }

    fun getEmptyNotification(context: Context, channelId: String, channelName: String): Notification {
        val notificationBuilder = getNotificationCompatBuilder(context, channelId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (nm != null) {
                val channel = genNotificationChannel(channelId, channelName)
                nm.createNotificationChannel(channel)
            }
        }
        return notificationBuilder.build()
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    fun genNotificationChannel(id: String, name: String): NotificationChannel {
        return getNotificationChannel(id, name, false, NotificationManager.IMPORTANCE_DEFAULT, false)
    }

    private fun getNotificationChannel(id: String, name: String, isEnableLight: Boolean, importance: Int, isSilent: Boolean): NotificationChannel {
        var channel = NotificationChannel(id, name, importance)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = NotificationChannel(id, name, importance)
            channel.enableLights(isEnableLight)
            if (isSilent)
                channel.setSound(null, null)
        }
        return channel
    }

    fun getNotificationCompatBuilder(context: Context, channelId: String): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationCompat.Builder(context, channelId)
        else
            NotificationCompat.Builder(context)
    }


    private fun createNormalNotificationView(context: Context, title: String, description: String, image: Bitmap?): RemoteViews {
        var image = image
        Log.d("NotifyHelper", "createNormalNotificationView(GamePushNotificationHelper.java : 61) ------------------>>>>>>>>> " + "")
        val notificationView = RemoteViews(context.packageName, R.layout.layout_normal_notification)
        if (image == null)
            image = BitmapFactory.decodeResource(context.resources, R.drawable.image_push_bg)
        notificationView.setImageViewBitmap(R.id.image, image)
        notificationView.setTextViewText(R.id.title, title)
//        notificationView.setTextViewText(R.id.description, description)

        return notificationView
    }

}