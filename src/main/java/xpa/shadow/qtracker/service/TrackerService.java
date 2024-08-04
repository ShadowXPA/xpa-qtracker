package xpa.shadow.qtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import xpa.shadow.qtracker.data.JKAServer;
import xpa.shadow.qtracker.model.JKAStatusResponse;
import xpa.shadow.qtracker.parser.JKAResponseParser;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class TrackerService {

    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private ServerNameLookupService serverNameLookupService;
    @Autowired
    private JKAServerService jkaServerService;
    @Autowired
    private JKAResponseParser jkaResponseParser;

    private final byte[] getStatus;
    private final byte[] getInfo;
    private final DatagramSocket socket;

    public TrackerService() throws SocketException {
        var prefix = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        var status = "getstatus".getBytes();
        var info = "getinfo".getBytes();
        getStatus = new byte[prefix.length + status.length];
        System.arraycopy(prefix, 0, getStatus, 0, prefix.length);
        System.arraycopy(status, 0, getStatus, prefix.length, status.length);
        getInfo = new byte[prefix.length + info.length];
        System.arraycopy(prefix, 0, getInfo, 0, prefix.length);
        System.arraycopy(info, 0, getInfo, prefix.length, info.length);
        socket = new DatagramSocket();
        socket.setSoTimeout(2000);
    }

    public JKAStatusResponse getStatus(String server) throws IOException {
        var ipAndPort = getServerIpAndPort(server);
        var receivePacket = sendDatagramPacket(ipAndPort.getFirst(), ipAndPort.getSecond(), getStatus);
        var result = jkaResponseParser.parseStatusResponse(new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.ISO_8859_1));
        jkaResponseParser.addPlayerMetrics(result)
                .addHost(result.getServerInfo(), ipAndPort);
        return result;
    }

    public Map<String, String> getInfo(String server) throws IOException {
        var ipAndPort = getServerIpAndPort(server);
        var receivePacket = sendDatagramPacket(ipAndPort.getFirst(), ipAndPort.getSecond(), getInfo);
        var result = jkaResponseParser.parseInfoResponse(new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.ISO_8859_1));
        jkaResponseParser.addHost(result, ipAndPort);
        return result;
    }

    public List<JKAServer> getServers() {
        return jkaServerService.getAll();
    }

    private DatagramPacket sendDatagramPacket(InetAddress address, int port, byte[] data) throws IOException {
        byte[] receive = new byte[BUFFER_SIZE];
        var sendPacket = new DatagramPacket(data, data.length, address, port);
        var receivePacket = new DatagramPacket(receive, receive.length);
        socket.send(sendPacket);
        socket.receive(receivePacket);
        return receivePacket;
    }

    private Pair<InetAddress, Integer> getServerIpAndPort(String server) throws UnknownHostException {
        var temp = serverNameLookupService.getServerIP(server);
        // ^[^:\n]+(?::\d{1,5})?$
        var serverIpAndPort = temp.split(":", 2);

        var ip = InetAddress.getByName(serverIpAndPort[0]);
        var port = serverIpAndPort.length == 2 ? Integer.parseInt(serverIpAndPort[1]) : 29070;

        return Pair.of(ip, port);
    }
}
