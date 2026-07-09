package behavioral.mediator.after;

public abstract class SmartHomeColleague {
    protected final SmartHomeMediator mediator;

    public SmartHomeColleague(SmartHomeMediator mediator) {
        this.mediator = mediator;
    }
}
