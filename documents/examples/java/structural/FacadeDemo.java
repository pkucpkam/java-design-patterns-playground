package structural;

/**
 * This demo illustrates the Facade pattern.
 *
 * The Facade pattern provides a simplified, unified interface to a complex subsystem.
 * It hides the complexities of the system and provides a single entry point for the client.
 *
 * In this example, we have a complex home theater system with multiple components:
 * an Amplifier, a DVD Player, a Projector, and Lights. To watch a movie, a user would
 * have to perform a sequence of operations on each component. The `HomeTheaterFacade`
 * simplifies this by providing a single `watchMovie()` method.
 */


// --- The Complex Subsystem Components ---
// These are the individual classes that make up our complex system.
// The client code does not need to interact with them directly.

class Amplifier {
    public void on() { System.out.println("Amplifier is on"); }
    public void off() { System.out.println("Amplifier is off"); }
    public void setDvd(DvdPlayer dvd) { System.out.println("Amplifier setting DVD player"); }
    public void setSurroundSound() { System.out.println("Amplifier surround sound on"); }
    public void setVolume(int level) { System.out.println("Amplifier setting volume to " + level); }
}

class DvdPlayer {
    private String movie;
    public void on() { System.out.println("DVD Player is on"); }
    public void off() { System.out.println("DVD Player is off"); }
    public void play(String movie) {
        this.movie = movie;
        System.out.println("DVD Player playing "" + movie + """);
    }
    public void stop() { System.out.println("DVD Player stopped "" + movie + """); }
    public void eject() { System.out.println("DVD Player eject"); }
}

class Projector {
    public void on() { System.out.println("Projector is on"); }
    public void off() { System.out.println("Projector is off"); }
    public void wideScreenMode() { System.out.println("Projector in widescreen mode (16x9 aspect ratio)"); }
}

class TheaterLights {
    public void on() { System.out.println("Theater lights are on"); }
    public void dim(int level) { System.out.println("Theater lights dimming to " + level + "%"); }
}


// --- The Facade ---

/**
 * The Facade class provides a simple interface to the complex subsystem.
 * It knows which subsystem classes are responsible for a request and delegates
 * the client's requests to the appropriate subsystem objects.
 */
class HomeTheaterFacade {
    // The facade holds references to all the components of the subsystem.
    private final Amplifier amp;
    private final DvdPlayer dvd;
    private final Projector projector;
    private final TheaterLights lights;

    public HomeTheaterFacade(Amplifier amp, DvdPlayer dvd, Projector projector, TheaterLights lights) {
        this.amp = amp;
        this.dvd = dvd;
        this.projector = projector;
        this.lights = lights;
    }

    /**
     * This is a simplified method that encapsulates a complex sequence of actions.
     * The client just needs to call this one method to watch a movie.
     * @param movie The name of the movie to watch.
     */
    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        lights.dim(10);
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setDvd(dvd);
        amp.setSurroundSound();
        amp.setVolume(5);
        dvd.on();
        dvd.play(movie);
    }

    /**
     * Another simplified method to end the movie.
     */
    public void endMovie() {
        System.out.println("
Shutting movie theater down...");
        lights.on();
        amp.off();
        projector.off();
        dvd.stop();
        dvd.eject();
        dvd.off();
    }
}


// --- The Client ---
public class FacadeDemo {
    public static void main(String[] args) {
        // 1. Instantiate all the complex subsystem components.
        Amplifier amp = new Amplifier();
        DvdPlayer dvd = new DvdPlayer();
        Projector projector = new Projector();
        TheaterLights lights = new TheaterLights();

        // 2. Create the Facade, providing it with the components it needs to manage.
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(amp, dvd, projector, lights);

        // 3. The client's interaction is now simple and clean.
        // The client doesn't need to know about the internal workings or the sequence of operations.
        homeTheater.watchMovie("Raiders of the Lost Ark");
        homeTheater.endMovie();
    }
}
