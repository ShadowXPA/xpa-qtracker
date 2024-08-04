package xpa.shadow.qtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xpa.shadow.qtracker.exception.JKAServerNotFoundException;

@Service
public class ServerNameLookupService {

    @Autowired
    private JKAServerService jkaServerService;

    public String getServerIP(String serverName) {
        try {
            var server = jkaServerService.getById(serverName);
            return server.getIp();
        } catch (JKAServerNotFoundException ex) {
            return serverName;
        }
    }
}
