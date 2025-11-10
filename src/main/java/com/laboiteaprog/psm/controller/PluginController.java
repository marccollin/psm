package com.laboiteaprog.psm.controller;

import com.laboiteaprog.psm.model.Plugin;
import com.laboiteaprog.psm.service.PluginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plugins")
public class PluginController {

    private final PluginService pluginService;

    public PluginController(PluginService pluginService) {
        this.pluginService=pluginService;
    }

    @PostMapping("/create/{id}")
    public String create(@PathVariable String id) {
        Plugin plugin = pluginService.createPlugin(id);
        return "Plugin créé : " + id + ", état initial : " + plugin.getState();
    }

    @PostMapping("/{id}/activate")
    public String activate(@PathVariable String id) {
        pluginService.activate(id);
        Plugin plugin = pluginService.getPlugin(id);
        return "Plugin activé : " + id + ", état : " + plugin.getState();
    }

    @PostMapping("/{id}/deactivate")
    public String deactivate(@PathVariable String id) {
        pluginService.deactivate(id);
        Plugin plugin = pluginService.getPlugin(id);
        return "Plugin désactivé : " + id + ", état : " + plugin.getState();
    }

    @PostMapping("/{id}/uninstall")
    public String uninstall(@PathVariable String id) {
        pluginService.uninstall(id);
        return "Plugin désinstallé : " + id;
    }

    @GetMapping("/{id}")
    public String getStatus(@PathVariable String id) {
        Plugin plugin = pluginService.getPlugin(id);
        if (plugin == null) {
            return "Plugin non trouvé";
        }
        return "Plugin " + id + ", état : " + plugin.getState();
    }
}
