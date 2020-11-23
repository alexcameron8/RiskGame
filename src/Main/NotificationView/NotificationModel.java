package Main.NotificationView;

import java.util.ArrayList;

public class NotificationModel {
    ArrayList<Notification> notificationArrayList;
    ArrayList<NotificationViewListener> notificationViewListeners;

    NotificationModel(){
        this.notificationArrayList = new ArrayList<>();
        this.notificationViewListeners = new ArrayList<>();
    }

    public void addNotification(String message, NotificationType notificationType){
        Notification notification = new Notification(message, notificationType);
        this.notificationArrayList.add(notification);
        updateNotificationListeners(new NotificationEvent(notification, NotificationEvent.NotificationEventType.ADD));

    }

    public void removeNotification(Notification notification){
        this.notificationArrayList.remove(notification);
        updateNotificationListeners(new NotificationEvent(notification, NotificationEvent.NotificationEventType.REMOVE));
    }

    public ArrayList<Notification> getNotifications(){
        return this.notificationArrayList;
    }

    public void addNotificationListener(NotificationViewListener nvl){
        this.notificationViewListeners.add(nvl);
    }

    private void updateNotificationListeners(NotificationEvent notificationEvent){
        for(NotificationViewListener nvl: notificationViewListeners){
            nvl.handleNotificationUpdate(notificationEvent);
        }
    }

    public enum NotificationType {
        INFO,
        WARNING
    }

    public class Notification {
        private String message;
        private NotificationType notificationType;

        Notification(String message, NotificationType notificationType){
            this.message = message;
            this.notificationType = notificationType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public NotificationType getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(NotificationType notificationType) {
            this.notificationType = notificationType;
        }
    }
}
