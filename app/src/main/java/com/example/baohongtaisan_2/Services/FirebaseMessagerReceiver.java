package com.example.baohongtaisan_2.Services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.baohongtaisan_2.Activity.User.HomeActivity;
import com.example.baohongtaisan_2.Model.IsLogin;
import com.example.baohongtaisan_2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Objects;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FirebaseMessagerReceiver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Map<String, String> stringMap = message.getData();
            if (IsLogin.getInstance().getTenPQ().equals("Admin") && stringMap.get("LoaiNoti").equals("UserToAdmin"))
            {
                String title = stringMap.get("TenP") + ": " + stringMap.get("TenTS");
                String body = "";
                if (Integer.parseInt(Objects.requireNonNull(stringMap.get("TinhTrang"))) == 1) {
                    body = "Hư hỏng nhẹ (Minor)";
                } else if (Integer.parseInt(Objects.requireNonNull(stringMap.get("TinhTrang")))  == 2) {
                    body = "Hư hỏng trung bình (Moderate)";
                } else if (Integer.parseInt(Objects.requireNonNull(stringMap.get("TinhTrang")))  == 3) {
                    body = "Hư hỏng nghiêm trọng (Severe)";
                } else if (Integer.parseInt(Objects.requireNonNull(stringMap.get("TinhTrang")))  == 4) {
                    body = "Hư hỏng hoàn toàn (Critical)";
                }
                showNotification(stringMap.get("MaND"),title, body);
            }else if (IsLogin.getInstance().getTenPQ().equals("User") && stringMap.get("LoaiNoti").equals("AdminToUser"))
            {
                String title = stringMap.get("TenP") + ": " + stringMap.get("TenTS");
                String body = "";
                 if (Integer.parseInt(Objects.requireNonNull(stringMap.get("TrangThai")))  == 2) {
                    body = "Thiết bị mà bạn báo hỏng đã đổi trạng thái sang: Đã tiếp nhận báo hỏng";
                } else if (Integer.parseInt(Objects.requireNonNull(stringMap.get("TrangThai")))  == 3) {
                    body = "Thiết bị mà bạn báo hỏng đã đổi trạng thái sang: Đang sửa chữa";
                } else if (Integer.parseInt(Objects.requireNonNull(stringMap.get("TrangThai")))  == 4) {
                    body = "Thiết bị mà bạn báo hỏng đã đổi trạng thái sang: Sửa thành công";
                }else if (Integer.parseInt(Objects.requireNonNull(stringMap.get("TrangThai")))  == 5) {
                    body = "Thiết bị mà bạn báo hỏng đã đổi trạng thái sang: Sửa không thành công";
                }
                showNotification(stringMap.get("MaND"),title, body);
            }


        }
    }

    private void showNotification(String MaND, String title, String body) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String channelID = MaND;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setSmallIcon(R.drawable.logo_tdmu_2)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);
        builder = builder.setContent(customNotification(title, body));
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelID, "test", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, builder.build());

    }


    public RemoteViews customNotification(String title, String body) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.txtTitle_noti, title);
        remoteViews.setTextViewText(R.id.txtBody_noti, body);
        return remoteViews;
    }

}
