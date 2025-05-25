package com.alerts.decorator;

public class PriorityAlertDecorator extends AlertDecorator {

    public PriorityAlertDecorator(AlertComponent wrapped) {
        super(wrapped);
    }

    /**
     * Returns the patient ID with a priority prefix.
     *
     * @return the patient ID with the afformentioned prefix
     */
    @Override
    public String getCondition() {
        return "[PRIORITY] " + super.getCondition();
    }
}
