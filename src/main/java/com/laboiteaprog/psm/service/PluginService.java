package com.laboiteaprog.psm.service;

import com.laboiteaprog.psm.model.Plugin;
import com.laboiteaprog.psm.model.PluginEvent;
import com.laboiteaprog.psm.model.PluginState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PluginService {

    private final Map<String, Plugin> plugins = new ConcurrentHashMap<>();

    @Autowired
    private StateMachineFactory<PluginState, PluginEvent> stateMachineFactory;

    public Plugin createPlugin(String id) {
        Plugin plugin = new Plugin(id);
        plugins.put(id, plugin);
        return plugin;
    }

    public Plugin getPlugin(String id) {
        return plugins.get(id);
    }

    public void activate(String id) {
        Plugin plugin = getPlugin(id);
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin non trouvé : " + id);
        }

        StateMachine<PluginState, PluginEvent> stateMachine = stateMachineFactory.getStateMachine();

        Message<PluginEvent> message = MessageBuilder
                .withPayload(PluginEvent.ACTIVATE)
                .setHeader("plugin", plugin)
                .build();

        stateMachine.sendEvent(message);
        plugin.setState(stateMachine.getState().getId());
    }

    public void deactivate(String id) {
        Plugin plugin = getPlugin(id);
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin non trouvé : " + id);
        }

        StateMachine<PluginState, PluginEvent> stateMachine = stateMachineFactory.getStateMachine();

        Message<PluginEvent> message = MessageBuilder
                .withPayload(PluginEvent.DEACTIVATE)
                .setHeader("plugin", plugin)
                .build();

        stateMachine.sendEvent(message);
        plugin.setState(stateMachine.getState().getId());
    }

    public void uninstall(String id) {
        Plugin plugin = getPlugin(id);
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin non trouvé : " + id);
        }

        StateMachine<PluginState, PluginEvent> stateMachine = stateMachineFactory.getStateMachine();

        Message<PluginEvent> message = MessageBuilder
                .withPayload(PluginEvent.UNINSTALL)
                .setHeader("plugin", plugin)
                .build();

        stateMachine.sendEvent(message);
        plugin.setState(stateMachine.getState().getId());
        plugins.remove(id); // Supprimer du stockage
    }

}
