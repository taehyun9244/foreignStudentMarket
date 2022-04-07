package com.example.demo.model;

import com.example.demo.util.Timesteamed;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "CommunityBoard")
public class CommunityBoard extends Timesteamed {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMUNITY_ID", nullable = false)
    private Long id;

    @Column(name = "COM_TITLE", nullable = false)
    private String com_title;

    @Column(name = "COM_SUBTITLE", nullable = false)
    private String com_subtitle;

    @Column(name = "COM_CONTENTS", nullable = false)
    private String comm_contents;

    @Column(name = "COM_LOCATION", nullable = false)
    private String com_location;

    @Column(name = "COUNTRY", nullable = false)
    private String com_country;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public CommunityBoard(String com_title, String com_subtitle, String comm_contents, String com_location, String com_country, User user) {
        super();
        this.com_title = com_title;
        this.com_subtitle = com_subtitle;
        this.comm_contents = comm_contents;
        this.com_location = com_location;
        this.com_country = com_country;
        this.user =user;
    }

}
