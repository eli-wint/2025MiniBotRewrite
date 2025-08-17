package frc.robot.subsystems.flipper;

public class FlipperIOSim implements FlipperIO {

    private double position = 0.0;
    private double velocity = 0.0;
    private double appliedVoltage = 0.0;
    private double current = 0.0;
    private double temperature = 25.0; // Ambient temperature
    private boolean forwardLimit = false;
    private boolean reverseLimit = false;

    private final double timeStepSeconds = 0.02; // Simulates 20ms periodic updates
    private final double maxSpeed = 100.0; // Arbitrary max velocity units per second
    private final double currentPerVolt = 1.2; // Fake current model
    private final double tempIncreasePerAmp = 0.005; // Fake temp model per update

    private final double lowerLimit = FlipperConstants.LOWER_ENDPOINT;
    private final double upperLimit = FlipperConstants.UPPER_ENDPOINT;

    @Override
    public void updateInputs(FlipperIOInputs inputs) {
        // Update position based on velocity
        position += velocity * timeStepSeconds;

        // Enforce soft limits
        if (position >= upperLimit) {
            position = upperLimit;
            forwardLimit = true;
            velocity = 0;
        } else if (position <= lowerLimit) {
            position = lowerLimit;
            reverseLimit = true;
            velocity = 0;
        } else {
            forwardLimit = false;
            reverseLimit = false;
        }

        // Simulate current draw and temperature
        current = Math.abs(appliedVoltage * currentPerVolt);
        temperature += current * tempIncreasePerAmp;

        // Populate inputs
        inputs.position = position;
        inputs.velocity = velocity;
        inputs.appliedVolts = appliedVoltage;
        inputs.currentAmps = current;
        inputs.tempCelsius = temperature;
        inputs.isForwardLimit = forwardLimit;
        inputs.isReverseLimit = reverseLimit;
    }

    @Override
    public void setVelocity(double newVelocity) {
        // Simulate motor velocity (scaled by maxSpeed)
        velocity = newVelocity * maxSpeed;
        appliedVoltage = newVelocity * 12.0; // Assuming ±12V max
    }

    @Override
    public void setPosition(double newPosition) {
        position = newPosition;
    }

    public void stop() {
        velocity = 0;
        appliedVoltage = 0;
    }
}
