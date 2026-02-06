package com.revplay;

import com.revplay.service.MusicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MusicServiceTest {

    private final MusicService musicService = new MusicService();

    @Test
    void testBrowseSongsDoesNotCrash() {

        Assertions.assertDoesNotThrow(
                () -> musicService.browseSongs(),
                "Browsing songs should not throw exception"
        );
    }
}
