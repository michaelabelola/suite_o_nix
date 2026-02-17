package com.suiteonix.nix.shared.ddd;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Service
public @interface UseCase {
}
