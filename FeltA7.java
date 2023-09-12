import java.io.IOException;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

public class PingScriptWithRetry {
    public static void main(String[] args) {
        String ipAddress = "192.168.0.102"; // Replace with the IP address you want to ping
        long initialDelay = 0; // Initial delay before first execution (in milliseconds)
        long period = 300 * 1000; // 30 seconds in milliseconds
        long retryDelay = 6 * 60 * 60 * 1000; 

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new PingTask(ipAddress, retryDelay), initialDelay, period);
    }

    static class PingTask extends TimerTask {
        private final String ipAddress;
        private final long retryDelay;
        private boolean hasFailed = false;

        public PingTask(String ipAddress, long retryDelay) {
            this.ipAddress = ipAddress;
            this.retryDelay = retryDelay;
        }

        @Override
        public void run() {
            try {
                InetAddress geek = InetAddress.getByName(ipAddress);
                System.out.println("Sending Ping Request to " + ipAddress);
                if (geek.isReachable(5000)) {
                    System.out.println("Host is reachable");
                    hasFailed = false;
                } else {
                    System.out.println("Ping failed! Sending a message...");
                    sendNotification();
                    hasFailed = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                hasFailed = true;
            }

            if (hasFailed) {
                try {
                    System.out.println("Waiting for " + retryDelay / (60 * 1000) + " minutes before retrying...");
                    Thread.sleep(retryDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private void sendNotification() {
            try {
        // Add code to send a message/notification here
        // For example, you can use a library to send an email, SMS, or any other notification method.
        // Example: Send an email using JavaMail API
        /*
        try {
            // Set up email properties and send the email
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        */
        
             String command = "curl -d Llama_Is_Down  ntfy.sh/Cyber4_Network";
             Process process = Runtime.getRuntime().exec(command);

        // Handle the process output and errors if needed
        // ...

        	int exitCode = process.waitFor();
            if (exitCode == 0) {
            System.out.println("Command executed successfully");
        } else {
            System.out.println("Command failed with exit code: " + exitCode);
        }
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
}
    }
}
