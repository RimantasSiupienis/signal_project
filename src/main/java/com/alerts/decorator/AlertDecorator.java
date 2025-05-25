package com.alerts.decorator;

/**
 * Interface representing an alert component in the healthcare system.
 * This interface defines the methods that any alert component must implement.
 */
public abstract class AlertDecorator implements AlertComponent {
    protected final AlertComponent wrapped;

    public AlertDecorator(AlertComponent wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getPatientId() {
        return wrapped.getPatientId();
    }

    @Override
    public long getTimestamp() {
        return wrapped.getTimestamp();
    }

    @Override
    public String getCondition() {
        return wrapped.getCondition();
    }
}
