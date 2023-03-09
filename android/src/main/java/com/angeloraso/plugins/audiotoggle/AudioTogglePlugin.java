package com.angeloraso.plugins.audiotoggle;

import android.content.Context;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.util.List;
import java.util.stream.Collectors;

@CapacitorPlugin(name = "AudioToggle")
public class AudioTogglePlugin extends Plugin {

    private AudioToggle audioToggle;

    public void load() {
        Context context = getContext();
        audioToggle = new AudioToggle(context, true);
    }

    @PluginMethod
    public void start(PluginCall call) {
        if (getActivity().isFinishing()) {
            call.reject("Audio toggle plugin error: App is finishing");
            return;
        }

        audioToggle.start(
            (audioDevices, audioDevice) -> {
                audioToggle.activate();
                JSObject res = new JSObject();
                List<String> availableDevices = audioDevices.stream().map(device -> device.getName()).collect(Collectors.toList());
                res.put("availableDevices", availableDevices);
                res.put("selectedDevice", audioDevice.getName());
                call.resolve(res);
            }
        );
    }

    @PluginMethod
    public void stop(PluginCall call) {
        if (getActivity().isFinishing()) {
            call.reject("Audio toggle plugin error: App is finishing");
            return;
        }

        audioToggle.stop();
        call.resolve();
    }

    @PluginMethod
    public void getDevices(PluginCall call) {
        if (getActivity().isFinishing()) {
            call.reject("Audio toggle plugin error: App is finishing");
            return;
        }

        JSObject res = new JSObject();
        List<String> availableDevices = audioToggle.availableAudioDevices
            .stream()
            .map(device -> device.getName())
            .collect(Collectors.toList());
        res.put("availableDevices", availableDevices);
        call.resolve(res);
    }

    @PluginMethod
    public void getSelectedDevice(PluginCall call) {
        if (getActivity().isFinishing()) {
            call.reject("Audio toggle plugin error: App is finishing");
            return;
        }

        JSObject res = new JSObject();
        AudioDevice device = audioToggle.selectedAudioDevice;
        res.put("selectedDevice", device.getName());
        call.resolve(res);
    }

    @PluginMethod
    public void selectDevice(PluginCall call) {
        if (getActivity().isFinishing()) {
            call.reject("Audio toggle plugin error: App is finishing");
            return;
        }

        String device = call.getString("device");

        if (device != null) {
            audioToggle.selectDevice(device);
            call.resolve();
        } else {
            call.reject("Audio toggle plugin error: Audio mode is required");
        }
    }
}
