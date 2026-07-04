package behavioral;

import java.util.Stack;

/**
 * This demo illustrates the Command pattern.
 *
 * The Command pattern turns a request into a stand-alone object that contains all
 * information about the request. This transformation lets you parameterize methods with
 * different requests, delay or queue a request's execution, and support undoable operations.
 *
 * This example models a simple remote control for home appliances.
 * - The RemoteControl is the "Invoker".
 * - The appliances (Light, Fan) are the "Receivers".
 * - The actions (LightOnCommand, FanStartCommand) are the "Commands".
 * The client configures the remote with specific commands, decoupling the remote (invoker)
 * from the appliances (receivers).
 */


// --- Receiver Classes ---
// These classes perform the actual work. They don't know anything about commands.

class Light {
    public void turnOn() { System.out.println("The light is ON"); }
    public void turnOff() { System.out.println("The light is OFF"); }
}

class Fan {
    public void start() { System.out.println("The fan is spinning"); }
    public void stop() { System.out.println("The fan has stopped"); }
}


// --- Command Interface and Concrete Commands ---

/**
 * 1. The Command Interface
 * Declares a method for executing a particular action.
 */
interface Command {
    void execute();
    void undo(); // Also declares a method for undoing the action.
}

/**
 * 2. A Concrete Command
 * This command encapsulates a request to turn a light on. It holds a reference to the receiver (the light).
 */
class LightOnCommand implements Command {
    private final Light light; // The receiver
    public LightOnCommand(Light light) { this.light = light; }

    @Override
    public void execute() { light.turnOn(); } // The command forwards the call to the receiver.
    
    @Override
    public void undo() { light.turnOff(); } // Implements the undo operation.
}

/**
 * Another Concrete Command for turning the light off.
 */
class LightOffCommand implements Command {
    private final Light light; // The receiver
    public LightOffCommand(Light light) { this.light = light; }

    @Override
    public void execute() { light.turnOff(); }
    
    @Override
    public void undo() { light.turnOn(); }
}

/**
 * A Concrete Command for starting a fan.
 */
class FanStartCommand implements Command {
    private final Fan fan; // The receiver
    public FanStartCommand(Fan fan) { this.fan = fan; }

    @Override
    public void execute() { fan.start(); }
    
    @Override
    public void undo() { fan.stop(); }
}


// --- Invoker Class ---

/**
 * The Invoker class. It holds a command and asks the command to carry out the request.
 * It doesn't know anything about the receiver; it only knows about the command interface.
 */
class SimpleRemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        System.out.println("Invoker: pressing button...");
        if (command != null) {
            command.execute();
        }
    }
}

/**
 * A more advanced Invoker that keeps a history of commands to support undo.
 */
class RemoteControlWithUndo {
    private final Stack<Command> commandHistory = new Stack<>();
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        System.out.println("Invoker: pressing button...");
        if (command != null) {
            command.execute();
            commandHistory.push(command); // Push the executed command onto the history stack.
        }
    }

    public void pressUndo() {
        System.out.println("Invoker: pressing undo...");
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        } else {
            System.out.println("Nothing to undo.");
        }
    }
}


// --- Demo ---
public class CommandDemo {
    public static void main(String[] args) {
        // 5. The client is responsible for creating receivers, commands, and invokers.
        Light livingRoomLight = new Light();
        Fan ceilingFan = new Fan();

        // Create command objects and link them with receivers
        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);
        Command fanStart = new FanStartCommand(ceilingFan);

        // Create an invoker
        RemoteControlWithUndo remote = new RemoteControlWithUndo();

        // --- Client uses the remote ---
        System.out.println("--- Turning light on ---");
        remote.setCommand(lightOn);

        // The client tells the invoker to execute the request.
        // The invoker doesn't know what the command does, only that it can be executed.
        remote.pressButton();

        System.out.println("
--- Turning light off ---");
        remote.setCommand(lightOff);
        remote.pressButton();

        System.out.println("
--- Starting the fan ---");
        remote.setCommand(fanStart);
        remote.pressButton();
        
        System.out.println("
--- UNDOING ACTIONS ---");
        remote.pressUndo(); // Undoes the fan start -> fan stops
        remote.pressUndo(); // Undoes the light off -> light turns on
        remote.pressUndo(); // Undoes the light on -> light turns off
        remote.pressUndo(); // No more actions to undo
    }
}
