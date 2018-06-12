package com.dm.listener;

import com.dm.action.ActionCommand;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jrichard on 30/06/2017.
 */
public class ActionListenerRegistry {

    private static Map<ActionListener, String> actionListenerMap = new HashMap<>();

    public static void add(ActionListener actionListener, String command) {
        actionListenerMap.put(actionListener, command);
    }

    public static List<ActionListener> get(String command) {
        List<ActionListener> result = new ArrayList<>();
        for (Map.Entry<ActionListener, String> entry : actionListenerMap.entrySet()) {
            if (entry.getValue().equals(command)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public static void fireActionListeners(ActionCommand actionCommand, ActionEvent actionEvent) {
        for (ActionListener actionListener : ActionListenerRegistry.get(actionCommand.name())) {
            actionListener.actionPerformed(actionEvent);
        }
    }
}
