package behavioral.mediator.after;

public class Siren extends SmartHomeColleague {
    private boolean sounding = false;

    public Siren(SmartHomeMediator mediator) {
        super(mediator);
    }

    public void startSounding() {
        sounding = true;
        System.out.println("Siren: Còi báo động BẮT ĐẦU kêu cứu hỏa!");
    }

    public void stopSounding() {
        sounding = false;
        System.out.println("Siren: Còi báo động ĐÃ TẮT.");
    }

    public boolean isSounding() {
        return sounding;
    }
}
