package xpa.shadow.qtracker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JKAPlayer {

    @JsonProperty("name")
    private String name;
    @JsonProperty("score")
    private Integer score;
    @JsonProperty("ping")
    private Integer ping;

    @JsonProperty("isBot")
    public Boolean isBot() {
        return getPing() == 0;
    }
}
