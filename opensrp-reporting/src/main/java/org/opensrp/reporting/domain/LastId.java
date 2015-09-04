package org.opensrp.reporting.domain;

import javax.persistence.*;

@Entity
@Table(name="last_id")
public class LastId {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "anm_id", insertable = true, updatable = true)
    @ManyToOne
    private ANM anm;

    @Column(name = "last_id")
    private Long lastId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ANM getAnm() {
        return anm;
    }

    public void setAnm(ANM anm) {
        this.anm = anm;
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }
}
