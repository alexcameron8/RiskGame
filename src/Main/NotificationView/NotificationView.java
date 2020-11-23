package Main.NotificationView;

import Main.Map.MapView;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * View to display notifications to the user.
 */
public class NotificationView extends JPanel implements NotificationViewListener{
    public static final String CLOSE_ALL_ACTION = "closeAll";
    private JPanel notificationList;
    private NotificationModel notificationModel;
    private NotificationController notificationController;


    /**
     * Create new notification view.
     */
    public NotificationView(){
        notificationModel = new NotificationModel();
        notificationModel.addNotificationListener(this);
        notificationController = new NotificationController(notificationModel);

        this.setLayout(new BorderLayout());

        notificationList = new JPanel();
        notificationList.setLayout(new GridBagLayout());

        JButton closeAll = new JButton("Close All");
        closeAll.setActionCommand(CLOSE_ALL_ACTION);
        closeAll.addActionListener(notificationController);

        this.add(closeAll, BorderLayout.PAGE_START);
        this.add(new JScrollPane(notificationList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);

    }

    /**
     * Display a message to the user.
     * @param message Message
     * @param notificationType Type of notification
     */
    public void notifyUser(String message, NotificationModel.NotificationType notificationType){
        this.notificationModel.addNotification(message, notificationType);
    }

    /**
     * Handle notification updates.
     * @param e NotificationEvent object.
     */
    @Override
    public void handleNotificationUpdate(NotificationEvent e) {
        NotificationModel.Notification notification = e.getNotification();
        NotificationEvent.NotificationEventType notificationEventType = e.getNotificationEventType();

        if(notificationEventType == NotificationEvent.NotificationEventType.ADD){
            GameNotification gameNotification = new GameNotification(notification);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            notificationList.add(gameNotification, gbc, 0);
        } else if(notificationEventType == NotificationEvent.NotificationEventType.REMOVE){
            Component removeComponent = null;
            for(Component component: notificationList.getComponents()){
                if(component instanceof GameNotification){
                    GameNotification gameNotification = (GameNotification) component;
                    if(notification == gameNotification.getNotification()){
                        removeComponent = gameNotification;
                        break;
                    }
                }
            }
            if(Objects.nonNull(removeComponent)){
                notificationList.remove(removeComponent);
            }
        } else if(notificationEventType == NotificationEvent.NotificationEventType.REMOVE_ALL){
            notificationList.removeAll();
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * Individual notification frame
     */
    public class GameNotification extends JPanel {
        private NotificationModel.Notification notification;
        public final String CLOSE_ACTION_COMMAND = "close";

        /**
         * Create a new notification frame
         * @param notification Notification to display in the frame
         */
        GameNotification(NotificationModel.Notification notification){
            this.setLayout(new BorderLayout());
            this.notification = notification;
            JLabel typeLabel;
            if(this.notification.getNotificationType() == NotificationModel.NotificationType.INFO){
                typeLabel = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));
            } else {
                typeLabel = new JLabel(UIManager.getIcon("OptionPane.warningIcon"));
            }
            this.add(typeLabel, BorderLayout.LINE_START);

            JTextArea notificationTextArea = new JTextArea(2, 20);
            notificationTextArea.setText(this.notification.getMessage());
            notificationTextArea.append("\n" + notification.getTimestamp());
            notificationTextArea.setWrapStyleWord(true);
            notificationTextArea.setLineWrap(true);
            notificationTextArea.setOpaque(false);
            notificationTextArea.setEditable(false);
            notificationTextArea.setFocusable(false);
            notificationTextArea.setBackground(UIManager.getColor("Label.background"));
            notificationTextArea.setFont(UIManager.getFont("Label.font"));
            notificationTextArea.setBorder(UIManager.getBorder("Label.border"));
            this.add(notificationTextArea, BorderLayout.CENTER);

            JButton closeNotificationButton = new JButton("X");
            closeNotificationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NotificationView.this.notificationController.actionPerformed(
                            new ActionEvent(GameNotification.this, ActionEvent.ACTION_PERFORMED, CLOSE_ACTION_COMMAND));
                }
            });
            this.add(closeNotificationButton, BorderLayout.LINE_END);

            this.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        }

        /**
         * Return the notification displayed in the frame.
         * @return Notification of the frame.
         */
        public NotificationModel.Notification getNotification() {
            return notification;
        }
    }
}
