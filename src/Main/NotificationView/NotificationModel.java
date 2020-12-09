package Main.NotificationView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Model for notification views
 */
public class NotificationModel {
    ArrayList<Notification> notificationArrayList;
    ArrayList<NotificationViewListener> notificationViewListeners;

    /**
     * Create a new notification model.
     */
    NotificationModel(){
        this.notificationArrayList = new ArrayList<>();
        this.notificationViewListeners = new ArrayList<>();
    }

    /**
     * Add a new notification to the list of current notifications.
     * @param message Message to display in the notification.
     * @param notificationType Notification type.
     */
    public void addNotification(String message, NotificationType notificationType){
        Notification notification = new Notification(message, notificationType);
        this.notificationArrayList.add(notification);
        updateNotificationListeners(new NotificationEvent(notification, NotificationEvent.NotificationEventType.ADD));

    }

    /**
     * Remove a notification from thhe list of current notifications.
     * @param notification Notification to remove.
     */
    public void removeNotification(Notification notification){
        this.notificationArrayList.remove(notification);
        updateNotificationListeners(new NotificationEvent(notification, NotificationEvent.NotificationEventType.REMOVE));
    }

    /**
     * Remove all current notifications.
     */
    public void removeAllNotifications(){
        this.notificationArrayList.clear();
        updateNotificationListeners(new NotificationEvent(null, NotificationEvent.NotificationEventType.REMOVE_ALL));
    }

    /**
     * Get the list of current notifications.
     * @return List of notifications.
     */
    public ArrayList<Notification> getNotifications(){
        return this.notificationArrayList;
    }

    /**
     * Add a new notification listener.
     * @param nvl Notification listener.
     */
    public void addNotificationListener(NotificationViewListener nvl){
        this.notificationViewListeners.add(nvl);
    }

    /**
     * Update listeners.
     * @param notificationEvent Event to pass in update.
     */
    private void updateNotificationListeners(NotificationEvent notificationEvent){
        for(NotificationViewListener nvl: notificationViewListeners){
            nvl.handleNotificationUpdate(notificationEvent);
        }
    }

    /**
     * Available notification types.
     */
    public enum NotificationType {
        INFO,
        WARNING
    }

    /**
     * Notification class which displays the AI's moves to notify user what the AI did.
     */
    public class Notification {
        private String message;
        private NotificationType notificationType;
        private String timestamp;

        /**
         * Create a new notification.
         * @param message Message to display in notification
         * @param notificationType Type of notification.
         */
        Notification(String message, NotificationType notificationType){
            this.message = message;
            this.notificationType = notificationType;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            this.timestamp = dtf.format(now);
        }

        /**
         * Return the message of the notification.
         * @return Message of the notification.
         */
        public String getMessage() {
            return message;
        }

        /**
         * Change the message of a notification.
         * @param message New message.
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * Return the notification type.
         * @return Notification type.
         */
        public NotificationType getNotificationType() {
            return notificationType;
        }

        /**
         * Change the notification type.
         * @param notificationType New notification type.
         */
        public void setNotificationType(NotificationType notificationType) {
            this.notificationType = notificationType;
        }

        /**
         * Return the time the notification was created.
         * @return Time the notification was created.
         */
        public String getTimestamp() {
            return timestamp;
        }
    }
}
