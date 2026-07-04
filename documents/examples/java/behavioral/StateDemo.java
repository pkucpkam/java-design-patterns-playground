package behavioral;

/**
 * This demo illustrates the State pattern.
 *
 * The State pattern allows an object to alter its behavior when its internal state changes.
 * The object will appear to change its class. This pattern is used to avoid massive
 * if/else or switch statements that check the object's current state.
 *
 * In this example, we model a simple `AudioPlayer`. The player's behavior (what happens
 * when you click play, pause, stop) depends entirely on its current state (Ready, Playing, Paused).
 * We encapsulate the logic for each state into its own class. The `AudioPlayer` (the Context)
 * delegates actions to its current state object.
 */


// --- The Context Class ---

/**
 * 1. The Context
 * The context object holds a reference to the current state and delegates state-specific
 * behavior to it. It also provides a way for states to transition the context to a new state.
 */
class AudioPlayer {
    private PlayerState state;

    public AudioPlayer() {
        // The initial state of the player is "Ready".
        this.state = new ReadyState();
        System.out.println("AudioPlayer initialized. Current state: Ready");
    }

    /**
     * The context allows state objects to change its current state.
     * @param state The new state to transition to.
     */
    void setState(PlayerState state) {
        this.state = state;
        System.out.println("State changed to: " + state.getClass().getSimpleName());
    }

    // The context's public methods delegate the work to the state object.
    // The client interacts with these methods, unaware of the internal state changes.
    public void clickPlay() {
        System.out.print("Client clicks Play button -> ");
        state.play(this);
    }

    public void clickPause() {
        System.out.print("Client clicks Pause button -> ");
        state.pause(this);
    }

    public void clickStop() {
        System.out.print("Client clicks Stop button -> ");
        state.stop(this);
    }
}


// --- The State Interface and Concrete States ---

/**
 * 2. The State Interface
 * Declares methods that correspond to the actions the context can perform.
 */
interface PlayerState {
    void play(AudioPlayer player);
    void pause(AudioPlayer player);
    void stop(AudioPlayer player);
}

/**
 * 3. Concrete States
 * Each state implements the behavior associated with that state of the context.
 * They are also responsible for transitioning the context to a new state.
 */
class ReadyState implements PlayerState {
    @Override
    public void play(AudioPlayer player) {
        System.out.println("Starting playback...");
        // When play is clicked in Ready state, transition the context to the Playing state.
        player.setState(new PlayingState());
    }

    @Override
    public void pause(AudioPlayer player) {
        System.out.println("Action ignored: Already stopped. Cannot pause.");
    }

    @Override
    public void stop(AudioPlayer player) {
        System.out.println("Action ignored: Already stopped.");
    }
}

class PlayingState implements PlayerState {
    @Override
    public void play(AudioPlayer player) {
        System.out.println("Action ignored: Already playing.");
    }

    @Override
    public void pause(AudioPlayer player) {
        System.out.println("Pausing playback...");
        // Transition to the Paused state.
        player.setState(new PausedState());
    }

    @Override
    public void stop(AudioPlayer player) {
        System.out.println("Stopping playback...");
        // Transition back to the Ready state.
        player.setState(new ReadyState());
    }
}

class PausedState implements PlayerState {
    @Override
    public void play(AudioPlayer player) {
        System.out.println("Resuming playback...");
        // Transition back to the Playing state.
        player.setState(new PlayingState());
    }

    @Override
    public void pause(AudioPlayer player) {
        System.out.println("Action ignored: Already paused.");
    }

    @Override
    public void stop(AudioPlayer player) {
        System.out.println("Stopping playback from paused state...");
        // Transition back to the Ready state.
        player.setState(new ReadyState());
    }
}


// --- Demo ---
public class StateDemo {
    public static void main(String[] args) {
        // The client only interacts with the AudioPlayer context object.
        AudioPlayer player = new AudioPlayer();
        
        System.out.println("
--- User Interaction Simulation ---");

        // Sequence of actions
        player.clickPlay();  // State: Ready -> Playing
        player.clickPause(); // State: Playing -> Paused
        player.clickPlay();  // State: Paused -> Playing
        player.clickPlay();  // Action ignored
        player.clickStop();  // State: Playing -> Ready
        player.clickPause(); // Action ignored
    }
}
