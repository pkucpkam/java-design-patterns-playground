package behavioral.mediator.before;

public class Sprinkler {
    private boolean watering = false;

    public void startWatering() {
        watering = true;
        System.out.println("Sprinkler: Hệ thống phun nước BẮT ĐẦU hoạt động!");
    }

    public void stopWatering() {
        watering = false;
        System.out.println("Sprinkler: Hệ thống phun nước ĐÃ DỪNG.");
    }

    public boolean isWatering() {
        return watering;
    }
}
