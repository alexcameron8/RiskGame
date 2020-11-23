package Main.NotificationView;

/**
 * Events regarding notifications.
 */
public class NotificationEvent {
    private NotificationModel.Notification notification;
    private NotificationEventType notificationEventType;

    /**
     * Create a new notification event.
     * @param notification Notification being updated.
     * @param notificationEventType Type of update.
     */
    NotificationEvent(NotificationModel.Notification notification, NotificationEventType notificationEventType){
        this.notification = notification;
        this.notificationEventType = notificationEventType;
    }

    /**
     * Get the notification of a notification event.
     * @return The notification.
     */
    public NotificationModel.Notification getNotification() {
        return notification;
    }

    /**
     * Get the type of notification being updated.
     * @return Event type.
     */
    public NotificationEventType getNotificationEventType() {
        return notificationEventType;
    }

    /**
     * Notification type.
     */
    public enum NotificationEventType {
        REMOVE,
        ADD,
        REMOVE_ALL
    }
}
