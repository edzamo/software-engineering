package ec.com.pattern.behavioral.command.credicardstatus;

public class CrediCardInvoker {
    
    private Command command;

    public CrediCardInvoker() {
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void run() {
        command.execute();
    }
    
}
