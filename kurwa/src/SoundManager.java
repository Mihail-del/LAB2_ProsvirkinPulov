/*
 * File: GBricksPreview.java
 * -------------------
 * Name: Mykhailo Pulov, Veronika Prosvirkin
 * Class Leader: Veronika
 *
 * This file is a class for uploading and using sounds.
 */

import javax.sound.sampled.*;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

    public final class SoundManager {
        // Кеш завантажених звуків: назва -> Clip
        private static final Map<String, Clip> clips =
                Collections.synchronizedMap(new HashMap<>());

        private SoundManager() {}


        public static void loadFromResource(String name, String resourcePath) {
            try {
                URL url = SoundManager.class.getResource(resourcePath);
                if (url == null) {
                    System.err.println("[SoundManager] Resource not found: " + resourcePath);
                    return;
                }
                try (AudioInputStream ais = AudioSystem.getAudioInputStream(url)) {
                    Clip clip = AudioSystem.getClip();
                    clip.open(ais);
                    clips.put(name, clip);
                }
            } catch (Exception e) {
                System.err.println("[SoundManager] Failed to load: " + resourcePath);
                e.printStackTrace();
            }
        }


        public static void play(String name) {
            Clip c = clips.get(name);
            if (c == null) {
                return;
            }
            if (c.isRunning()) {
                c.stop();
            }
            c.setFramePosition(0);
            c.start();
        }

    }

