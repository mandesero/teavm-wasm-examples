import org.teavm.interop.Address;
import org.teavm.interop.Export;
import org.teavm.interop.Import;

import org.teavm.backend.wasm.wasi.Wasi;
import org.teavm.backend.wasm.wasi.FdResult;
import org.teavm.backend.wasm.wasi.WasiNetwork;
import org.teavm.backend.wasm.wasi.WasiSocket;

public class WasiSocketExample {

    public static void main(String[] args) {
        // empty
    }

    /**
     * Method that simply outputs a message by calling the private native {@code printMessage} method.<br>
     * It is exported to be accessible from the WebAssembly module.
     */
    @Export(name = "hello")
    public static int hello() {
        printMessage("SIIII, WebAssembly!");
        return 10;
    }

    @Export(name = "hello_socket")
    public static void clientExample() {
        WasiNetwork network = new WasiNetwork();

        // Open a socket
        if (network.openSocket(WasiSocket.WasiAddressFamily.INET4, WasiSocket.WasiSockType.SOCKET_STREAM)) {
            System.out.println("Socket opened successfully.");

            // Bind the socket to a local address and port
            if (network.bind("127.0.0.1", (short) 8080)) {
                System.out.println("Socket bound to address.");

                // Connect to a remote address
                if (network.connect("127.0.0.1", (short) 8080)) {
                    System.out.println("Connected to remote address.");

                    // Send data
                    String message = "Hello, WASI!";
                    int bytesSent = network.send(message.getBytes());
                    System.out.println("Sent bytes: " + bytesSent);

                    // Receive data
                    byte[] buffer = new byte[1024];
                    int bytesReceived = network.receive(buffer);
                    if (bytesReceived > 0) {
                        System.out.println("Received bytes: " + bytesReceived);
                        System.out.println("Received message: " + new String(buffer, 0, bytesReceived));
                    }
                }
            }

            // Close the socket
            network.close();
        } else {
            System.out.println("Failed to open socket.");
        }
    }

    /**
     * Native method that is expected to print the message specified as parameter.<br>
     * It is a native method whose implementation will be written in JavaScript.
     *
     * @param message The message to be printed
     */
    @Import(module = "env", name = "printMessage")
    private static native void printMessage(String message);
}
