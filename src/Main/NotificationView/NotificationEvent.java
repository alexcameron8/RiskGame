package Main.NotificationView;

public class NotificationEvent {
    private NotificationModel.Notification notification;
    private NotificationEventType notificationEventType;

    NotificationEvent(NotificationModel.Notification notification, NotificationEventType notificationEventType){
        this.notification = notification;
        this.notificationEventType = notificationEventType;
    }

    public NotificationModel.Notification getNotification() {
        return notification;
    }

    public NotificationEventType getNotificationEventType() {
        return notificationEventType;
    }

    public enum NotificationEventType {
        REMOVE,
        ADD,
        REMOVE_ALL
    }
}
