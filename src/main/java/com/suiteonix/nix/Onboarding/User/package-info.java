
@ApplicationModule(id = "user-onboarding", displayName = "User Onboarding",
        allowedDependencies = {"Auth::service", "User :: service", "Mail"})
package com.suiteonix.nix.Onboarding.User;

import org.springframework.modulith.ApplicationModule;