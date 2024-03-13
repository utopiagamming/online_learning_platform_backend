package cn.exam.neo4j.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Section {
    @Id
    private Long id;
    private String name;
}
