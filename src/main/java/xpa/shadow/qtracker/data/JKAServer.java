package xpa.shadow.qtracker.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jka_server")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JKAServer {

    @Id
    @Column(name = "id")
    @JsonProperty("id")
    private String id;

    @Column(name = "ip")
    @JsonProperty("ip")
    private String ip;
}
