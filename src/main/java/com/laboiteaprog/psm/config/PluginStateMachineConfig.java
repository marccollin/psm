package com.laboiteaprog.psm.config;

import com.laboiteaprog.psm.action.ActivatePluginAction;
import com.laboiteaprog.psm.action.DeactivatePluginAction;
import com.laboiteaprog.psm.action.FailPluginAction;
import com.laboiteaprog.psm.model.PluginEvent;
import com.laboiteaprog.psm.model.PluginState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class PluginStateMachineConfig extends StateMachineConfigurerAdapter<PluginState, PluginEvent> {

    private final ActivatePluginAction activateAction;
    private final DeactivatePluginAction deactivateAction;
    private final FailPluginAction failAction;

    public PluginStateMachineConfig(ActivatePluginAction activateAction,
                                    DeactivatePluginAction deactivateAction,
                                    FailPluginAction failAction) {
        this.activateAction=activateAction;
        this.deactivateAction=deactivateAction;
        this.failAction=failAction;
    }

    @Override
    public void configure(StateMachineStateConfigurer<PluginState, PluginEvent> states) throws Exception {
        states
                .withStates()
                .initial(PluginState.INSTALLED)
                .states(EnumSet.allOf(PluginState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<PluginState, PluginEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(PluginState.INSTALLED)
                .target(PluginState.ACTIVE)
                .event(PluginEvent.ACTIVATE)
                .action(activateAction)
                .and()
                .withExternal()
                .source(PluginState.ACTIVE)
                .target(PluginState.INACTIVE)
                .event(PluginEvent.DEACTIVATE)
                .action(deactivateAction)
                .and()
                .withExternal()
                .source(PluginState.INACTIVE)
                .target(PluginState.ACTIVE)
                .event(PluginEvent.ACTIVATE)
                .action(activateAction)
                .and()
                .withExternal()
                .source(PluginState.ACTIVE)
                .target(PluginState.UPDATING)
                .event(PluginEvent.UPDATE)
                .and()
                .withExternal()
                .source(PluginState.UPDATING)
                .target(PluginState.ACTIVE)
                .event(PluginEvent.ACTIVATE)
                .action(activateAction)
                .and()
                .withExternal()
                .source(PluginState.INSTALLED)
                .target(PluginState.UNINSTALLED)
                .event(PluginEvent.UNINSTALL)
                .and()
                .withExternal()
                .source(PluginState.ACTIVE)
                .target(PluginState.FAILED)
                .event(PluginEvent.ERROR)
                .action(failAction)
                .and()
                .withExternal()
                .source(PluginState.INACTIVE)
                .target(PluginState.FAILED)
                .event(PluginEvent.ERROR)
                .action(failAction)
                .and()
                .withExternal()
                .source(PluginState.FAILED)
                .target(PluginState.UNINSTALLED)
                .event(PluginEvent.UNINSTALL);
    }
}
