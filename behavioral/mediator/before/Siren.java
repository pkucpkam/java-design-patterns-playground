package behavioral.mediator.before;

public class Siren {
    private boolean sounding = false;

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
