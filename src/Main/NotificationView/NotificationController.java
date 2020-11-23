package Main.NotificationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handle UI events on the notification panel
 */
public class NotificationController implements ActionListener {
    private NotificationModel notificationModel;

    /**
     * Create a new notification controller
     * @param notificationModel UI's notification
     */
    public NotificationController(NotificationModel notificationModel) {
        this.notificationModel = notificationModel;
    }

    /**
     * Handle UI events.
     * @param e Event from UI.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            if(e.getActionCommand().equals(NotificationView.CLOSE_ALL_ACTION)){
                notificationModel.removeAllNotifications();
            }
        }
        if(e.getSource() instanceof NotificationView.GameNotification){
            NotificationModel.Notification notification = ((NotificationView.GameNotification) e.getSource()).getNotification();
            notificationModel.removeNotification(notification);
        }
    }
}
