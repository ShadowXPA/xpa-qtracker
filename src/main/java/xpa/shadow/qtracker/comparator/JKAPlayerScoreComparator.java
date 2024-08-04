package xpa.shadow.qtracker.comparator;

import org.springframework.stereotype.Component;
import xpa.shadow.qtracker.model.JKAPlayer;

import java.util.Comparator;

@Component
public class JKAPlayerScoreComparator implements Comparator<JKAPlayer> {
    @Override
    public int compare(JKAPlayer o1, JKAPlayer o2) {
        return -o1.getScore().compareTo(o2.getScore());
    }
}
