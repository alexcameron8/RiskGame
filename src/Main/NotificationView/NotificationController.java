package Main.NotificationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificationController implements ActionListener {
    private NotificationModel notificationModel;
    public NotificationController(NotificationModel notificationModel) {
        this.notificationModel = notificationModel;
    }

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
