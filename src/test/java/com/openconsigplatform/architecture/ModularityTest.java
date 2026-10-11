package com.openconsigplatform.architecture;

import com.openconsigplatform.OpenConsigPlatformApplication;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTest {

    @Test
    void applicationModulesMustRespectDeclaredBoundaries() {
        ApplicationModules.of(OpenConsigPlatformApplication.class).verify();
    }
}
