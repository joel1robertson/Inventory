package org.joro.inventory.ui.util;

@SuppressWarnings("java:S1610")
public abstract class LibraryClass {

    protected LibraryClass() {
        throw new UnsupportedOperationException("%s is a library class - do not instantiate it"
                .formatted(getClass().getSimpleName()));
    }

}
