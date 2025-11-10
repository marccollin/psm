package com.laboiteaprog.psm.action;

import com.laboiteaprog.psm.model.Plugin;
import com.laboiteaprog.psm.model.PluginEvent;
import com.laboiteaprog.psm.model.PluginState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class DeactivatePluginAction implements Action<PluginState, PluginEvent> {

    private static final Logger log = LoggerFactory.getLogger(DeactivatePluginAction.class);

    @Override
    public void execute(StateContext<PluginState, PluginEvent> context) {
        Plugin plugin = (Plugin) context.getMessage().getHeaders().get("plugin");
        log.info("Plugin désactivé : " + plugin.getId());
    }
}
