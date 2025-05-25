package com.alerts.decorator;

public class RepeatedAlertDecorator extends AlertDecorator {
    private final int repeatCount;

    public RepeatedAlertDecorator(AlertComponent wrapped, int repeatCount) {
        super(wrapped);
        this.repeatCount = repeatCount;
    }

    /**
     * Returns the patient ID with a repeat count suffix.
     *
     * @return the patient ID with the repeat count
     */
    @Override
    public String getCondition() {
        return super.getCondition() + " (Repeated " + repeatCount + " times)";
    }
}
