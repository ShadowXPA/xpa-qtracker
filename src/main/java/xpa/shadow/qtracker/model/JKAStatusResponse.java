package xpa.shadow.qtracker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JKAStatusResponse {

    @JsonProperty("serverInfo")
    private Map<String, String> serverInfo = new HashMap<>();
    @JsonProperty("players")
    private List<JKAPlayer> players = new ArrayList<>();
}
