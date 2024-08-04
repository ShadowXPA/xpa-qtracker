package xpa.shadow.qtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xpa.shadow.qtracker.configuration.Constants;
import xpa.shadow.qtracker.data.JKAServer;
import xpa.shadow.qtracker.model.JKAStatusResponse;
import xpa.shadow.qtracker.service.TrackerService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(Constants.API_V1_URL + TrackerController.BASE_URL)
public class TrackerController {

    public static final String BASE_URL = "/tracker";

    @Autowired
    private TrackerService trackerService;

    @GetMapping("/status/{server}")
    @ResponseStatus(HttpStatus.OK)
    public JKAStatusResponse getStatus(@PathVariable("server") String server) throws IOException {
        return trackerService.getStatus(server);
    }

    @GetMapping("/info/{server}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getInfo(@PathVariable("server") String server) throws IOException {
        return trackerService.getInfo(server);
    }

    @GetMapping("/servers")
    @ResponseStatus(HttpStatus.OK)
    public List<JKAServer> getServers() {
        return trackerService.getServers();
    }
}
