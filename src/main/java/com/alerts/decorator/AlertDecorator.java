package com.alerts.decorator;

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
