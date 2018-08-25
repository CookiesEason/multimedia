package com.example.multimedia.domian.maindomian.tag;

import com.example.multimedia.domian.maindomian.Tags;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author CookiesEason
 * 2018/08/25 20:25
 */
@Entity
@Data
public class SmallTags implements Serializable {

    private static final long serialVersionUID = -4512766661337454661L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  smallTag;

    @ManyToOne
    @JoinColumn(name="tags_id")
    private Tags tags;

}
