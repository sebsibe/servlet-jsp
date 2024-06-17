package miss.xing;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        out.println("Current Time: " + LocalDateTime.now());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

/*
package org.example;
        import java.io.IOException;
        import java.security.KeyPair;
        import java.security.KeyPairGenerator;
        import java.security.NoSuchAlgorithmException;
        import java.util.Collections;

        import org.apache.sshd.server.SshServer;
        import org.apache.sshd.sftp.server.SftpSubsystemFactory;

public class MockSFTPServer {


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        // Manually generate a key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // You can adjust the key size as needed
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(2223);

        // Set up manually generated key pair
       // sshd.setKeyPairProvider((session, requestType) -> Collections.singletonList(keyPair));

        sshd.setKeyPairProvider(new CustomHostKeyProvider(keyPair));
        // Set up password authentication
        sshd.setPasswordAuthenticator((username, password, session) ->
                "username".equals(username) && "password".equals(password));

        // Set up SFTP subsystem
        sshd.setSubsystemFactories(Collections.singletonList(new SftpSubsystemFactory()));

        // Start the server
        sshd.start();

        if (sshd.isStarted()) {
            System.out.println("SFTP server started...");
        } else {
            System.out.println("Failed to start SFTP server.");
        }

        // Wait for the server to finish (you can remove this if you don't need to keep the server running indefinitely)
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

*/

/*
package org.example;
import com.jcraft.jsch.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.Properties;

public class SFTPClient {

    public static void main(String[] args) {
        String username = "username";
        String host = "localhost";
        int port = 2223;
        String privateKeyPath = "/Users/sebsibe/Documents/Project/sftp/src/main/resources/private_key.pem"; // Provide the path to your private key file

        try {
            JSch jsch = new JSch();

            // Add the private key
            //jsch.addIdentity(privateKeyPath);

            // Connect to the SSH server
            Session session = jsch.getSession(username, host, port);
            session.setPassword("password");
            session.setConfig("StrictHostKeyChecking", "no"); // Skip host key check
            session.connect();

            // Open an SFTP channel
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("Successfully connected to SFTP server.");

           // String localFile = "path/to/local/file.txt";
            String localFile  = "/Users/sebsibe/Documents/Project/sftp/pom.xml";
            String remoteDir = "/Users/sebsibe/Documents/Project/sftp/upload";
           sftpChannel.cd(remoteDir);

            sftpChannel.put(new FileInputStream(localFile), "pom.xml");
            System.out.println("File uploaded to SFTP server.");
            // Perform SFTP operations here...

            // Disconnect from the SFTP server

            String remoteFile = "/Users/sebsibe/Documents/Project/sftp/upload/file.txt";
            String localDownloadPath = "/Users/sebsibe/Documents/Project/sftp/file.txt";

            sftpChannel.get(remoteFile, new FileOutputStream(localDownloadPath));
            System.out.println("File downloaded from SFTP server.");

            sftpChannel.disconnect();
            System.out.println("Successfully disconnected from SFTP server.");

            // Disconnect from the SSH session
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        catch (SftpException e) {
            throw new RuntimeException(e);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>sftpproject</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- Apache Mina SSHD -->
        <dependency>
            <groupId>org.apache.sshd</groupId>
            <artifactId>sshd-core</artifactId>
            <version>2.7.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.sshd</groupId>
            <artifactId>sshd-sftp</artifactId>
            <version>2.7.0</version>
        </dependency>
        <dependency>
            <groupId>com.jcraft</groupId>
            <artifactId>jsch</artifactId>
            <version>0.1.54</version> <!-- or the latest version -->
        </dependency>

    </dependencies>

package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyToFile {
    public static void main(String[] args) {
        // Provided RSA private key
        String privateKey = "SunRsaSign RSA private CRT key, 2048 bits\n" +
                "  params: null\n" +
                "  modulus: 26892140538798129419756431897571529087184173269805632241230705074577880819779067536870589761614221451536653819058021118365512888259890378796566766172109672942409263695831087346133390553273777950126508635007737803707704640129423365484090832963680397831167323049246807621865674354510595925586781153213454602567044570744577927256471511360847457311604372007186273139339862750853553825163002077518392456588769732837296270914495505502802275017823866592456792558894052001008000653140434671148437246336225162356410484196532924017282641728234899066969349992378019120674123843253911065616229608219111766751852956178157599843339\n" +
                "  private exponent: 3399627452644193390644705102024507049259515625377110077034291950239982644794079291743180740268456363977313225977626454760795799002596880973031351110608185304909604493949380634797368520589487622515496956544228568651575948601130240673752117904452326106340254687626986300061905672019169129552565449354921210648416064015873127027450562283380118918123108693263595207269463737325275050944273817674902618582328565209587285616588521964826055791125488313008975113870139354528002470899546688690822560292270279669061485408713452019152970878182675018471670874894147483395654343338952369094468668175714830111605281259059489623073";
        // Path to the output file
        String filePath = "/Users/sebsibe/Documents/Project/sftp/src/main/resources/private_key.pem"; // Change this to the desired file path

        try {
            // Write private key to file
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(privateKey);
            fileWriter.close();

            System.out.println("Private key saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyToFile {
    public static void main(String[] args) {
        // Provided RSA private key
        String privateKey = "SunRsaSign RSA private CRT key, 2048 bits\n" +
                "  params: null\n" +
                "  modulus: 26892140538798129419756431897571529087184173269805632241230705074577880819779067536870589761614221451536653819058021118365512888259890378796566766172109672942409263695831087346133390553273777950126508635007737803707704640129423365484090832963680397831167323049246807621865674354510595925586781153213454602567044570744577927256471511360847457311604372007186273139339862750853553825163002077518392456588769732837296270914495505502802275017823866592456792558894052001008000653140434671148437246336225162356410484196532924017282641728234899066969349992378019120674123843253911065616229608219111766751852956178157599843339\n" +
                "  private exponent: 3399627452644193390644705102024507049259515625377110077034291950239982644794079291743180740268456363977313225977626454760795799002596880973031351110608185304909604493949380634797368520589487622515496956544228568651575948601130240673752117904452326106340254687626986300061905672019169129552565449354921210648416064015873127027450562283380118918123108693263595207269463737325275050944273817674902618582328565209587285616588521964826055791125488313008975113870139354528002470899546688690822560292270279669061485408713452019152970878182675018471670874894147483395654343338952369094468668175714830111605281259059489623073";
        // Path to the output file
        String filePath = "/Users/sebsibe/Documents/Project/sftp/src/main/resources/private_key.pem"; // Change this to the desired file path

        try {
            // Write private key to file
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(privateKey);
            fileWriter.close();

            System.out.println("Private key saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


</project>
*/


