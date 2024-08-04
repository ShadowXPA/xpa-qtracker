package xpa.shadow.qtracker.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import xpa.shadow.qtracker.comparator.JKAPlayerScoreComparator;
import xpa.shadow.qtracker.model.JKAPlayer;
import xpa.shadow.qtracker.model.JKAStatusResponse;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JKAResponseParser {

    @Autowired
    private JKAPlayerScoreComparator jkaPlayerScoreComparator;

    public JKAStatusResponse parseStatusResponse(String statusResponse) {
        var result = new JKAStatusResponse();
        if (statusResponse == null || statusResponse.isBlank() || !statusResponse.startsWith("ÿÿÿÿstatusResponse"))
            return result;
        var splitResponse = statusResponse.split("\\n", 3);
        result.setServerInfo(parseInfoResponse("ÿÿÿÿinfoResponse\n" + splitResponse[1]));

        if (!splitResponse[2].isBlank()) {
            var players = new ArrayList<JKAPlayer>();
            var playerInfo = splitResponse[2].split("\\n");
            for (String p : playerInfo) {
                var temp = p.split(" ", 3);
                var name = temp[2].substring(1, temp[2].length() - 1);
                var score = temp[0];
                var ping = temp[1];
                players.add(new JKAPlayer(name, Integer.parseInt(score), Integer.parseInt(ping)));
            }
            players.sort(jkaPlayerScoreComparator);
            result.setPlayers(players);
        }

        return result;
    }

    public Map<String, String> parseInfoResponse(String infoResponse) {
        var result = new HashMap<String, String>();
        if (infoResponse == null || infoResponse.isBlank() || !infoResponse.startsWith("ÿÿÿÿinfoResponse"))
            return result;
        var splitResponse = infoResponse.split("\\n", 2);
        var serverInfo = splitResponse[1].split("\\\\");
        for (int i = 1; i < serverInfo.length; i += 2)
            result.put(serverInfo[i], serverInfo[i + 1]);

        return result;
    }

    public JKAResponseParser addHost(Map<String, String> result, Pair<InetAddress, Integer> ipAndPort) {
        result.put("ip", ipAndPort.getFirst().getHostAddress());
        result.put("port", ipAndPort.getSecond().toString());
        return this;
    }

    public JKAResponseParser addPlayerMetrics(JKAStatusResponse result) {
        var players = result.getPlayers();
        var totalPlayers = players.size();
        var botPlayers = countBots(players);
        var realPlayers = totalPlayers - botPlayers;
        result.getServerInfo().put("totalPlayers", String.valueOf(totalPlayers));
        result.getServerInfo().put("realPlayers", String.valueOf(realPlayers));
        result.getServerInfo().put("botPlayers", String.valueOf(botPlayers));
        return this;
    }

    private long countBots(List<JKAPlayer> players) {
        return players.stream().filter(JKAPlayer::isBot).count();
    }
}
