package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class CustomException extends Exception {
    public CustomException(String e) {
        super(e);
    }
    public Component getComponentMessage() {
        return Component.text(super.getMessage(), NamedTextColor.RED);
    }
    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
    public static class invalidArgsException extends CustomException {
        public invalidArgsException(int size) {
            super("You must enter at least " + size + " arguments. ");
        }
    }
}

