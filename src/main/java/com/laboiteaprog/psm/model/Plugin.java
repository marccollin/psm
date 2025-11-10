package com.laboiteaprog.psm.model;

public class Plugin {

    private String id;
    private PluginState state;

    public Plugin(String id) {
        this.id = id;
        this.state = PluginState.INSTALLED; // Après installation
    }

    public String getId() { return id; }
    public PluginState getState() { return state; }
    public void setState(PluginState state) { this.state = state; }
}
