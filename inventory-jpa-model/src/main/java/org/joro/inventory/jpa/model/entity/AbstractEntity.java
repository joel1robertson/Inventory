package org.joro.inventory.jpa.model.entity;

import java.util.Optional;

public abstract class AbstractEntity<T> {

    abstract T getKey();

    @Override
    public int hashCode() {
        return Optional.ofNullable(getKey())
                .map(Object::hashCode)
                .orElse(super.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity<?> abstractEntity)) {
            return false;
        }

        return Optional.ofNullable(getKey())
                .map(key -> key.equals(abstractEntity.getKey()))
                .orElse(super.equals(abstractEntity));
    }
}
