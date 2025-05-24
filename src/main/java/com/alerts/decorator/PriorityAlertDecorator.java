package com.alerts.decorator;

public class PriorityAlertDecorator extends AlertDecorator {

    public PriorityAlertDecorator(AlertComponent wrapped) {
        super(wrapped);
    }

    @Override
    public String getCondition() {
        return "[PRIORITY] " + super.getCondition();
    }
}
