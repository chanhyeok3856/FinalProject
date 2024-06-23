package org.example.finalproject33;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

@Component
public class WebBrowserLauncher implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        openHomePage();
    }

    private void openHomePage() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI("http://localhost:8080"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
