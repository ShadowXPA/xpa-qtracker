package xpa.shadow.qtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xpa.shadow.qtracker.data.JKAServer;
import xpa.shadow.qtracker.exception.JKAServerNotFoundException;
import xpa.shadow.qtracker.repository.JKAServerRepository;

import java.util.List;

@Service
public class JKAServerService {

    @Autowired
    private JKAServerRepository jkaServerRepository;

    public List<JKAServer> getAll() {
        return jkaServerRepository.findAll();
    }

    public JKAServer getById(String id) {
        return jkaServerRepository.findById(id).orElseThrow(JKAServerNotFoundException::new);
    }
}
