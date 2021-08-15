package learn.bgspr5.ch02;

import java.io.PrintStream;

public class HelloWorldGreeter implements Greeter {

    private PrintStream printStream;

    @Override
    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void greet() {
        printStream.print("Hello, World!");
    }
}
