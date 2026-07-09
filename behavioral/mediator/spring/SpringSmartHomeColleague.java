package behavioral.mediator.spring;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class SpringSmartHomeColleague {
    @Autowired
    protected SpringSmartHomeMediator mediator;

    public void setMediator(SpringSmartHomeMediator mediator) {
        this.mediator = mediator;
    }
}
